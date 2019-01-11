package br.com.allerp.libsoft.view.logado.forms;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.libsoft.entity.user.Bibliotecario;
import br.com.allerp.libsoft.entity.user.Doador;
import br.com.allerp.libsoft.entity.user.PessoaFisica;
import br.com.allerp.libsoft.entity.user.Reservista;
import br.com.allerp.libsoft.entity.user.User;
import br.com.allerp.libsoft.view.Add;

/*NÃO ESQUECER DO MÉTODO formPJ() para verificar a mudança*/

public class CadastroForm extends Panel {

	private static final long serialVersionUID = -8602331656168864676L;

	private DropDownChoice<String> dropPerfil;
	private Form<Bibliotecario> cadForm;
	private WebMarkupContainer divPF;
	private WebMarkupContainer divPJ;

	private final List<String> LIST_SX = Arrays.asList("Masculino", "Feminino");
	private final List<String> LIST_PES = Arrays.asList("Pessoa Física", "Pessoa Jurídica");
	private final List<String> LIST_PERFIS = Arrays.asList("Perfil do Usuário", "Bibliotecario", "Reservista");

	private String pessoaSelected = LIST_PES.get(0);
	private String perfilSelected = "";
	
	private Bibliotecario bibliotecario;
//	@SpringBean(name = "doador")
	private Doador doador;
//	@SpringBean(name = "reservista")
	private Reservista reservista;

/*	@SpringBean(name = "bibliotecarioService")
	private BibliotecarioService bibliotecarioService;*/
	/*@SpringBean(name = "bibliotecarioService")
	private BibliotecarioService bibliotecarioService;
	@SpringBean(name = "doadorService")
	private DoadorService doadorService;
	@SpringBean(name = "reservistaoService")
	private ReservistaService reservistaService;*/
	
	public CadastroForm(String id) {
		this(id, new Bibliotecario());
	}
	
	public CadastroForm(String id, Bibliotecario user) {
		super(id);
		
		bibliotecario = user;

		divPF = Add.markupContainer("divPF");
		divPF.setVisible(false);
		divPF.setOutputMarkupId(true);

		divPJ = Add.markupContainer("divPJ");
		divPJ.setVisible(false);

		RadioChoice<String> tipoPessoa = new RadioChoice<String>("tipoPessoa",
				new PropertyModel<String>(this, "pessoaSelected"), LIST_PES);
		tipoPessoa.setSuffix("");

		CompoundPropertyModel<Bibliotecario> userModel = new CompoundPropertyModel<Bibliotecario>(user);

		cadForm = new Form<Bibliotecario>("cadForm", userModel);
		cadForm.setOutputMarkupId(true);

		/************* Muda o tipo de formulário entre PF e PJ **************/
		if (pessoaSelected == LIST_PES.get(0)) {
			divPF.setVisible(true);
		}

		tipoPessoa.add(new AjaxFormChoiceComponentUpdatingBehavior() {

			private static final long serialVersionUID = 8410718552308495114L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (divPF.isVisible() && pessoaSelected != LIST_PES.get(0)) {
					divPF.setVisible(false);
					divPJ.setVisible(true);
				} else {
					divPF.setVisible(true);
					divPJ.setVisible(false);
				}
				target.add(cadForm);
			}
		});
		/********************************************************************/

		/********************** Campos comuns ***********************/
		WebMarkupContainer divComum = Add.markupContainer("divComum");

		TextField<String> userAccess = Add.textField("userAccess");
		EmailTextField email = Add.emailTextField("email");
		PasswordTextField psw = Add.passwordTextField("psw");
		TextField<String> telefone = Add.textField("telefone");

		TextField<String> rua = Add.textField("rua");
		rua.setRequired(true);
		TextField<String> bairro = Add.textField("bairro");
		bairro.setRequired(true);
		TextField<String> complemento = Add.textField("complemento");
		NumberTextField<Integer> num = Add.numberTextField("num");
		num.setRequired(true);
		TextField<String> pais = Add.textField("pais");
		pais.setRequired(true);
		TextField<String> uf = Add.textField("uf");
		uf.setRequired(true);
		TextField<String> cidade = Add.textField("cidade");
		cidade.setRequired(true);
		TextField<String> cep = Add.textField("cep");
		cep.setRequired(true);

		divComum.add(userAccess, email, psw, telefone, rua, bairro, complemento, num, pais, uf, cidade, cep);
		/************************************************************/

		cadForm.add(divComum, formPF()/* , formPJ(cadForm) */);
		cadForm.add(tipoPessoa);
		cadForm.add(new AjaxButton("btnCad") {

			private static final long serialVersionUID = 3295849037705762442L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				executaAoSalvar(target, bibliotecario);
				
				bibliotecario = new Bibliotecario();
				cadForm.clearInput();
				cadForm.modelChanged();
				cadForm.setModelObject(bibliotecario);
				
				target.add(cadForm);
			}

		}, Add.ajaxButton("btnCanc", null));
		add(cadForm);

	}
	
	public void executaAoSalvar(AjaxRequestTarget target, Bibliotecario bibliotecario) {
	}
	
	/*
	 * public String getPessoaSelected() { return pessoaSelected; }
	 * 
	 * public String getPerfilSelected() { return perfilSelected; }
	 */
	private WebMarkupContainer formPF() {

		TextField<String> nome = Add.textField("nome");
		nome.setRequired(true);
		TextField<String> rg = Add.textField("rg");
		rg.setRequired(true);
		TextField<String> cpf = Add.textField("cpf");
		cpf.setRequired(true);

		/*dropPerfil = new DropDownChoice<String>("perfis", new PropertyModel<String>(this, "perfilSelected"),
				LIST_PERFIS);
		dropPerfil.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			private static final long serialVersionUID = -8054927346100024393L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				switch (perfilSelected) {
				case "Bibliotecario":
					bibliotecario = new Bibliotecario();
					cadForm.setDefaultModel(new CompoundPropertyModel<Bibliotecario>(bibliotecario));
					cadForm.modelChanged();
					break;
					
				case "Doador":
					doador = new Doador();
					cadForm.setDefaultModel(new CompoundPropertyModel<Doador>(doador));
					cadForm.modelChanged();

				case "Reservista":
					reservista = new Reservista();
					cadForm.setDefaultModelObject(reservista);
					cadForm.modelChanged();
					break;
				default:
					break;
				}

			}
		});*/

	//		final WebMarkupContainer divDrop = Add.markupContainer("divDrop");
	//	divDrop.add(dropPerfil);

		TextField<String> celular = Add.textField("celular");
		celular.setRequired(true);
		RadioChoice<String> radioSx = new RadioChoice<String>("sexo", LIST_SX);
		radioSx.setSuffix("");
		DateTextField dtNasc = new DateTextField("dtNasc");
		divPF.add(dtNasc);
		DatePicker datePicker = new DatePicker() {

			private static final long serialVersionUID = -3701264872352758583L;

			@Override
			protected String getIconStyle() {
				return "cursor: pointer; border: none; margin-top: 15px;";
			}

		};
		datePicker.setShowOnFieldClick(true);
		datePicker.setAutoHide(true);
		dtNasc.setMarkupId("dtNasc");
		dtNasc.add(datePicker);

		divPF.add(nome, rg, cpf, celular, radioSx, dtNasc/*, divDrop*/);

		return divPF;

	}

	private WebMarkupContainer formPJ(Form<Bibliotecario> form) {

		doador = new Doador();
		/*
		 * CompoundPropertyModel<Doador> doadorModel = new
		 * CompoundPropertyModel<Doador>(doador); form = new Form<Doador>("form",
		 * doadorModel);
		 */
		form.setDefaultModelObject(doador);
		form.modelChanged();

		TextField<String> cnpj = new TextField<String>("cnpj");
		cnpj.setRequired(true);
		TextField<String> razaoSocial = new TextField<String>("razaoSocial");
		razaoSocial.setRequired(true);

		divPJ.add(cnpj, razaoSocial);

		return divPJ;
	}

/*	private <T> AjaxButton btnCad(Bibliotecario bibliotecario) {
	
		return new AjaxButton("btnCad") {

			private static final long serialVersionUID = 3295849037705762442L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				executaAoSalvar(target, bibliotecario);
				
				bibliotecario = new Bibliotecario();
				cadForm.clearInput();
				cadForm.modelChanged();
				cadForm.setModelObject(bibliotecario);
				
				target.add(cadForm);
			}

		};
	}*/

}