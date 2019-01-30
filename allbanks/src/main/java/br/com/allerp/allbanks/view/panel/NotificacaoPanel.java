package br.com.allerp.allbanks.view.panel;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.settings.IApplicationSettings;

public class NotificacaoPanel extends FeedbackPanel {

	private final class MessageListView extends ListView<FeedbackMessage> {
		private static final long serialVersionUID = 1L;

		/**
		 * @see org.apache.wicket.Component#Component(String)
		 */
		public MessageListView(final String id) {
			super(id);
			setDefaultModel(newFeedbackMessagesModel());
		}

		@Override
		protected IModel<FeedbackMessage> getListItemModel(final IModel<? extends List<FeedbackMessage>> listViewModel,
				final int index) {
			return new AbstractReadOnlyModel<FeedbackMessage>() {
				private static final long serialVersionUID = 1L;

				/**
				 * WICKET-4258 Feedback messages might be cleared already.
				 * 
				 * @see IApplicationSettings#setFeedbackMessageCleanupFilter(org.apache.wicket.feedback.IFeedbackMessageFilter)
				 */
				@Override
				public FeedbackMessage getObject() {
					if (index >= listViewModel.getObject().size()) {
						return null;
					} else {
						return listViewModel.getObject().get(index);
					}
				}
			};
		}

		@Override
		protected void populateItem(final ListItem<FeedbackMessage> listItem) {
			final FeedbackMessage message = listItem.getModelObject();
			message.markRendered();
			final Component label = newMessageDisplayComponent("message", message);
			final AttributeModifier levelModifier = AttributeModifier.append("class", getCSSClass(message));
			label.add(levelModifier);
			listItem.add(levelModifier);
			listItem.add(label);
		}
	}

	private static final long serialVersionUID = -5849090782949068322L;

	private final String cssClass = "feedback";
	private String additionalCSSClass = "feedback-top-right";

	// Create a notifcation panel with the default additional class, specified as a
	// field variable
	public NotificacaoPanel(String id) {
		super(id);

		init(id, additionalCSSClass);
	}

	private MessageListView messageListView;

	public NotificacaoPanel(final String id, IFeedbackMessageFilter filter) {
		super(id);
		WebMarkupContainer messagesContainer = new WebMarkupContainer("feedbackul") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisible(anyMessage());
			}
		};
		add(messagesContainer);
		messageListView = new MessageListView("messages");
		messageListView.setVersioned(false);
		messagesContainer.add(messageListView);

		if (filter != null) {
			setFilter(filter);
		}
	}

	// Create a notifcation panel with a custom additional class, overwriting the
	// field variable
	public NotificacaoPanel(String id, String additionalCSSClass) {
		super(id);

		this.additionalCSSClass = additionalCSSClass;

		init(id, additionalCSSClass);
	}

	private void init(String id, String additionalCSSClass) {
		// set custom markup id and ouput it, to find the component later on in the js
		// function
		setMarkupId(id);
		setOutputMarkupId(true);

		// Add the additional cssClass and hide the element by default
		add(new AttributeModifier("class", new Model<String>(cssClass + " " + additionalCSSClass)));
		add(new AttributeModifier("style", new Model<String>("opacity: 0;")));
	}

	/**
	 * Method to refresh the notification panel
	 * 
	 * if there are any feedback messages for the user, find the gravest level,
	 * format the notification panel accordingly and show it
	 * 
	 * @param target AjaxRequestTarget to add panel and the calling javascript
	 *               function
	 */
	public void refresh(AjaxRequestTarget target) {

		// any feedback at all in the current form?
		if (anyMessage()) {
			int highestFeedbackLevel = FeedbackMessage.INFO;

			// any feedback with the given level?
			if (anyMessage(FeedbackMessage.WARNING))
				highestFeedbackLevel = FeedbackMessage.WARNING;
			if (anyMessage(FeedbackMessage.ERROR))
				highestFeedbackLevel = FeedbackMessage.ERROR;
			if (anyMessage(FeedbackMessage.SUCCESS))
				highestFeedbackLevel = FeedbackMessage.SUCCESS;

			// add the css classes to the notification panel,
			// including the border css which represents the highest level of feedback
			add(new AttributeModifier("class", new Model<String>(cssClass + " " + additionalCSSClass
					+ " notificationpanel_border_" + String.valueOf(highestFeedbackLevel))));

			// refresh the panel and call the js function with the panel markup id
			// and the total count of messages
			target.add(this);
			target.appendJavaScript(
					"mostraNotificações('" + getMarkupId() + "', " + getCurrentMessages().size() + ");");
		}
	}

	/**
	 * Returns css class for the single rows of the panel
	 * 
	 * @see org.apache.wicket.markup.html.panel.FeedbackPanel#getCSSClass(org.apache.wicket.feedback.FeedbackMessage)
	 */
	@Override
	protected String getCSSClass(FeedbackMessage message) {
		return "feedback-" + message.getLevelAsString();
	}

}
