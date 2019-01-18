package br.com.allerp.allbanks.view.cadastros.panels;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.conta.Agencia;
import br.com.allerp.allbanks.entity.conta.Banco;
import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.entity.enums.Contas;
import br.com.allerp.allbanks.entity.enums.Status;
import br.com.allerp.allbanks.service.conta.AgenciaService;
import br.com.allerp.allbanks.service.conta.BancoService;
import br.com.allerp.allbanks.view.Util;

public class CadContaPanel extends Util<Conta> {

	private static final long serialVersionUID = 1L;

	private Conta ctAux;

	@SpringBean(name = "agenciaService")
	private AgenciaService agenciaService;
	@SpringBean(name = "bancoService")
	private BancoService bancoService;

	private List<Agencia> agencias = agenciaService.findAll();
	private List<Banco> bancos = bancoService.findAll();
	private List<Status> ltStatus = Arrays.asList(Status.values());
	private List<Contas> contas = Arrays.asList(Contas.values());
	private final List<String> LIST_PES = Arrays.asList("Pessoa Física", "Pessoa Jurídica");

	private WebMarkupContainer divPf;
	private WebMarkupContainer divPj;
	private WebMarkupContainer divTitCt;

	private String selectedPes = LIST_PES.get(0);

	public CadContaPanel(String id, ModalWindow modal) {
		this(id, new Conta(), new Titular(), modal);
	}

	public CadContaPanel(String id, Conta conta, Titular titular, ModalWindow modal) {
		super(id);

		// CadTitularPanel cadTit = new CadTitularPanel("cadTit");

		CompoundPropertyModel<Conta> modelCadCt = new CompoundPropertyModel<Conta>(conta);

		final Form<Conta> formCadCt = new Form<Conta>("formCadCt", modelCadCt);

		ChoiceRenderer<Agencia> agRender = new ChoiceRenderer<Agencia>("codAg");
		DropDownChoice<Agencia> agencia = new DropDownChoice<Agencia>("agencia",
				new PropertyModel<Agencia>(conta, "agencia"), agencias, agRender);

		ChoiceRenderer<Banco> bcRender = new ChoiceRenderer<Banco>("nome");
		DropDownChoice<Banco> banco = new DropDownChoice<Banco>("banco", new PropertyModel<Banco>(conta, "banco"),
				bancos, bcRender);

		ChoiceRenderer<Contas> ctRender = new ChoiceRenderer<Contas>("text");
		DropDownChoice<Contas> dropConta = new DropDownChoice<Contas>("conta",
				new PropertyModel<Contas>(conta, "tipoConta"), contas, ctRender);
		TextField<String> numConta = new TextField<String>("numConta");

		ChoiceRenderer<Status> statusRender = new ChoiceRenderer<Status>("text");
		RadioChoice<Status> status = new RadioChoice<Status>("status", new PropertyModel<Status>(conta, "status"),
				ltStatus, statusRender);
		status.setSuffix("&nbsp;&nbsp;");

		formCadCt.add(agencia, banco, dropConta, numConta, status, btnCan("btnCanc", modal));

		divTitCt = new WebMarkupContainer("divTitCt");
		divTitCt.setOutputMarkupId(true);
		RadioChoice<String> radioTipoPes = new RadioChoice<String>("tipoPessoa",
				new PropertyModel<String>(this, "selectedPes"), LIST_PES);
		radioTipoPes.setSuffix("&nbsp;");

		divPf = new WebMarkupContainer("divPf");
		divPf.setVisible(false);
		divPf.setOutputMarkupId(true);

		divPj = new WebMarkupContainer("divPj");
		divPj.setVisible(false);
		divPj.setOutputMarkupId(true);

		if (selectedPes == LIST_PES.get(0)) {
			divPf.setVisible(true);
		}

		radioTipoPes.add(new AjaxFormChoiceComponentUpdatingBehavior() {

			private static final long serialVersionUID = 8410718552308495114L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (divPf.isVisible() && selectedPes == LIST_PES.get(1)) {
					divPf.setVisible(false);
					divPj.setVisible(true);
				} else {
					divPf.setVisible(true);
					divPj.setVisible(false);
				}
				target.add(divTitCt);
			}
		});

		titular.setTipoPessoa(selectedPes);
		conta.setTitular(titular);
		ctAux = conta;
		formCadCt.add(new AjaxButton("salvar") {

			private static final long serialVersionUID = -7557597292953590474L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				atualizaAoModificar(target, ctAux);

				ctAux = new Conta();
				formCadCt.clearInput();
				formCadCt.modelChanged();
				formCadCt.setModelObject(ctAux);
				target.add(formCadCt);
			}

		});

		divTitCt.add(radioTipoPes, formPf(), formPj(), divEnd());
		formCadCt.add(divTitCt);
		add(formCadCt);

	}

	private WebMarkupContainer formPf() {

		TextField<String> nome = new TextField<String>("titular.nome");
		TextField<String> rg = new TextField<String>("titular.rg");
		TextField<String> cpfCnpj = new TextField<String>("titular.cpfCnpj");
		DateTextField dtNasc = new DateTextField("titular.dtNasc");
		DatePicker datePicker = new DatePicker() {

			private static final long serialVersionUID = -3701264872352758583L;

			@Override
			protected String getIconStyle() {
				return "cursor: pointer; border: none;";
			}

		};
		datePicker.setShowOnFieldClick(true);
		datePicker.setAutoHide(true);
		dtNasc.setMarkupId("dtNasc");
		dtNasc.add(datePicker);
		TextField<String> celular = new TextField<String>("titular.celular");
		TextField<String> telefone = new TextField<String>("titular.telefone");

		divPf.add(nome, rg, cpfCnpj, celular, telefone, dtNasc);

		return divPf;

	}

	private WebMarkupContainer formPj() {

		TextField<String> nome = new TextField<String>("titular.nome");
		TextField<String> cpfCnpj = new TextField<String>("titular.cpfCnpj");
		TextField<String> ie = new TextField<String>("titular.ie");
		TextField<String> telefone = new TextField<String>("titular.telefone");

		divPj.add(nome, cpfCnpj, ie, telefone);

		return divPj;

	}

	private WebMarkupContainer divEnd() {
		WebMarkupContainer divEnd = new WebMarkupContainer("divEnd");

		TextField<String> rua = Util.textField("titular.endereco.rua");
		rua.setRequired(true);
		TextField<String> bairro = Util.textField("titular.endereco.bairro");
		bairro.setRequired(true);
		TextField<String> complemento = Util.textField("titular.endereco.complemento");
		NumberTextField<Integer> num = Util.numberTextField("titular.endereco.num");
		num.setRequired(true);
		TextField<String> pais = Util.textField("titular.endereco.pais");
		pais.setRequired(true);
		TextField<String> uf = Util.textField("titular.endereco.uf");
		uf.setRequired(true);
		TextField<String> cidade = Util.textField("titular.endereco.cidade");
		cidade.setRequired(true);
		TextField<String> cep = Util.textField("titular.endereco.cep");
		cep.setRequired(true);

		divEnd.add(rua, bairro, complemento, num, pais, uf, cidade, cep);

		return divEnd;

	}

	public String getSelectedPes() {
		return selectedPes;
	}

}
