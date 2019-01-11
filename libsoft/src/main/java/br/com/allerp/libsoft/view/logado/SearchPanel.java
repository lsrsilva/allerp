package br.com.allerp.libsoft.view.logado;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.libsoft.entity.user.Bibliotecario;
import br.com.allerp.libsoft.service.user.BibliotecarioService;

public class SearchPanel extends Panel {

	private static final long serialVersionUID = -5093516820589011437L;

	@SpringBean(name = "bibliotecarioService")
	private BibliotecarioService bibliotecarioService;
	
	private List<Bibliotecario> bibliotecarios;
	
	public SearchPanel(String id) {
		this(id, null);
	}

	public SearchPanel(String id, List<Bibliotecario> listUsers) {
		super(id);
		
		final Bibliotecario biblio = new Bibliotecario();
		CompoundPropertyModel<Bibliotecario> modelPF = new CompoundPropertyModel<Bibliotecario>(biblio);
		Form<Bibliotecario> searchForm = new Form<Bibliotecario>("searchForm", modelPF);
		final TextField<String> searchCPF = new TextField<String>("cpf");
		final TextField<String> searchUAcss = new TextField<String>("userAccess");

		bibliotecarios = listUsers;
		searchForm.add(new AjaxButton("search") {

			private static final long serialVersionUID = 3865918601254958016L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(searchCPF != null && searchUAcss != null || biblio.getCpf().equals("") || biblio.getUserAccess().equals("")) {
					bibliotecarios = bibliotecarioService.search(biblio.getCpf(), biblio.getUserAccess());
				}
				atualiza(target);
			}

		});

		searchForm.add(searchCPF, searchUAcss);
		add(searchForm);

	}
	
	public void atualiza(AjaxRequestTarget target) {
	}

	public List<Bibliotecario> getBibliotecarios() {
		return bibliotecarios;
	}
	
}