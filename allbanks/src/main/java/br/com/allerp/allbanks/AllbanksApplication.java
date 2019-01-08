package br.com.allerp.allbanks;

import org.apache.wicket.Page;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.parameter.UrlPathPageParametersEncoder;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import br.com.allerp.allbanks.view.Login;

public class AllbanksApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return Login.class;
	}
	
	@Override
	protected void init() {
		super.init();
		
		mount(new MountedMapper("/login", Login.class, new UrlPathPageParametersEncoder()));
		
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
	}

}
