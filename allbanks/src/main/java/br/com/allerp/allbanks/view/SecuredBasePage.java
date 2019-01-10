package br.com.allerp.allbanks.view;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;

import br.com.allerp.allbanks.AllbanksSession;

public class SecuredBasePage extends WebPage {

	private static final long serialVersionUID = 9071207182185957571L;

	public SecuredBasePage() {
		super();
		verifyAccess();
	}

	protected void verifyAccess() {
		// Redirect to Login page on invalid access.
		if (!isLogedIn()) {
			throw new RestartResponseAtInterceptPageException(LoginPage.class);
		}
	}

	protected boolean isLogedIn() {
		return getSessao().isLogedIn();
	}
	
	public AllbanksSession getSessao() {
		return ((AllbanksSession) super.getSession());
	}

}
