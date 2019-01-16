package br.com.allerp.allbanks.view.cadastros;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.colaborador.Funcionario;
import br.com.allerp.allbanks.entity.conta.Agencia;
import br.com.allerp.allbanks.entity.conta.Banco;
import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.entity.user.User;
import br.com.allerp.allbanks.service.UserService;
import br.com.allerp.allbanks.service.colaborador.FuncionarioService;
import br.com.allerp.allbanks.service.conta.AgenciaService;
import br.com.allerp.allbanks.service.conta.BancoService;
import br.com.allerp.allbanks.service.conta.ContaService;
import br.com.allerp.allbanks.service.conta.TitularService;
import br.com.allerp.allbanks.view.DashboardPage;
import br.com.allerp.allbanks.view.Util;
import br.com.allerp.allbanks.view.cadastros.panels.CadAgPanel;
import br.com.allerp.allbanks.view.cadastros.panels.CadBancoPanel;
import br.com.allerp.allbanks.view.cadastros.panels.CadContaPanel;
import br.com.allerp.allbanks.view.cadastros.panels.CadFuncPanel;
import br.com.allerp.allbanks.view.cadastros.panels.CadTitularPanel;
import br.com.allerp.allbanks.view.cadastros.panels.CadUserPanel;
import br.com.allerp.allbanks.view.cadastros.panels.ExcluirPanel;

public class CadastrosPage extends DashboardPage {

	private static final long serialVersionUID = -6335704257672944888L;

	// ---------------------------- SERVICES --------------------------------//

	@SpringBean(name = "agenciaService")
	private AgenciaService agenciaService;
	@SpringBean(name = "userService")
	private UserService userService;
	@SpringBean(name = "funcService")
	private FuncionarioService funcService;
	@SpringBean(name = "bancoService")
	private BancoService bancoService;
	@SpringBean(name = "contaService")
	private ContaService contaService;
	@SpringBean(name = "titularService")
	private TitularService titularService;

	// ----------------------------------------------------------------------//

	// ---------------------------- Lists --------------------------------//

	private List<Agencia> listAg;
	private List<User> listUser;
	private List<Funcionario> listFunc;
	private List<Banco> listBc;
	private List<Conta> listCt;
	private List<Titular> listTit;

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

	public CadastrosPage() {
		
		if(!getSessao().getUser().getPerfil().toString().equals("Gerente")) {
			setResponsePage(DashboardPage.class);
		}

		setTitle("Cadastros");
		
		add(Util.link("dashboard", DashboardPage.class));
		
		cadMdUser = new ModalWindow("cadMdUser");
		cadMdFunc = new ModalWindow("cadMdFunc");
		cadMdAg = new ModalWindow("cadMdAg");
		cadMdBc = new ModalWindow("cadMdBc");
		cadMdCt = new ModalWindow("cadMdCt");
		cadMdTit = new ModalWindow("cadMdTit");
		excModal = new ModalWindow("excModal");

		divUser = new WebMarkupContainer("divUser");
		divUser.setOutputMarkupId(true);
		divFunc = new WebMarkupContainer("divFunc");
		divFunc.setOutputMarkupId(true);
		divAg = new WebMarkupContainer("divAg");
		divAg.setOutputMarkupId(true);
		divBc = new WebMarkupContainer("divBc");
		divBc.setOutputMarkupId(true);
		divCt = new WebMarkupContainer("divCt");
		divCt.setOutputMarkupId(true);
		divTit = new WebMarkupContainer("divTit");
		divTit.setOutputMarkupId(true);

		listAg = agenciaService.findAll();
		listUser = userService.findAll();
		listFunc = funcService.findAll();
		listBc = bancoService.findAll();
		listCt = contaService.findAll();
		listTit = titularService.findAll();

		listViewUser();
		listViewFunc();
		listViewAg();
		listViewBanco();
		listViewConta();
		listViewTitular();

		add(excModal);
	}

	private void listViewUser() {

		/*
		 * final WebMarkupContainer divUser = new WebMarkupContainer("divUser"); final
		 * ModalWindow cadMdUser = new ModalWindow("cadMdUser");
		 */

		divUser.add(new AjaxLink<User>("btnAddUser") {

			private static final long serialVersionUID = 2044861581184497470L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				CadUserPanel userPanel = new CadUserPanel(cadMdUser.getContentId(), cadMdUser) {

					private static final long serialVersionUID = -7337021053411693916L;

					@Override
					public void atualizaAoModificar(AjaxRequestTarget target, User object) {
						userService.saveOrUpdate(object);
						listUser = userService.findAll();
						//divUser.setVisible(true);
						divUser.setVisibilityAllowed(true);
						target.add(divUser);
						cadMdUser.close(target);
					}
				};
				cadMdUser.setContent(userPanel);
				cadMdUser.show(target);
			}

		});

		LoadableDetachableModel<List<User>> loader = new LoadableDetachableModel<List<User>>(listUser) {

			private static final long serialVersionUID = 8071851812481107692L;

			@Override
			protected List<User> load() {
				return listUser;
			}

		};

		ListView<User> lvUser = new ListView<User>("lvUser", loader) {

			private static final long serialVersionUID = 7253438382745424326L;

			@Override
			protected void populateItem(ListItem<User> item) {

				final User user = item.getModelObject();

				item.add(new Label("email", user.getEmail()), new Label("userAccess", user.getUserAccess()),
						new Label("perfil", user.getPerfil()));
				item.add(new AjaxLink<User>("edit") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						CadUserPanel editForm = new CadUserPanel(cadMdUser.getContentId(), user, cadMdUser) {

							private static final long serialVersionUID = -2360017131168195435L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, User object) {
								userService.saveOrUpdate(object);
								target.add(divUser);
								cadMdUser.close(target);
							}

						};

						cadMdUser.setContent(editForm);
						cadMdUser.show(target);
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
								listUser = userService.findAll();
								target.add(divUser);
							}

						};

						excPanel.setService(userService);
						excModal.setContent(excPanel);
						excModal.show(target);
					};

				});

			}
		};

		divUser.setOutputMarkupId(true);
		divUser.add(lvUser);

		add(divUser, cadMdUser);
	}

	private void listViewFunc() {
		divFunc.add(new AjaxLink<Funcionario>("btnAddFunc") {

			private static final long serialVersionUID = 2044861581184497470L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				CadFuncPanel funcPanel = new CadFuncPanel(cadMdFunc.getContentId(), cadMdFunc) {

					private static final long serialVersionUID = -1866501526720516765L;

					@Override
					public void atualizaAoModificar(AjaxRequestTarget target, Funcionario object) {
						funcService.saveOrUpdate(object);
						listFunc = funcService.findAll();

						target.add(divFunc);
						cadMdFunc.close(target);
					}
				};
				cadMdFunc.setContent(funcPanel);
				cadMdFunc.show(target);
			}

		});

		LoadableDetachableModel<List<Funcionario>> loader = new LoadableDetachableModel<List<Funcionario>>(listFunc) {

			private static final long serialVersionUID = 8071851812481107692L;

			@Override
			protected List<Funcionario> load() {
				return listFunc;
			}

		};

		ListView<Funcionario> lvFunc = new ListView<Funcionario>("lvFunc", loader) {

			private static final long serialVersionUID = 7253438382745424326L;

			@Override
			protected void populateItem(ListItem<Funcionario> item) {

				final Funcionario func = (Funcionario) item.getModelObject();

				item.add(new Label("nome", func.getNome()), new Label("rg", func.getRg()),
						new Label("cpf", func.getCpf()), new Label("funcao", func.getRg()));
				item.add(new AjaxLink<Funcionario>("edit") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						CadFuncPanel editForm = new CadFuncPanel(cadMdFunc.getContentId(), func, cadMdFunc) {

							private static final long serialVersionUID = -2360017131168195435L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Funcionario object) {
								funcService.saveOrUpdate(object);
								cadMdFunc.close(target);
								target.add(divFunc);
							}

						};

						cadMdFunc.setContent(editForm);
						cadMdFunc.show(target);
					}

				}, new AjaxLink<Funcionario>("delete") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						// verificar a questão da tipagem
						// util.addExcPanel("excluir", agencia, "agência", "Exluir a Agência" +
						// agencia.getCodigo() + "?", excModal, divAg, target);
						ExcluirPanel<Funcionario> excPanel = new ExcluirPanel<Funcionario>(excModal.getContentId(),
								func, "funcionário", "Excluir o cadastro do funcionário" + func.getNome() + "?") {

							private static final long serialVersionUID = -2564309581427741392L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Funcionario object) {
								excModal.close(target);
								listFunc = funcService.findAll();
								
								target.add(divAg);
							}

						};
						excPanel.setService(funcService);
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

		divFunc.add(lvFunc);
		add(divFunc, cadMdFunc);
	}

	private void listViewAg() {

		divAg.add(new AjaxLink<Agencia>("btnAddAg") {

			private static final long serialVersionUID = 2044861581184497470L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				CadAgPanel agPanel = new CadAgPanel(cadMdAg.getContentId(), cadMdAg) {

					private static final long serialVersionUID = -1866501526720516765L;

					@Override
					public void atualizaAoModificar(AjaxRequestTarget target, Agencia object) {
						
						agenciaService.saveOrUpdate(object);
						listAg = agenciaService.findAll();

						cadMdAg.close(target);
						target.add(divAg);
					}
				};
				cadMdAg.setContent(agPanel);
				cadMdAg.show(target);
			}

		});

		LoadableDetachableModel<List<Agencia>> loader = new LoadableDetachableModel<List<Agencia>>(listAg) {

			private static final long serialVersionUID = 8750330117036998650L;

			@Override
			protected List<Agencia> load() {
				return listAg;
			}
		};

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
						CadAgPanel editForm = new CadAgPanel(cadMdAg.getContentId(), agencia, cadMdAg) {

							private static final long serialVersionUID = -2360017131168195435L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Agencia object) {
								agenciaService.saveOrUpdate(object);
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
								listAg = agenciaService.findAll();
								target.add(divAg);
							}

						};
						excPanel.setService(agenciaService);
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
		divBc.add(new AjaxLink<Banco>("btnAddBc") {

			private static final long serialVersionUID = 2044861581184497470L;
			
			@Override
			public void onClick(AjaxRequestTarget target) {
				CadBancoPanel bcPanel = new CadBancoPanel(cadMdBc.getContentId(), cadMdBc) {

					private static final long serialVersionUID = 6411274232083887033L;

					@Override
					public void atualizaAoModificar(AjaxRequestTarget target, Banco object) {
						bancoService.saveOrUpdate(object);
						listBc = bancoService.findAll();
						target.add(divBc);
						cadMdBc.close(target);
					}
				};
				
				cadMdBc.setContent(bcPanel);
				cadMdBc.show(target);
			}

		});

		LoadableDetachableModel<List<Banco>> loader = new LoadableDetachableModel<List<Banco>>(listBc) {

			private static final long serialVersionUID = 8750330117036998650L;

			@Override
			protected List<Banco> load() {
				return listBc;
			}
		};

		ListView<Banco> lvBc = new ListView<Banco>("lvBc", loader) {

			private static final long serialVersionUID = 7253438382745424326L;

			@Override
			protected void populateItem(final ListItem<Banco> item) {

				final Banco banco = item.getModelObject();

				item.add(new Label("codCompensacao", banco.getCodCompensacao()),
						new Label("nomeBanco", banco.getNome()));
				item.add(new AjaxLink<Banco>("edit") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						CadBancoPanel editForm = new CadBancoPanel(cadMdBc.getContentId(), banco, cadMdBc) {

							private static final long serialVersionUID = -2360017131168195435L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Banco object) {
								bancoService.saveOrUpdate(object);
								cadMdBc.close(target);
								target.add(divBc);
							}

						};

						cadMdBc.setContent(editForm);
						cadMdBc.show(target);
					}

				}, new AjaxLink<Banco>("delete") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						// verificar a questão da tipagem
						// util.addExcPanel("excluir", agencia, "agência", "Exluir a Agência" +
						// agencia.getCodigo() + "?", excModal, divAg, target);
						ExcluirPanel<Banco> excPanel = new ExcluirPanel<Banco>(excModal.getContentId(), banco, "banco",
								"Excluir o banco " + banco.getCodCompensacao() + "?") {

							private static final long serialVersionUID = -2564309581427741392L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Banco object) {
								excModal.close(target);
								listBc = bancoService.findAll();
								target.add(divBc);
							}

						};
						excPanel.setService(bancoService);
						excModal.setContent(excPanel);
						excModal.show(target);
					};

				});

			}
		};
		divBc.setOutputMarkupId(true);
		divBc.add(lvBc);

		add(divBc, cadMdBc);
	}

	private void listViewConta() {
		divCt.add(new AjaxLink<Conta>("btnAddCt") {

			private static final long serialVersionUID = 2044861581184497470L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				CadContaPanel ctPanel = new CadContaPanel(cadMdCt.getContentId(), cadMdCt) {

					private static final long serialVersionUID = -1866501526720516765L;

					@Override
					public void atualizaAoModificar(AjaxRequestTarget target, Conta object) {
						contaService.saveOrUpdate(object);
						listCt = contaService.findAll();

						cadMdCt.close(target);
						target.add(divCt);
					}
				};
				cadMdCt.setContent(ctPanel);
				cadMdCt.show(target);
			}

		});

		LoadableDetachableModel<List<Conta>> loader = Util.addLoadable(listCt);

		ListView<Conta> lvCt = new ListView<Conta>("lvCt", loader) {

			private static final long serialVersionUID = 7253438382745424326L;

			@Override
			protected void populateItem(ListItem<Conta> item) {

				final Conta conta = (Conta) item.getModelObject();

				item.add(new Label("agencia", conta.getAgencia().getCodAg()), new Label("numConta", conta.getNumConta()),
						new Label("status", conta.getStatus()));
				item.add(new AjaxLink<Conta>("edit") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						CadContaPanel editForm = new CadContaPanel(cadMdCt.getContentId(), conta, cadMdCt) {

							private static final long serialVersionUID = -2360017131168195435L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Conta object) {
								contaService.saveOrUpdate(object);
								cadMdCt.close(target);
								target.add(divCt);
							}

						};

						cadMdCt.setContent(editForm);
						cadMdCt.show(target);
					}

				}, new AjaxLink<Conta>("delete") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						// verificar a questão da tipagem
						// util.addExcPanel("excluir", agencia, "agência", "Exluir a Agência" +
						// agencia.getCodigo() + "?", excModal, divAg, target);
						ExcluirPanel<Conta> excPanel = new ExcluirPanel<Conta>(excModal.getContentId(), conta, "conta",
								"Excluir a conta " + conta.getNumConta() + "?") {

							private static final long serialVersionUID = -2564309581427741392L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Conta object) {
								excModal.close(target);
								listCt = contaService.findAll();
								target.add(divCt);
							}

						};
						excPanel.setService(contaService);
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

		divCt.add(lvCt);
		add(divCt, cadMdCt);
	}

	private void listViewTitular() {

		LoadableDetachableModel<List<Titular>> loader = Util.addLoadable(listTit);

		ListView<Titular> lvTit = new ListView<Titular>("lvTit", loader) {

			private static final long serialVersionUID = 7253438382745424326L;

			@Override
			protected void populateItem(ListItem<Titular> item) {

				final Titular titular = item.getModelObject();
				item.add(new Label("nomeRzSocial", titular.getNome()),
						new Label("cpfCnpj", titular.getCpfCnpj()));
				item.add(new AjaxLink<Titular>("edit") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						CadTitularPanel editForm = new CadTitularPanel(cadMdTit.getContentId(), titular) {

							private static final long serialVersionUID = -2360017131168195435L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Titular object) {
								titularService.saveOrUpdate(object);
								cadMdTit.close(target);
								target.add(divTit);
							}

						};

						cadMdTit.setContent(editForm);
						cadMdTit.show(target);
					}

				}, new AjaxLink<Titular>("delete") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						// verificar a questão da tipagem
						// util.addExcPanel("excluir", agencia, "agência", "Exluir a Agência" +
						// agencia.getCodigo() + "?", excModal, divAg, target);
						ExcluirPanel<Titular> excPanel = new ExcluirPanel<Titular>(excModal.getContentId(), titular,
								"titular", "Excluir o titular " + titular.getNome() + "?") {

							private static final long serialVersionUID = -2564309581427741392L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Titular object) {
								excModal.close(target);
								listTit = titularService.findAll();
								target.add(divTit);
							}

						};

						excPanel.setService(titularService);
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

		divTit.add(lvTit);
		add(divTit, cadMdTit);
	}

}
