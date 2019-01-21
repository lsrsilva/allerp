package br.com.allerp.allbanks;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.parameter.UrlPathPageParametersEncoder;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import br.com.allerp.allbanks.view.DashboardPage;
import br.com.allerp.allbanks.view.LoginPage;
import br.com.allerp.allbanks.view.cadastros.CadastrosPage;
import br.com.allerp.allbanks.view.titular.TransacaoPage;

public class AllbanksApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return LoginPage.class;
	}
	
	@Override
	protected void init() {
		super.init();
		
		mount(new MountedMapper("/login", LoginPage.class, new UrlPathPageParametersEncoder()));
		mount(new MountedMapper("/dashboard", DashboardPage.class, new UrlPathPageParametersEncoder()));
		mount(new MountedMapper("/cadastro", CadastrosPage.class, new UrlPathPageParametersEncoder()));
		mount(new MountedMapper("/transacao", TransacaoPage.class, new UrlPathPageParametersEncoder()));
		
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new AllbanksSession(request);
	}
	
}
