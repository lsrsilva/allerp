package br.com.allerp.libsoft.view;

import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.libsoft.service.user.UserService;
import br.com.allerp.libsoft.view.logado.forms.CadastroForm;
import br.com.allerp.libsoft.view.templates.TemplatePage;

public class HomePage extends TemplatePage {

	private static final long serialVersionUID = 2131846526644850881L;

	@SpringBean(name = "userService")
	private UserService userService;

	public HomePage() {
		setTitulo("Home");

		CadastroForm cadForm = new CadastroForm("cad");

		add(cadForm);
	}

}
