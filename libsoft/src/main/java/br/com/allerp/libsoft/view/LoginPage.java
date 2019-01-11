package br.com.allerp.libsoft.view;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.libsoft.LibsoftSession;
import br.com.allerp.libsoft.entity.user.User;
import br.com.allerp.libsoft.service.user.UserService;
import br.com.allerp.libsoft.view.logado.DashboardPage;
import br.com.allerp.libsoft.view.templates.panels.FooterPanel;

public class LoginPage extends WebPage {

	private static final long serialVersionUID = 5035729844984918418L;

	private Component footer;

	@SpringBean(name = "userService")
	private UserService userService;

	private User user;

	public LoginPage() {
		footer = new FooterPanel("footer");

		user = new User();

		CompoundPropertyModel<User> userModel = new CompoundPropertyModel<User>(user);

		final Form<User> form = new Form<User>("formLogin", userModel);

		form.add(Add.link("homeLink", HomePage.class));
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
				System.out.println("Result: " + user.getUserAccess() + user.getPsw());
				if (userService.autenticaUser(user.getUserAccess(), user.getPsw(), "Bibliotecario")) {
					LibsoftSession.getInstance().setUser(user);
					setResponsePage(DashboardPage.class);
				} else {
					setResponsePage(LoginPage.class);
				}
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
		add(footer);

	}

}
