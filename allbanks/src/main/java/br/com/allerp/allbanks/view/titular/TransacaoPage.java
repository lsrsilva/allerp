package br.com.allerp.allbanks.view.titular;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.ibm.icu.text.DecimalFormat;

import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Contato;
import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.exceptions.FeedbackException;
import br.com.allerp.allbanks.service.conta.ContaService;
import br.com.allerp.allbanks.service.conta.TitularService;
import br.com.allerp.allbanks.view.DashboardPage;

public class TransacaoPage extends DashboardPage {

	private static final long serialVersionUID = -837099547910614195L;

	@SpringBean(name = "titularService")
	private TitularService titularService;

	@SpringBean(name = "contaService")
	private ContaService contaService;

	private Titular titular;
	private Conta conta;
	private Contato contato;

	private Double valDep;
	private Double valSaq;
	private Double valTransf;
	private String cpfCnpjBenef;
	private String agBenef;
	private Integer numContaBenef;
	private DecimalFormat decFormt;

	private Label saldo;
	private ModalWindow addContatoModal;

	public TransacaoPage() {
		setTitle("Transações");
		if (!getUserPerfil("Titular")) {
			setResponsePage(DashboardPage.class);
			return;
		}

		decFormt = new DecimalFormat("R$ #,##0.00");

		add(lbSaldo());
		add(formDep(), formSaque(), formTransf());

	}

	public Label lbSaldo() {

		titular = getSessao().getUser().getTitular();
		List<Conta> contas = titular.getContas();
		conta = new Conta();

		// Compara o acesso do usuário do titular com a conta cadastrada para que possa
		// ser trabalhado as transações para esta conta
		for (int i = 0; i < contas.size(); i++) {
			if (titular.getUser().getUserAccess().equals(contas.get(i).getNumConta().toString())) {
				conta = contas.get(i);
				break;
			}
		}

		saldo = new Label("saldo", Model.of("Saldo: " + decFormt.format(conta.getSaldo())));
		saldo.setOutputMarkupId(true);

		return saldo;
	}

	public Form<Double> formDep() {

		Form<Double> formDep = new Form<Double>("formDep");
		TextField<Double> valDepo = new TextField<Double>("valDep", new PropertyModel<Double>(this, "valDep"));
		valDepo.clearInput();

		formDep.add(valDepo, new AjaxButton("depositar", formDep) {

			private static final long serialVersionUID = 5413163658877175673L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				try {
					contaService.deposita(conta, valDep);
					saldo.modelChanged();
					saldo.setDefaultModel(Model.of("Saldo: " + decFormt.format(conta.getSaldo())));
					target.add(saldo, form);
				} catch (NullPointerException | FeedbackException e) {
					String message = e.getMessage();
					System.out.println(message);
				}
			}

		});

		return formDep;
	}

	public Form<Double> formSaque() {
		Form<Double> formSaque = new Form<Double>("formSaque");
		TextField<Double> valSaque = new TextField<Double>("valSaque", new PropertyModel<Double>(this, "valSaq"));
		valSaque.clearInput();

		formSaque.add(valSaque, new AjaxButton("sacar", formSaque) {

			private static final long serialVersionUID = 5413163658877175673L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				try {
					contaService.saque(conta, valSaq);
					saldo.modelChanged();
					saldo.setDefaultModel(Model.of("Saldo: " + decFormt.format(conta.getSaldo())));
					target.add(saldo);
				} catch (NullPointerException | FeedbackException e) {
					String message = e.getMessage();
					System.out.println(message);
				}
			}

		});

		return formSaque;
	}

	public Form<Conta> formTransf() {

		Form<Conta> formTransf = new Form<Conta>("formTransf");
		final TextField<String> cnpfCnpjBenefi = new TextField<String>("cpfCnpjBenef",
				new PropertyModel<String>(this, "cpfCnpjBenef"));
		final TextField<String> agCtBenef = new TextField<String>("agBenef",
				new PropertyModel<String>(this, "agBenef"));
		final TextField<Integer> numCtBenef = new TextField<Integer>("numContaBenef",
				new PropertyModel<Integer>(this, "numContaBenef"));
		final TextField<Double> valorTransf = new TextField<Double>("valTransf",
				new PropertyModel<Double>(this, "valTransf"));

		formTransf.add(new AjaxButton("transferir") {

			private static final long serialVersionUID = -6305437308864849716L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				try {
					Conta ctBenef = contaService.verifExisteConta(numContaBenef, cpfCnpjBenef);
					contaService.transfere(conta, ctBenef, valTransf);

					saldo.modelChanged();
					saldo.setDefaultModel(Model.of("Saldo: " + decFormt.format(conta.getSaldo())));

					target.add(saldo);
				} catch (NumberFormatException | FeedbackException fe) {
					System.out.println(fe.getMessage());
				}

			}

		});

		formTransf.add(cnpfCnpjBenefi, agCtBenef, numCtBenef, valorTransf);
		return formTransf;

	}

	public ModalWindow addContatoMd() {
		addContatoModal = new ModalWindow("addContatoMd");
//
//		addContatoModal
//				.setContent(new AddContatoPanel(addContatoModal.getContentId(), contato, conta, addContatoModal));

		return addContatoModal;
	}

	public Double getValDep() {
		return valDep;
	}

	public void setValDep(Double valDep) {
		this.valDep = valDep;
	}

	public Double getValSaq() {
		return valSaq;
	}

	public void setValSaq(Double valSaq) {
		this.valSaq = valSaq;
	}

	public Double getValTransf() {
		return valTransf;
	}

	public void setValTransf(Double valTransf) {
		this.valTransf = valTransf;
	}

	public String getCpfCnpjBenef() {
		return cpfCnpjBenef;
	}

	public void setCpfCnpjBenef(String cpfCnpjBenef) {
		this.cpfCnpjBenef = cpfCnpjBenef;
	}

	public String getAgBenef() {
		return agBenef;
	}

	public void setAgBenef(String agBenef) {
		this.agBenef = agBenef;
	}

	public Integer getNumContaBenef() {
		return numContaBenef;
	}

	public void setNumContaBenef(Integer numContaBenef) {
		this.numContaBenef = numContaBenef;
	}
}
