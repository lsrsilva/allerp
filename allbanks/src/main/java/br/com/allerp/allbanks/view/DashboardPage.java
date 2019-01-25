package br.com.allerp.allbanks.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;

import br.com.allerp.allbanks.view.cadastros.CadastrosPage;
import br.com.allerp.allbanks.view.titular.ContatosPage;
import br.com.allerp.allbanks.view.titular.TransacaoPage;

public class DashboardPage extends SecuredBasePage {

	private static final long serialVersionUID = -4712703917780323193L;

	private Link<Object> transacao;
	private Link<Object> contatos;

	public DashboardPage() {

		setTitle("Dashboard");
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
		transacao = Util.link("transacao", TransacaoPage.class);
		transacao.setVisible(false);
		contatos = Util.link("contatos", ContatosPage.class);
		contatos.setVisible(false);

		if (getUserPerfil("Gerente")) {
			cadastros.setVisible(true);
		} else if (getUserPerfil("Titular")) {
			// Verá o saldo o extrato dos últimos 30 dias
			titular();
		} else {
			setResponsePage(this);
		}

		add(cadastros, transacao, contatos);

	}

	public boolean getUserPerfil(String perfil) {
		return getSessao().getUser().getPerfil().toString().equals(perfil);
	}

	private void titular() {
		transacao.setVisible(true);
		contatos.setVisible(true);
	}

}
