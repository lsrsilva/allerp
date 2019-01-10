package br.com.allerp.allbanks.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;

public class DashboardPage extends SecuredBasePage {

	private static final long serialVersionUID = -4712703917780323193L;

	public DashboardPage() {
		
		add(new AjaxLink<Object>("logout") {

			private static final long serialVersionUID = 1288650964782456928L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				getSessao().invalidate();
				getSessao().onInvalidate();
				setResponsePage(LoginPage.class);
			}
			
		});
		
	}
	
}
