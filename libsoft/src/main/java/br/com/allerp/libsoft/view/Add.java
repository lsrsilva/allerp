package br.com.allerp.libsoft.view;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.component.IRequestablePage;

public class Add {

	public static <T> TextField<T> textField(String id) {
		return new TextField<T>(id);
	}
	
	public static <N extends Number & Comparable<N>> NumberTextField<N> numberTextField(String id) {
		return new NumberTextField<N>(id);
	}

	public static EmailTextField emailTextField(String id) {
		return new EmailTextField(id);
	}

	public static PasswordTextField passwordTextField(String id) {
		return new PasswordTextField(id);
	}

	public static <T, C extends IRequestablePage> Link<T> link(String id, final Class<C> responsePage) {
		return new Link<T>(id) {
			private static final long serialVersionUID = 6341732124957969371L;

			@Override
			public void onClick() {
				setResponsePage(responsePage);
			}
		};

	}
	
	public static <T, C extends IRequestablePage> AjaxLink<T> ajaxLink(String id, final Class<C> responsePage) {
		return new AjaxLink<T>(id) {
			private static final long serialVersionUID = 6341732124957969371L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(responsePage);
			}
		};

	}
	
	public static <T, C extends IRequestablePage> AjaxButton ajaxButton(String id, final Class<C> responsePage) {
		return new AjaxButton(id) {
			private static final long serialVersionUID = 6341732124957969371L;

			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				
				setResponsePage(responsePage);
			}
		};

	}
	
	public static <T> DropDownChoice<T> dropDown(String id, List<? extends T> choices) {
		return new DropDownChoice<T>(id, choices);
	}
	
	public static WebMarkupContainer markupContainer(String id) {
		return new WebMarkupContainer(id);
	}

}
