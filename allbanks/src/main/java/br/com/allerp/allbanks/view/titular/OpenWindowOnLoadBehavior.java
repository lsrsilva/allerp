package br.com.allerp.allbanks.view.titular;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

public class OpenWindowOnLoadBehavior extends AbstractDefaultAjaxBehavior {

	private static final long serialVersionUID = -4365278580670659855L;
	private String id;
	
	public OpenWindowOnLoadBehavior(String modalid) {
		this.id = modalid;
	}
	
	@Override
	protected void respond(AjaxRequestTarget target) {
		ModalWindow window = (ModalWindow) getComponent();
        window.show(target);
	}
	
	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forScript(getCallbackScript().toString(), id));
	}

}
