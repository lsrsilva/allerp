package br.com.allerp.allbanks.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.user.User;
import br.com.allerp.allbanks.service.UserService;
import br.com.allerp.allbanks.service.conta.ContaService;
import br.com.allerp.allbanks.view.panel.NotificacaoPanel;

public class LoginPage extends SecuredBasePage {

	private static final long serialVersionUID = 2219243357019506101L;

	@SpringBean(name = "userService")
	private UserService userService;

	@SpringBean(name = "contaService")
	ContaService conta;

	public LoginPage() {

		final User user = new User();

		CompoundPropertyModel<?> userModel = new CompoundPropertyModel<>(user);

		final Form<?> form = new Form<>("formLogin", userModel);

		final TextField<String> userAccess = new TextField<String>("userAccess");
		//userAccess.setRequired(true);
		final PasswordTextField senha = new PasswordTextField("psw");
		senha.setRequired(false);

		final NotificacaoPanel feedback = new NotificacaoPanel("feedback");

		form.add(userAccess);
		form.add(senha);

		form.add(new AjaxButton("btnLogin") {

			private static final long serialVersionUID = -8073860944878508482L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				
				if (userService.existeUser(userAccess.getValue(), senha.getValue())) {
					setResponsePage(DashboardPage.class);
				} else {
					for (String mensagem : userService.getMensagens()) {
						error(mensagem);
					}
					feedback.refresh(target);
					userService.getMensagens().clear();
				}
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				if(!userService.existeUser(userAccess.getValue(), senha.getValue())) {
					for (String mensagem : userService.getMensagens()) {
						error(mensagem);
					}
				}
				feedback.refresh(target);
				userService.getMensagens().clear();
			}

		});

		add(form, feedback);
	}

	@Override
	protected void verifyAccess() {

		if (isLogedIn()) {
			setResponsePage(DashboardPage.class);
		}
	}

}
