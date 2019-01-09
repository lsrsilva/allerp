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

public class Login extends SecuredBasePage {

	private static final long serialVersionUID = 2219243357019506101L;
	
	@SpringBean(name = "userService")
	private UserService userService;
	public Login() {

		final User<?> user = new User<>();
		
		CompoundPropertyModel<?> userModel = new CompoundPropertyModel<>(user);
		
		final Form<?> form = new Form<>("formLogin", userModel);

		final TextField<String> email = new TextField<String>("userAccess");
		email.setRequired(true);
		final PasswordTextField senha = new PasswordTextField("psw");
		senha.setRequired(true);

		form.add(email);
		form.add(senha);

		form.add(new AjaxButton("btnLogin") {

			private static final long serialVersionUID = -8073860944878508482L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				System.out.println(email.getValue() + " " + senha.getValue());
				if(userService.autentica(email.getValue(), senha.getValue())) {
					setResponsePage(DashboardPage.class);
				} else {
					error("Usuário ou senha inválidos");
				}
			}

		});
		
		add(form);
	}
	
	@Override
	protected void verifyAccess() {
		
		if(isLogedIn()) {
			setResponsePage(DashboardPage.class);
		}
	}

}
