package br.com.allerp.allbanks.view.cadastros.panels;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.enums.Perfis;
import br.com.allerp.allbanks.entity.user.User;
import br.com.allerp.allbanks.service.UserService;
import br.com.allerp.allbanks.view.Util;
import br.com.allerp.allbanks.view.panel.NotificacaoPanel;

public class CadUserPanel extends Util<User> {

	private static final long serialVersionUID = -449253488613140249L;

	private User userAux;
	
	protected boolean valido;

	@SpringBean(name = "userService")
	private UserService userService;

	protected List<Perfis> perfis = Arrays.asList(Perfis.values());

	private NotificacaoPanel userNotificacao;

	public CadUserPanel(String id, ModalWindow modal) {
		this(id, new User(), modal, "Cadastro");
	}

	public CadUserPanel(String id, User user, final ModalWindow modal, String titulo) {
		super(id);
		add(new Label("tituloCad", Model.of(titulo + "de Usuário.")));
		userNotificacao = userNotificacao();
		
		CompoundPropertyModel<User> modelCadUs = new CompoundPropertyModel<User>(user);

		final Form<User> formCadUs = new Form<User>("formCadUs", modelCadUs);

		TextField<String> userAccess = new TextField<String>("userAccess");
		EmailTextField email = new EmailTextField("email");

		userAux = user;
		formCadUs.add(new AjaxButton("salvar") {

			private static final long serialVersionUID = -7557597292953590474L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				valido = userService.camposSaoValidos(userAux);
				target.appendJavaScript("mostraTabCad('userCad');");
				
				if (valido) {
					for (String mensagem : userService.getMensagens()) {
						success(mensagem);
					}
					userNotificacao.refresh(target);

					atualizaAoModificar(target, userAux);

					userAux = new User();
					formCadUs.clearInput();
					formCadUs.modelChanged();
					formCadUs.setModelObject(userAux);

					userService.getMensagens().clear();
					modal.close(target);
					target.add(formCadUs);
				} else {
					for (String mensagem : userService.getMensagens()) {
						userNotificacao.error(mensagem);
					}

					userNotificacao.refresh(target);
					userService.getMensagens().clear();
				}
			}
//
//			@Override
//			public void onError(AjaxRequestTarget target, Form<?> form) {
//				if (!valido) {
//					for (String mensagem : userService.getMensagens()) {
//						userNotificacao.error(mensagem);
//					}
//
//					userNotificacao.refresh(target);
//					userService.getMensagens().clear();
//				}
//			}

		});

		formCadUs.add(userAccess, email, psw(), dropPerfil(), btnCan("btnCanc", modal));

		add(formCadUs, userNotificacao());

	}

	private NotificacaoPanel userNotificacao() {

		userNotificacao = new NotificacaoPanel("userNotificacao");

		return userNotificacao;
	}
	
	protected DropDownChoice<Perfis> dropPerfil() {
		DropDownChoice<Perfis> perfil = new DropDownChoice<Perfis>("perfil", perfis) {

			private static final long serialVersionUID = 7194271655576226917L;

			@Override
			protected boolean isDisabled(Perfis object, int index, String selected) {
				if (object == Perfis.TITULAR) {
					return true;
				}
				return super.isDisabled(object, index, selected);
			}
			
			
		};
		
		return perfil;
	}
	
	protected PasswordTextField psw() {
		PasswordTextField psw = new PasswordTextField("psw");
		psw.setRequired(false);
		
		return psw;
	}

}
