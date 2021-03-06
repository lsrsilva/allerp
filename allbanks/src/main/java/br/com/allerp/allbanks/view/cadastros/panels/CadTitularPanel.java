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
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.view.Util;

public class CadTitularPanel extends Util<Titular> {

	private static final long serialVersionUID = -6106665929906304243L;

	private Titular titularAux;

	private WebMarkupContainer divPf;
	private WebMarkupContainer divPj;

	private final List<String> LIST_PES = Arrays.asList("Pessoa Física", "Pessoa Jurídica");

	private String selectedPes = LIST_PES.get(0);

	public CadTitularPanel(String id, ModalWindow modal) {
		this(id, new Titular(), modal);
	}
	
	public CadTitularPanel(String id, Titular titular, ModalWindow modal) {
		super(id);

		CompoundPropertyModel<Titular> modelCadTit = new CompoundPropertyModel<Titular>(titular);

		final Form<Titular> formCadTit = new Form<Titular>("formCadTit", modelCadTit);
		formCadTit.setOutputMarkupId(true);

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
				target.add(formCadTit);
			}
		});

		titularAux = titular;
		formCadTit.add(new AjaxButton("salvar") {

			private static final long serialVersionUID = -7557597292953590474L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				target.appendJavaScript("mostraTabCad('titularCad')");
				atualizaAoModificar(target, titularAux);

				titularAux = new Titular();
				formCadTit.clearInput();
				formCadTit.modelChanged();
				formCadTit.setModelObject(titularAux);
				target.add(formCadTit);
			}

		});

		formCadTit.add(radioTipoPes, formPf(), formPj(), divEnd(), btnCan("btnCanc", modal));
		add(formCadTit);

	}

	private WebMarkupContainer formPf() {

		TextField<String> nome = new TextField<String>("nome");
		TextField<String> rg = new TextField<String>("rg");
		TextField<String> cpfCnpj = new TextField<String>("cpfCnpj");
		DateTextField dtNasc = new DateTextField("dtNasc");
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
		TextField<String> celular = new TextField<String>("celular");
		TextField<String> telefone = new TextField<String>("telefone");

		divPf.add(nome, rg, cpfCnpj, celular, telefone, dtNasc);

		return divPf;

	}

	private WebMarkupContainer formPj() {

		TextField<String> nome = new TextField<String>("nome");
		TextField<String> cpfCnpj = new TextField<String>("cpfCnpj");
		TextField<String> ie = new TextField<String>("ie");
		TextField<String> telefone = new TextField<String>("telefone");

		divPj.add(nome, cpfCnpj, ie, telefone);

		return divPj;

	}
	
	private WebMarkupContainer divEnd() {
		WebMarkupContainer divEnd = new WebMarkupContainer("divEnd");
		
		TextField<String> rua = Util.textField("endereco.rua");
		rua.setRequired(true);
		TextField<String> bairro = Util.textField("endereco.bairro");
		bairro.setRequired(true);
		TextField<String> complemento = Util.textField("endereco.complemento");
		NumberTextField<Integer> num = Util.numberTextField("endereco.num");
		num.setRequired(true);
		TextField<String> pais = Util.textField("endereco.pais");
		pais.setRequired(true);
		TextField<String> uf = Util.textField("endereco.uf");
		uf.setRequired(true);
		TextField<String> cidade = Util.textField("endereco.cidade");
		cidade.setRequired(true);
		TextField<String> cep = Util.textField("endereco.cep");
		cep.setRequired(true);
		
		divEnd.add(rua, bairro, complemento, num, pais, uf, cidade, cep);
		
		return divEnd;
		
	}
	
	public String getSelectedPes() {
		return selectedPes;
	}

}
