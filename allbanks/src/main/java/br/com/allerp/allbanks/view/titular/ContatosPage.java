package br.com.allerp.allbanks.view.titular;

import br.com.allerp.allbanks.view.DashboardPage;

public class ContatosPage extends DashboardPage{

	private static final long serialVersionUID = 1655107947145982466L;
	
	public ContatosPage() {
		if(!getUserPerfil("Titular")) {
			setResponsePage(DashboardPage.class);
		}
	}
	
}