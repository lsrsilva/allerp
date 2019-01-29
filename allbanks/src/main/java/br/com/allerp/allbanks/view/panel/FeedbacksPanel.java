package br.com.allerp.allbanks.view.panel;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class FeedbacksPanel extends FeedbackPanel {

	private static final long serialVersionUID = -5849090782949068322L;

	public FeedbacksPanel(String id) {
		super(id);
	}
	
	@Override
	protected String getCSSClass(FeedbackMessage message) {
		String css;
		
		switch(message.getLevel()) {
		case FeedbackMessage.ERROR:
			css = "feedback-error";
			break;
			default:
				css = super.getCSSClass(message);
		}
		
		return css;
	}

}
