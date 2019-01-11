package br.com.allerp.libsoft;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import br.com.allerp.libsoft.entity.user.User;

public class LibsoftSession extends WebSession {

	private static final long serialVersionUID = 452616987877811425L;

	private User user;
	
	public LibsoftSession(Request request) {
		super(request);
	}

	public static LibsoftSession getInstance() {
		return (LibsoftSession) Session.get();
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
}
