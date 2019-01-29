package br.com.allerp.allbanks.view.cadastros.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.service.GenericService;
import br.com.allerp.allbanks.view.Util;

public class ExcluirPanel<T> extends Util<T> {

	private static final long serialVersionUID = -5809832993758487928L;

	@SpringBean(name = "service")
	private GenericService<T> service;
	
	public ExcluirPanel(String id, final T object, String titulo, String message) {
		super(id);
		add(new Label("confirma", "Exclusão de " + titulo + "!"));

		add(new Label("exMsg", message));

		add(new AjaxLink<T>("btn-confirma") {
			private static final long serialVersionUID = -6737235094619280702L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				service.delete(object);
				atualizaAoModificar(target, object);
			}
		});

		add(new AjaxLink<T>("btn-cancelar") {

			private static final long serialVersionUID = -2299143403487701927L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				atualizaAoModificar(target, object);
			}
		});
	}
	
	public void setService(GenericService<T> service) {
		this.service = service;
	}
}
