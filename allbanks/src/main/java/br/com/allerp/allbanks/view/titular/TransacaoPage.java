package br.com.allerp.allbanks.view.titular;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.ibm.icu.text.DecimalFormat;

import br.com.allerp.allbanks.entity.conta.Conta;
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

	private Double valDep;
	private Double valSaq;
	private DecimalFormat decFormt;

	private Label saldo;

	public TransacaoPage() {
		setTitle("Transações");
		if (!getUserPerfil("Titular")) {
			setResponsePage(DashboardPage.class);
			return;
		}

		decFormt = new DecimalFormat("R$ #,##0.00");
		
		add(lbSaldo());
		add(formDep(), formSaque());

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

	public void formTransf() {
		/**
		 * CPF ou CNPJ da pessoa/empresa beneficiária;
		 * Número da agência onde a pessoa/empresa possui conta;
		 * Número da conta bancária.
		 */
		
		
	}
}
