package br.com.allerp.allbanks.view.cadastros.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import br.com.allerp.allbanks.entity.user.User;
import br.com.allerp.allbanks.view.Util;

public class CadUserPanel extends Util<User> {

	private static final long serialVersionUID = -449253488613140249L;

	private User userAux;

	public CadUserPanel(String id, ModalWindow modal) {
		this(id, new User(), modal);
	}

	public CadUserPanel(String id, User user, ModalWindow modal) {
		super(id);

		CompoundPropertyModel<User> modelCadAg = new CompoundPropertyModel<User>(user);

		final Form<User> formCadUs = new Form<User>("formCadUs", modelCadAg);

		TextField<String> userAccess = new TextField<String>("userAccess");
		EmailTextField email = new EmailTextField("email");
		PasswordTextField psw = new PasswordTextField("psw");

		userAux = user;
		formCadUs.add(new AjaxButton("salvar") {

			private static final long serialVersionUID = -7557597292953590474L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				atualizaAoModificar(target, userAux);

				userAux = new User();
				formCadUs.clearInput();
				formCadUs.modelChanged();
				formCadUs.setModelObject(userAux);
				target.add(formCadUs);
			}

		});

		formCadUs.add(userAccess, email, psw, btnCan("btnCanc", user, modal));

		add(formCadUs);

	}

}
