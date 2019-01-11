package br.com.allerp.libsoft;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.parameter.UrlPathPageParametersEncoder;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import br.com.allerp.libsoft.view.HomePage;
import br.com.allerp.libsoft.view.LoginPage;
import br.com.allerp.libsoft.view.logado.DashboardPage;
import br.com.allerp.libsoft.view.logado.UsuariosPanel;

public class LibsoftApplication extends WebApplication{

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}
	
	@Override
	protected void init() {
		super.init();
		
		mount(new MountedMapper("/home", HomePage.class, new UrlPathPageParametersEncoder()));
		mount(new MountedMapper("/login", LoginPage.class, new UrlPathPageParametersEncoder()));
		/*mount(new MountedMapper("/cadasto", CadastoPage.class, new UrlPathPageParametersEncoder()));*/
		mount(new MountedMapper("/dashboard", DashboardPage.class, new UrlPathPageParametersEncoder()));
		mount(new MountedMapper("/lista-usuarios", UsuariosPanel.class, new UrlPathPageParametersEncoder()));
		
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new LibsoftSession(request);
	}

}
