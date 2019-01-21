package br.com.allerp.allbanks.view.titular;

import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.service.conta.TitularService;
import br.com.allerp.allbanks.view.DashboardPage;

public class TransacaoPage extends DashboardPage {
	
	private static final long serialVersionUID = -837099547910614195L;

	@SpringBean(name = "titularService")
	private TitularService titularService;
	
	Conta conta = new Conta();
	
	public TransacaoPage() {
		setTitle("Transações");
		if(!getUserPerfil("Titular")) {
			setResponsePage(DashboardPage.class);
		}
		
	}

}
