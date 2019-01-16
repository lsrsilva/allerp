package br.com.allerp.allbanks.view.cadastros.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import br.com.allerp.allbanks.entity.conta.Banco;
import br.com.allerp.allbanks.view.Util;

public class CadBancoPanel extends Util<Banco> {

	private static final long serialVersionUID = -5325867493473280585L;

	private Banco bancoAux;

	public CadBancoPanel(String id, ModalWindow modal) {
		this(id, new Banco(), modal);
	}

	public CadBancoPanel(String id, Banco banco, ModalWindow modal) {
		super(id);

		CompoundPropertyModel<Banco> modelCadBc = new CompoundPropertyModel<Banco>(banco);

		final Form<Banco> formCadBc = new Form<Banco>("formCadBc", modelCadBc);

		TextField<String> codCompensacao = new TextField<String>("codCompensacao");
		TextField<String> nome = new TextField<String>("nome");

		bancoAux = banco;
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

		formCadBc.add(codCompensacao, nome, btnCan("btnCanc", banco, modal));

		add(formCadBc);
	}
	/*
	Botão para cadastrar agência no modal do banco
	private AjaxButton btnCadAg() {
		return new AjaxButton("btnCadAg") {

			private static final long serialVersionUID = 861357931906093534L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

			}

		};
	}*/

}
