package br.com.allerp.allbanks.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;

import br.com.allerp.allbanks.entity.user.User;

public class Login extends WebPage {

	private static final long serialVersionUID = 2219243357019506101L;

	public Login() {

		User user = new User();

		CompoundPropertyModel<User> userModel = new CompoundPropertyModel<User>(user);

		final Form<User> form = new Form<User>("formLogin", userModel);

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

			}

		});

		form.add(new Link<Object>("btnCad") {

			private static final long serialVersionUID = -8073860944878508482L;

			@Override
			public void onClick() {
				// setResponsePage(CadastroPage.class);
			}

		});

		add(form);
	}

}
