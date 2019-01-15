package br.com.allerp.allbanks.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import br.com.allerp.allbanks.view.cadastros.CadastrosPage;

public class DashboardPage extends SecuredBasePage {

	private static final long serialVersionUID = -4712703917780323193L;

	private String title;

	public DashboardPage() {
		
		title = "Dashboard";
		add(new Label("title", new PropertyModel<>(this, "title")));
		
		add(new AjaxLink<Object>("logout") {

			private static final long serialVersionUID = 1288650964782456928L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				getSessao().invalidate();
				getSessao().onInvalidate();
				setResponsePage(LoginPage.class);
			}

		}, Util.link("cadastros", CadastrosPage.class));
		
		

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
