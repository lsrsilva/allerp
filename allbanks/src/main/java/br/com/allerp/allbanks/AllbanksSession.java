package br.com.allerp.allbanks;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import br.com.allerp.allbanks.entity.user.User;

public class AllbanksSession extends WebSession {

	private static final long serialVersionUID = -6443766019723662073L;

	private User user;

	public AllbanksSession(Request request) {
		super(request);
	}

	public static AllbanksSession get() {
		return (AllbanksSession) Session.get();
	}

	public synchronized User getUser() {
		return user;
	}

	public synchronized void setUser(User user) {
		this.user = user;
	}

	public synchronized boolean isLogedIn() {
		return (user != null);
	}

	@Override
	public void onInvalidate() {
		setUser(null);
	}

}
