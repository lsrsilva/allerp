package br.com.allerp.allbanks.view.cadastros.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.view.Util;

public class CadContaPanel extends Util<Conta> {

	private static final long serialVersionUID = 1L;

	private Conta ctAux;

	public CadContaPanel(String id, ModalWindow modal) {
		this(id, new Conta(), modal);
	}

	public CadContaPanel(String id, Conta conta, ModalWindow modal) {
		super(id);

		CompoundPropertyModel<Conta> modelCadCt = new CompoundPropertyModel<Conta>(conta);

		final Form<Conta> formCadCt = new Form<Conta>("formCadCt", modelCadCt);

		TextField<String> agencia = new TextField<String>("agencia");
		TextField<String> numConta = new TextField<String>("numConta");
		TextField<String> status = new TextField<String>("status");

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

		formCadCt.add(agencia, numConta, status, btnCan("btnCanc", conta, modal));
		add(formCadCt);

	}

}
