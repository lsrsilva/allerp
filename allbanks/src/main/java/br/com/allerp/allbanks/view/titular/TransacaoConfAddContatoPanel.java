package br.com.allerp.allbanks.view.titular;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Contato;
import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.service.conta.ContatoService;
import br.com.allerp.allbanks.view.Util;

public class TransacaoConfAddContatoPanel extends Util<Conta> {

	private static final long serialVersionUID = -816662741800789145L;

	@SpringBean(name = "contatoService")
	private ContatoService contatoService;

	public TransacaoConfAddContatoPanel(String id, Contato contato, Titular titular, Conta contaCt, ModalWindow modal) {
		super(id);

		lblAddContato(id, contato, titular, contaCt, modal);
	}

	private void lblAddContato(String id, final Contato contato, final Titular titular, final Conta contaCt, final ModalWindow modal) {
		add(new Label("lblConfirma", Model
				.of("Adicionar a conta de " + contaCt.getTitular().getNome() + " em sua lista de contatos?")));
		add(new AjaxLink<Object>("confirma") {

			private static final long serialVersionUID = 8378657139492580114L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				contato.setCtContato(contaCt);
				contato.setTitular(titular);
				contatoService.merge(contato);
				modal.close(target);
			}

		});
		add(new AjaxLink<Object>("cancela") {

			private static final long serialVersionUID = 8378657139492580114L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modal.close(target);
			}

		});
	}

}
