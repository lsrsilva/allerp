package br.com.allerp.libsoft.view.logado;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import br.com.allerp.libsoft.view.Add;

public class DashboardPage extends WebPage {

	private static final long serialVersionUID = 8434882470580565603L;

	private String title;

	public DashboardPage() {
		title = "Dashboard";
		add(new Label("title", new PropertyModel<>(this, "title")));

		add(Add.link("listUser", UsuariosPanel.class));
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
