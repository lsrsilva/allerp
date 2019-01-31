package br.com.allerp.allbanks.view.cadastros.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.conta.Banco;
import br.com.allerp.allbanks.service.conta.BancoService;
import br.com.allerp.allbanks.view.Util;
import br.com.allerp.allbanks.view.panel.NotificacaoPanel;

public class CadBancoPanel extends Util<Banco> {

	private static final long serialVersionUID = -5325867493473280585L;

	private Banco bancoAux;
	protected boolean valido;
	
	@SpringBean(name="bancoService")
	private BancoService bancoService;

	public CadBancoPanel(String id, ModalWindow modal) {
		this(id, new Banco(), modal);
	}

	public CadBancoPanel(String id, Banco banco, final ModalWindow modal) {
		super(id);
		
		final NotificacaoPanel bancoNotificacao = new NotificacaoPanel("bancoNotificacao");
		
		CompoundPropertyModel<Banco> modelCadBc = new CompoundPropertyModel<Banco>(banco);

		final Form<Banco> formCadBc = new Form<Banco>("formCadBc", modelCadBc);

		TextField<String> codCompensacao = new TextField<String>("codCompensacao");
		TextField<String> nome = new TextField<String>("nome");

		bancoAux = banco;
		formCadBc.add(new AjaxButton("salvar") {

			private static final long serialVersionUID = 4850648916902160346L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				valido = bancoService.camposSaoValidos(bancoAux);
				target.appendJavaScript("mostraTabCad('bancoCad');");
				
				if(valido) {
					for(String mensagem : bancoService.getMensagens()) {
						bancoNotificacao.success(mensagem);
					}
					bancoNotificacao.refresh(target);
					
					atualizaAoModificar(target, bancoAux);
	
					bancoAux = new Banco();
					formCadBc.clearInput();
					formCadBc.modelChanged();
					formCadBc.setModelObject(bancoAux);
					target.add(formCadBc);
					bancoService.getMensagens().clear();
					modal.close(target);
				} else {
					for(String mensagem : bancoService.getMensagens()) {
						bancoNotificacao.error(mensagem);
					}
					bancoNotificacao.refresh(target);
					bancoService.getMensagens().clear();
				}

			}

		});

		formCadBc.add(codCompensacao, nome, btnCan("btnCanc", modal));

		add(formCadBc, bancoNotificacao);
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
