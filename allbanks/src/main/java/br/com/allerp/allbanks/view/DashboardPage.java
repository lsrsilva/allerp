package br.com.allerp.allbanks.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;

import br.com.allerp.allbanks.view.cadastros.CadastrosPage;

public class DashboardPage extends SecuredBasePage {

	private static final long serialVersionUID = -4712703917780323193L;

	private String title;
	
	public DashboardPage() {
		
		title = "Dashboard";
		add(new Label("title", new PropertyModel<>(this, "title")));
		
		add(new Link<Object>("home") {

			private static final long serialVersionUID = 1288650964782456928L;

			@Override
			public void onClick() {
				setResponsePage(DashboardPage.class);
			}
			
		}, new AjaxLink<Object>("logout") {

			private static final long serialVersionUID = 1288650964782456928L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				getSessao().invalidate();
				getSessao().onInvalidate();
				setResponsePage(LoginPage.class);
			}

		});
		
		Link<Object> cadastros = Util.link("cadastros", CadastrosPage.class);
		
		if(getUserPerfil().equals("Gerente")) {
			cadastros.setVisible(true);
		} else {
			cadastros.setVisible(false);
		}
		
		add(cadastros);

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUserPerfil() {
		return getSessao().getUser().getPerfil().toString();
	}

}
