package br.com.allerp.allbanks.view.cadastros.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import br.com.allerp.allbanks.entity.conta.Banco;
import br.com.allerp.allbanks.view.Util;

public class CadBancoPanel extends Util<Banco> {

	private static final long serialVersionUID = -5325867493473280585L;

	private Banco bancoAux;

	public CadBancoPanel(String id) {
		this(id, new Banco());
	}

	public CadBancoPanel(String id, Banco banco) {
		super(id);

		bancoAux = banco;

		CompoundPropertyModel<Banco> modelCadBc = new CompoundPropertyModel<Banco>(banco);

		final Form<Banco> formCadBc = new Form<Banco>("formBanco", modelCadBc);

		TextField<String> codCompensacao = new TextField<String>("codCompensacao");
		TextField<String> nome = new TextField<String>("nome");
		NumberTextField<Integer> codAg = new NumberTextField<Integer>("codAg");

		formCadBc.add(new AjaxButton("salvar") {

			private static final long serialVersionUID = 4850648916902160346L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				atualizaAoModificar(target, bancoAux);

				bancoAux = new Banco();
				formCadBc.clearInput();
				formCadBc.modelChanged();
				formCadBc.setModelObject(bancoAux);
				target.add(formCadBc);

			}

		});

		formCadBc.add(codCompensacao, codAg, nome, btnCan(id, banco, null));

		add(formCadBc);
	}

	private AjaxButton btnCadAg() {
		return new AjaxButton("btnCadAg") {

			private static final long serialVersionUID = 861357931906093534L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

			}

		};
	}

}
