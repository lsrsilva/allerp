package br.com.allerp.allbanks.view.titular;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.hibernate.LazyInitializationException;

import com.ibm.icu.text.DecimalFormat;

import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Contato;
import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.exceptions.FeedbackException;
import br.com.allerp.allbanks.service.conta.ContaService;
import br.com.allerp.allbanks.service.conta.ContatoService;
import br.com.allerp.allbanks.service.conta.TitularService;
import br.com.allerp.allbanks.view.DashboardPage;
import br.com.allerp.allbanks.view.LoginPage;

public class TransacaoPage extends DashboardPage {

	private static final long serialVersionUID = -837099547910614195L;

	@SpringBean(name = "titularService")
	private TitularService titularService;

	@SpringBean(name = "contaService")
	private ContaService contaService;

	@SpringBean(name = "contatoService")
	private ContatoService contatoService;

	private Titular titular;
	private Conta conta;
	private Contato contato;

	private List<Contato> contatos;

	private Double valDep;
	private Double valSaq;
	private Double valTransf;
	private String cpfCnpjBenef;
	private Integer agBenef;
	private Integer numContaBenef;
	private DecimalFormat decFormt;

	private Label lbSaldo;
	private ModalWindow addContatoModal;

	public TransacaoPage() {
		setTitle("Transações");
		if (!getUserPerfil("Titular")) {
			setResponsePage(DashboardPage.class);
			return;
		}

		contatos = contatoService.searchContatos(getSessao().getUser().getTitular());
		decFormt = new DecimalFormat("R$ #,##0.00");

		addContatoModal = new ModalWindow("addContatoMd");

		addContatoModal.setInitialHeight(200);
		addContatoModal.setInitialWidth(450);

		add(lbSaldo());
		add(formDep(), formSaque(), formTransf(), addContatoModal);

	}

	public Label lbSaldo() {

		titular = getSessao().getUser().getTitular();
		List<Conta> contas = titular.getContas();
		conta = new Conta();

		// Compara o acesso do usuário do titular com a conta cadastrada para que possa
		// ser trabalhado as transações para esta conta
		try {
			for (int i = 0; i < contas.size(); i++) {
				if (titular.getUser().getUserAccess().equals(contas.get(i).getNumConta().toString())) {
					conta = contas.get(i);
					break;
				}
			}
		} catch (LazyInitializationException lie) {
			getSessao().invalidate();
			setResponsePage(LoginPage.class);
		}

		lbSaldo = new Label("saldo", Model.of("Saldo: " + decFormt.format(conta.getSaldo())));
		lbSaldo.setOutputMarkupId(true);

		return lbSaldo;
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
					lbSaldo.modelChanged();
					lbSaldo.setDefaultModel(Model.of("Saldo: " + decFormt.format(conta.getSaldo())));
					target.add(lbSaldo, form);
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
					lbSaldo.modelChanged();
					lbSaldo.setDefaultModel(Model.of("Saldo: " + decFormt.format(conta.getSaldo())));
					target.add(lbSaldo);
				} catch (NullPointerException | FeedbackException e) {
					String message = e.getMessage();
					System.out.println(message);
				}
			}

		});

		return formSaque;
	}

	public Form<Conta> formTransf() {

		contato = new Contato();
		final Form<Conta> formTransf = new Form<Conta>("formTransf");
		formTransf.setOutputMarkupId(true);

		final TextField<String> cpfCnpjBenefi = new TextField<String>("cpfCnpjBenef",
				new PropertyModel<String>(this, "cpfCnpjBenef"));
		cpfCnpjBenefi.setOutputMarkupId(true);
		// habilita ou desabilita o campo: cpfCnpjBenefi.setEnabled(false);
		final TextField<Integer> agCtBenef = new TextField<Integer>("agBenef",
				new PropertyModel<Integer>(this, "agBenef"));
		agCtBenef.setOutputMarkupId(true);
		final TextField<Integer> numCtBenef = new TextField<Integer>("numContaBenef",
				new PropertyModel<Integer>(this, "numContaBenef"));
		numCtBenef.setOutputMarkupId(true);
		final TextField<Double> valorTransf = new TextField<Double>("valTransf",
				new PropertyModel<Double>(this, "valTransf"));
		valorTransf.setOutputMarkupId(true);

		ChoiceRenderer<Contato> contatoRender = new ChoiceRenderer<Contato>("ctContato.titular.nome");
		final DropDownChoice<Contato> dropContatos = new DropDownChoice<Contato>("dropContatos", new Model<Contato>(),
				new ArrayList<Contato>(), contatoRender) {

			private static final long serialVersionUID = -7535708000088989987L;

			@Override
			protected void onBeforeRender() {
				IModel<List<Contato>> contatosMd = new LoadableDetachableModel<List<Contato>>() {

					private static final long serialVersionUID = 2057471502415626394L;

					@Override
					protected List<Contato> load() {
						return contatos;
					}
				};
				setChoices(contatosMd);
				super.onBeforeRender();
			}
		};

		OnChangeAjaxBehavior onChangeAjaxBehavior = new OnChangeAjaxBehavior() {

			private static final long serialVersionUID = 154132402874017582L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				Contato cont = dropContatos.getModelObject();

				try {
					cpfCnpjBenef = cont.getCtContato().getTitular().getCpfCnpj();
					cpfCnpjBenefi.modelChanged();
					cpfCnpjBenefi.setModelObject(cpfCnpjBenef);
					cpfCnpjBenefi.setEnabled(false);

					agBenef = cont.getCtContato().getAgencia().getCodAg();
					agCtBenef.modelChanged();
					agCtBenef.setModelObject(agBenef);
					agCtBenef.setEnabled(false);

					numContaBenef = cont.getCtContato().getNumConta();
					numCtBenef.modelChanged();
					numCtBenef.setModelObject(numContaBenef);
					numCtBenef.setEnabled(false);

					target.add(cpfCnpjBenefi, agCtBenef, numCtBenef);
				} catch (NullPointerException ne) {
					formTransf.clearInput();
					numCtBenef.setEnabled(true);
					agCtBenef.setEnabled(true);
					cpfCnpjBenefi.setEnabled(true);
					target.add(formTransf);
				}

			}
		};
		dropContatos.add(onChangeAjaxBehavior);

		formTransf.add(new AjaxButton("transferir") {

			private static final long serialVersionUID = -6305437308864849716L;

			private boolean temContato;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				System.out.println("Valor drop: " + dropContatos.get(1));

				try {
					Conta ctBenef = contaService.verifExisteConta(numContaBenef, cpfCnpjBenef, agBenef);

					contaService.transfere(conta, ctBenef, valTransf);

					lbSaldo.modelChanged();
					lbSaldo.setDefaultModel(Model.of("Saldo: " + decFormt.format(conta.getSaldo())));
					temContato = titularService.existeContato(titular, ctBenef);

					TransacaoConfAddContatoPanel addContato = new TransacaoConfAddContatoPanel(
							addContatoModal.getContentId(), contato, titular, ctBenef, addContatoModal);
					addContatoModal.setContent(addContato);
					if (!temContato) {
						addContatoModal.show(target);
					}

					target.add(lbSaldo);
				} catch (NumberFormatException | FeedbackException fe) {
					System.out.println(fe.getMessage());
				}

			}

		});

		formTransf.add(cpfCnpjBenefi, agCtBenef, numCtBenef, valorTransf, dropContatos);
		return formTransf;

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

	public Integer getAgBenef() {
		return agBenef;
	}

	public void setAgBenef(Integer agBenef) {
		this.agBenef = agBenef;
	}

	public Integer getNumContaBenef() {
		return numContaBenef;
	}

	public void setNumContaBenef(Integer numContaBenef) {
		this.numContaBenef = numContaBenef;
	}
}
