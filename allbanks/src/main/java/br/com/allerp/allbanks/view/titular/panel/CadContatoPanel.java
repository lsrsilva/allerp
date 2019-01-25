package br.com.allerp.allbanks.view.titular.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Contato;
import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.exceptions.FeedbackException;
import br.com.allerp.allbanks.service.conta.ContaService;
import br.com.allerp.allbanks.service.conta.ContatoService;
import br.com.allerp.allbanks.view.Util;

public class CadContatoPanel extends Util<Contato> {

	private static final long serialVersionUID = -7836788990979079621L;

	@SpringBean(name = "contatoService")
	private ContatoService contatoService;
	@SpringBean(name = "contaService")
	private ContaService contaService;

	private Contato contatoAux;

	private String nomeTitu;
	private Integer nuConta;

	public CadContatoPanel(String id, Titular titular, ModalWindow modal) {
		this(id, new Contato(), titular, modal);
	}

	public CadContatoPanel(String id, final Contato contato, final Titular titular, final ModalWindow modal) {
		super(id);

		final Form<Contato> formContato = new Form<Contato>("formContato");
		TextField<String> nomeTitular = new TextField<String>("titular", new PropertyModel<String>(this, "nomeTitu"));
		TextField<Integer> numConta = new TextField<Integer>("numConta", new PropertyModel<Integer>(this, "nuConta"));

		formContato.add(nomeTitular, numConta);

		contatoAux = contato;
		formContato.add(new AjaxLink<Contato>("salvar") {

			private static final long serialVersionUID = -3000003489718453819L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				try {
					Conta ctContato = contaService.verifExisteConta(nomeTitu, nuConta);
					contatoService.saveOrUpdate(contatoAux, ctContato, titular);
				} catch (FeedbackException e) {
					e.printStackTrace();
				}
				atualizaAoModificar(target, contatoAux);

				contatoAux = new Contato();
				formContato.clearInput();

				target.add(formContato);
			}

		}, new AjaxButton("cancelar") {

			private static final long serialVersionUID = 8626657603254788029L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				modal.close(target);
			}

		});
		add(formContato);

	}

	public Integer getNuConta() {
		return nuConta;
	}

	public void setNuConta(Integer nuConta) {
		this.nuConta = nuConta;
	}

	public String getNomeTitu() {
		return nomeTitu;
	}

	public void setNomeTitu(String nomeTitu) {
		this.nomeTitu = nomeTitu;
	}

}
