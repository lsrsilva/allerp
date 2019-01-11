package br.com.allerp.allbanks.view.cadastros;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.conta.Agencia;
import br.com.allerp.allbanks.service.conta.AgenciaService;
import br.com.allerp.allbanks.view.Util;
import br.com.allerp.allbanks.view.cadastros.panels.CadAgPanel;
import br.com.allerp.allbanks.view.cadastros.panels.ExcluirPanel;

public class CadTemplatePage extends WebPage {

	private static final long serialVersionUID = -6335704257672944888L;

	// ---------------------------- SERVICES --------------------------------//

	@SpringBean(name = "agenciaService")
	private AgenciaService agenciaService;

	// ----------------------------------------------------------------------//

	// ---------------------------- Lists --------------------------------//

	private List<Agencia> listAg = agenciaService.findAll();

	// ----------------------------------------------------------------------//

	// ---------------------------- COMPONENTES --------------------------------//

	private ModalWindow cadMdUser = new ModalWindow("cadMdUser");
	private ModalWindow cadMdFunc = new ModalWindow("cadMdFunc");
	private ModalWindow cadMdAg = new ModalWindow("cadMdAg");
	private ModalWindow cadMdBc = new ModalWindow("cadMdBc");
	private ModalWindow cadMdCt = new ModalWindow("cadMdCt");
	private ModalWindow cadMdTit = new ModalWindow("cadMdTit");
	private ModalWindow excModal = new ModalWindow("excModal");

	private WebMarkupContainer divUser = new WebMarkupContainer("divUser");
	private WebMarkupContainer divFunc = new WebMarkupContainer("divFunc");
	private WebMarkupContainer divAg = new WebMarkupContainer("divAg");
	private WebMarkupContainer divBc = new WebMarkupContainer("divBc");
	private WebMarkupContainer divCt = new WebMarkupContainer("divCt");
	private WebMarkupContainer divTit = new WebMarkupContainer("divTit");

	// ----------------------------------------------------------------------//

	public CadTemplatePage() {
		CadAgPanel cadAg = new CadAgPanel("cadAg");

		listViewUser();
		listViewFunc();
		listViewAg();
		listViewBanco();
		listViewConta();
		listViewTitular();
		
		add(cadAg);
	}

	private void listViewUser() {
		add(divUser, cadMdUser);
	}

	private void listViewFunc() {
		add(divFunc, cadMdFunc);
	}

	private void listViewAg() {
		LoadableDetachableModel<List<Agencia>> loader = Util.addLoadable(listAg);

		ListView<Agencia> lvAg = new ListView<Agencia>("lvAg", loader) {

			private static final long serialVersionUID = 7253438382745424326L;

			@Override
			protected void populateItem(ListItem<Agencia> item) {

				final Agencia agencia = (Agencia) item.getModelObject();

				item.add(new Label("codAg", agencia.getCodAg()));
				item.add(new Label("codBanco", agencia.getBanco().getCodCompensacao()));
				item.add(new Label("nomeBanco", agencia.getBanco().getNome()));
				item.add(new AjaxLink<Agencia>("edit") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						CadAgPanel editForm = new CadAgPanel(cadMdAg.getContentId(), agencia) {

							private static final long serialVersionUID = -2360017131168195435L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Agencia agencia) {
								agenciaService.update(agencia);
								cadMdAg.close(target);
								target.add(divAg);
							}

						};

						cadMdAg.setContent(editForm);
						cadMdAg.show(target);
					}

				});

				item.add(new AjaxLink<Agencia>("delete") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						ExcluirPanel<Agencia> excPanel = new ExcluirPanel<Agencia>(excModal.getContentId(), agencia) {

							private static final long serialVersionUID = -2564309581427741392L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Agencia agencia) {
								excModal.close(target);
								target.add(divAg);
							}

						};

						excModal.setContent(excPanel);
						excModal.show(target);
					}

				});

			}
		};
		divAg.add(lvAg);
		add(divAg, cadMdAg);

	}

	private void listViewBanco() {
		add(divBc, cadMdBc);
	}

	private void listViewConta() {
		add(divCt, cadMdCt);
	}

	private void listViewTitular() {
		add(divTit, cadMdTit);
	}

}
