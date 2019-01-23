package br.com.allerp.allbanks.view.titular;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Contato;
import br.com.allerp.allbanks.service.conta.ContatoService;
import br.com.allerp.allbanks.view.Util;

public class AddContatoPanel extends Util<Conta> {

	private static final long serialVersionUID = -816662741800789145L;

	@SpringBean(name = "contatoService")
	private ContatoService contatoService;

	public AddContatoPanel(String id, Contato contato, Conta conta, ModalWindow modal) {
		super(id);

		lblAddContato(contato, conta, modal);
	}

	private void lblAddContato(final Contato contato, final Conta conta, final ModalWindow modal) {
		add(new Label("lblConfirma", Model
				.of("Deseja adicionar a conta de " + conta.getTitular().getNome() + " em sua lista de contatos?")));
		add(new AjaxButton("confirma") {

			private static final long serialVersionUID = 8378657139492580114L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				contatoService.saveOrUpdate(contato, conta);
				modal.close(target);
			}

		});
		add(new AjaxButton("cancela") {

			private static final long serialVersionUID = 8378657139492580114L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				modal.close(target);
			}

		});
	}

}
