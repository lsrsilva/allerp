package br.com.allerp.allbanks.view.cadastros;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.conta.Agencia;
import br.com.allerp.allbanks.entity.user.User;
import br.com.allerp.allbanks.service.UserService;
import br.com.allerp.allbanks.service.conta.AgenciaService;
import br.com.allerp.allbanks.view.Util;
import br.com.allerp.allbanks.view.cadastros.panels.CadAgPanel;
import br.com.allerp.allbanks.view.cadastros.panels.CadUserPanel;
import br.com.allerp.allbanks.view.cadastros.panels.ExcluirPanel;

public class CadTemplatePage extends WebPage {

	private static final long serialVersionUID = -6335704257672944888L;

	// ---------------------------- SERVICES --------------------------------//

	@SpringBean(name = "agenciaService")
	private AgenciaService agenciaService;
	@SpringBean(name = "userService")
	private UserService userService;
//	@SpringBean(name = "agenciaService")
//	private AgenciaService agenciaService;
//	@SpringBean(name = "agenciaService")
//	private AgenciaService agenciaService;
//	@SpringBean(name = "agenciaService")
//	private AgenciaService agenciaService;
//	@SpringBean(name = "agenciaService")
//	private AgenciaService agenciaService;

	// ----------------------------------------------------------------------//

	// ---------------------------- Lists --------------------------------//

	private List<Agencia> listAg = agenciaService.findAll();
	private List<User> listUser = userService.findAll();
//	private List<Agencia> listAg = agenciaService.findAll();
//	private List<Agencia> listAg = agenciaService.findAll();
//	private List<Agencia> listAg = agenciaService.findAll();
//	private List<Agencia> listAg = agenciaService.findAll();

	// ----------------------------------------------------------------------//

	// ---------------------------- COMPONENTES --------------------------------//

	private ModalWindow cadMdUser;
	private ModalWindow cadMdFunc;
	private ModalWindow cadMdAg;
	private ModalWindow cadMdBc;
	private ModalWindow cadMdCt;
	private ModalWindow cadMdTit;
	private ModalWindow excModal;

	private WebMarkupContainer divUser;
	private WebMarkupContainer divFunc;
	private WebMarkupContainer divAg;
	private WebMarkupContainer divBc;
	private WebMarkupContainer divCt;
	private WebMarkupContainer divTit;

	// ----------------------------------------------------------------------//

	public CadTemplatePage() {

		cadMdUser = new ModalWindow("cadMdUser");
		cadMdFunc = new ModalWindow("cadMdFunc");
		cadMdAg = new ModalWindow("cadMdAg");
		cadMdBc = new ModalWindow("cadMdBc");
		cadMdCt = new ModalWindow("cadMdCt");
		cadMdTit = new ModalWindow("cadMdTit");
		excModal = new ModalWindow("excModal");

		divUser = new WebMarkupContainer("divUser");
		divFunc = new WebMarkupContainer("divFunc");
		divAg = new WebMarkupContainer("divAg");
		divBc = new WebMarkupContainer("divBc");
		divCt = new WebMarkupContainer("divCt");
		divTit = new WebMarkupContainer("divTit");

		listViewUser();
		listViewFunc();
		listViewAg();
		listViewBanco();
		listViewConta();
		listViewTitular();

	}

	private void listViewUser() {
		divUser.add(new AjaxLink<User>("btnAddUser") {

			private static final long serialVersionUID = 2044861581184497470L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				CadUserPanel agPanel = new CadUserPanel(cadMdAg.getContentId()) {

					private static final long serialVersionUID = -1866501526720516765L;

					@Override
					public void atualizaAoModificar(AjaxRequestTarget target, User object) {
						userService.saveOrUpdate(object);

						cadMdAg.close(target);
					}
				};
				cadMdAg.setContent(agPanel);
				cadMdAg.show(target);
			}

		});

		LoadableDetachableModel<List<User>> loader = Util.addLoadable(listUser);

		ListView<User> lvUser = new ListView<User>("lvUser", loader) {

			private static final long serialVersionUID = 7253438382745424326L;

			@Override
			protected void populateItem(ListItem<User> item) {

				final User user = (User) item.getModelObject();

				item.add(new Label("email", user.getEmail()),
						new Label("userAccess", user.getUserAccess()),
						new Label("perfil", user.getPerfil()));
				item.add(new AjaxLink<User>("edit") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						CadUserPanel editForm = new CadUserPanel(cadMdAg.getContentId(), user) {

							private static final long serialVersionUID = -2360017131168195435L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, User object) {
								userService.update(object);
								cadMdAg.close(target);
								target.add(divAg);
							}

						};

						cadMdAg.setContent(editForm);
						cadMdAg.show(target);
					}

				}, new AjaxLink<User>("delete") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						// verificar a questão da tipagem
						// util.addExcPanel("excluir", agencia, "agência", "Exluir a Agência" +
						// agencia.getCodigo() + "?", excModal, divAg, target);
						ExcluirPanel<User> excPanel = new ExcluirPanel<User>(excModal.getContentId(), user, "usuário",
								"Excluir o usuário" + user.getUserAccess() + "?") {

							private static final long serialVersionUID = -2564309581427741392L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, User object) {
								excModal.close(target);
								target.add(divAg);
							}

						};

						excModal.setContent(excPanel);
						excModal.show(target);
					};

				});

			}
		};

		/*
		 * if(listAg == null) { divAg.add(new Label("notFound",
		 * "Nenhum resultado Encontrado")); } else { divAg.add(new Label("notFound",
		 * "")); }
		 */

		divUser.add(lvUser);
		add(divUser, cadMdUser);
	}

	private void listViewFunc() {
		add(divFunc, cadMdFunc);
	}

	private void listViewAg() {

		divAg.add(new AjaxLink<Agencia>("btnAddAg") {

			private static final long serialVersionUID = 2044861581184497470L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				CadAgPanel agPanel = new CadAgPanel(cadMdAg.getContentId()) {

					private static final long serialVersionUID = -1866501526720516765L;

					@Override
					public void atualizaAoModificar(AjaxRequestTarget target, Agencia object) {
						agenciaService.saveOrUpdate(object);

						cadMdAg.close(target);
					}
				};
				cadMdAg.setContent(agPanel);
				cadMdAg.show(target);
			}

		});

		LoadableDetachableModel<List<Agencia>> loader = Util.addLoadable(listAg);

		ListView<Agencia> lvAg = new ListView<Agencia>("lvAg", loader) {

			private static final long serialVersionUID = 7253438382745424326L;

			@Override
			protected void populateItem(ListItem<Agencia> item) {

				final Agencia agencia = (Agencia) item.getModelObject();

				item.add(new Label("codAg", agencia.getCodAg()),
						new Label("codBanco", agencia.getBanco().getCodCompensacao()),
						new Label("nomeBanco", agencia.getBanco().getNome()));
				item.add(new AjaxLink<Agencia>("edit") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						CadAgPanel editForm = new CadAgPanel(cadMdAg.getContentId(), agencia) {

							private static final long serialVersionUID = -2360017131168195435L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Agencia object) {
								agenciaService.update(object);
								cadMdAg.close(target);
								target.add(divAg);
							}

						};

						cadMdAg.setContent(editForm);
						cadMdAg.show(target);
					}

				}, new AjaxLink<Agencia>("delete") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						// verificar a questão da tipagem
						// util.addExcPanel("excluir", agencia, "agência", "Exluir a Agência" +
						// agencia.getCodigo() + "?", excModal, divAg, target);
						ExcluirPanel<Agencia> excPanel = new ExcluirPanel<Agencia>(excModal.getContentId(), agencia,
								"agência", "Excluir a agência" + agencia.getCodAg() + "?") {

							private static final long serialVersionUID = -2564309581427741392L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Agencia object) {
								excModal.close(target);
								target.add(divAg);
							}

						};

						excModal.setContent(excPanel);
						excModal.show(target);
					};

				});

			}
		};

		/*
		 * if(listAg == null) { divAg.add(new Label("notFound",
		 * "Nenhum resultado Encontrado")); } else { divAg.add(new Label("notFound",
		 * "")); }
		 */

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
