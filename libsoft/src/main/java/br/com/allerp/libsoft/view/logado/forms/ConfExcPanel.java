package br.com.allerp.libsoft.view.logado.forms;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.libsoft.entity.user.Bibliotecario;
import br.com.allerp.libsoft.service.user.BibliotecarioService;

public class ConfExcPanel extends Panel {

	private static final long serialVersionUID = -1146544499294817028L;

	@SpringBean(name = "bibliotecarioService")
	private BibliotecarioService bibliotecarioService;
	
	public ConfExcPanel(String id, final Bibliotecario pessoa) {
		super(id);
		add(new Label("confirma", "Excluir usuário"));
		
		add(new Label("exMsg", "Esta ação irá excluir o usuário \"" + pessoa.getUserAccess() + "\". Prosseguir?"));

		add(new AjaxLink<Bibliotecario>("btn-confirma") {
			private static final long serialVersionUID = -6737235094619280702L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				bibliotecarioService.delete(pessoa);
				updAfterDel(target, pessoa);
			}
		});

		add(new AjaxLink<Bibliotecario>("btn-cancelar") {

			private static final long serialVersionUID = -2299143403487701927L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				updAfterDel(target, pessoa);
			}
		});
	}

	public void updAfterDel(AjaxRequestTarget target, Bibliotecario pessoa) {

	}
	
}
