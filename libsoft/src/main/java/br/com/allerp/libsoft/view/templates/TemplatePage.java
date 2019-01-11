package br.com.allerp.libsoft.view.templates;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import br.com.allerp.libsoft.view.templates.panels.FooterPanel;
import br.com.allerp.libsoft.view.templates.panels.HeaderPanel;

public class TemplatePage extends WebPage {

	private static final long serialVersionUID = 5565673440300575457L;

	private String titulo;
	private HeaderPanel header;
	private FooterPanel footer;
	
	public TemplatePage() {
		
		add(new Label("titulo", new PropertyModel<String>(this, "titulo")));
		
		header = new HeaderPanel("header");
		footer = new FooterPanel("footer");
		add(header, footer);
		
		
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
