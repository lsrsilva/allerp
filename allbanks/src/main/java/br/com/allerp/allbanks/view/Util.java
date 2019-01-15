package br.com.allerp.allbanks.view;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.component.IRequestablePage;

import br.com.allerp.allbanks.view.cadastros.panels.ExcluirPanel;

public class Util<T> extends Panel {
	
	private T objeto;
	
	public Util(String id) {
		this(id, null);
	}
	
	public Util(String id, T objeto) {
		super(id);
		this.objeto = objeto;
	}
	
	public T getObjeto() {
		return objeto;
	}
	
	private static final long serialVersionUID = 1L;

	public void atualizaAoModificar(AjaxRequestTarget target, T object) {
	}

	// Pensar depois em como criar
	/*
	 * public AjaxButton btnCad(String id, final Form<T> yourForm, final T
	 * newObject){ new AjaxButton(id) { private static final long serialVersionUID =
	 * 6032897730453499590L;
	 * 
	 * @Override protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
	 * atualizaAoModificar(target, newObject);
	 * 
	 * yourForm.clearInput(); yourForm.modelChanged();
	 * yourForm.setModelObject(newObject); target.add(form); }
	 * 
	 * };
	 * 
	 * return null; }
	 */

	public ExcluirPanel<T> addExcPanel(String id, T object, String objExc, String message, final ModalWindow excModal,
			final WebMarkupContainer div, AjaxRequestTarget target) {
		ExcluirPanel<T> excPanel = new ExcluirPanel<T>(id, object, objExc, message) {

			private static final long serialVersionUID = 8750409035931257289L;

			@Override
			public void atualizaAoModificar(AjaxRequestTarget target, T object) {
				excModal.close(target);
				target.add(div);
			}

		};

		excModal.setContent(excPanel);
		excModal.show(target);

		return excPanel;

	}

	public AjaxButton btnCan(String id, final T object, final ModalWindow modal) {
		return new AjaxButton(id) {
			private static final long serialVersionUID = 6032897730453499590L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				atualizaAoModificar(target, object);
				modal.close(target);
			}

		};
	}

	public static <T> LoadableDetachableModel<List<T>> addLoadable(final List<T> listAtualizar) {
		return new LoadableDetachableModel<List<T>>(listAtualizar) {

			private static final long serialVersionUID = 8071851812481107692L;

			@Override
			protected List<T> load() {
				return listAtualizar;
			}

		};
	}

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
