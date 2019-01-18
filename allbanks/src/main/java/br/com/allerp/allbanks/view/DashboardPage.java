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
		cadastros.setVisible(false);
		Link<Object> transacao = Util.link("transacao", this.getClass());
		transacao.setVisible(false);

		if (getUserPerfil("Gerente")) {
			cadastros.setVisible(true);
		} else if (getUserPerfil("Titular")) {
			transacao.setVisible(true);
		} else {
		}

		add(cadastros, transacao);

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean getUserPerfil(String perfil) {
		return getSessao().getUser().getPerfil().toString().equals(perfil);
	}

}
