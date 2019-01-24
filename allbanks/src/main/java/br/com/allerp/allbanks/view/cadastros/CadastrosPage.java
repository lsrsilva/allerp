package br.com.allerp.allbanks.view.cadastros;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;

import br.com.allerp.allbanks.entity.colaborador.Funcionario;
import br.com.allerp.allbanks.entity.conta.Agencia;
import br.com.allerp.allbanks.entity.conta.Banco;
import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.entity.enums.Contas;
import br.com.allerp.allbanks.entity.enums.Perfis;
import br.com.allerp.allbanks.entity.enums.Status;
import br.com.allerp.allbanks.entity.user.User;
import br.com.allerp.allbanks.relatorio.GeradorRelatorio;
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

	// ---------------------------- BEANS --------------------------------//

	private User user;
	private Conta conta;
	private Titular titular;
	private Banco banco;

	// ----------------------------------------------------------------------//

	public CadastrosPage() {

		if (!getSessao().getUser().getPerfil().toString().equals("Gerente")) {
			setResponsePage(DashboardPage.class);
			return;
		}

		setTitle("Cadastros");

		cadMdUser = new ModalWindow("cadMdUser");
		cadMdUser.setResizable(false);
		cadMdUser.setInitialHeight(214);
		cadMdFunc = new ModalWindow("cadMdFunc");
		cadMdFunc.setResizable(false);
		cadMdAg = new ModalWindow("cadMdAg");
		cadMdAg.setResizable(false);
		cadMdBc = new ModalWindow("cadMdBc");
		cadMdBc.setResizable(false);
		cadMdCt = new ModalWindow("cadMdCt");
		cadMdCt.setResizable(false);
		cadMdTit = new ModalWindow("cadMdTit");
		cadMdTit.setResizable(false);
		excModal = new ModalWindow("excModal");
		excModal.setResizable(false);

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

//		add(new AjaxLink<Object>("abaUser") {
//			
//			private static final long serialVersionUID = 4409560948251289852L;
//			
//			@Override
//			public void onClick(AjaxRequestTarget target) {
//				target.add(divUser);
//			}
//		}, new AjaxLink<Object>("abaFunc") {
//			
//			private static final long serialVersionUID = 4409560948251289852L;
//			
//			@Override
//			public void onClick(AjaxRequestTarget target) {
//				//listViewFunc();
//				target.add(divFunc);
//			}
//		}, new AjaxLink<Object>("abaBanco") {
//			
//			private static final long serialVersionUID = 4409560948251289852L;
//			
//			@Override
//			public void onClick(AjaxRequestTarget target) {
//				target.add(divBc);
//			}
//		}, new AjaxLink<Object>("abaAg") {
//			
//			private static final long serialVersionUID = 4409560948251289852L;
//			
//			@Override
//			public void onClick(AjaxRequestTarget target) {
//				target.add(divAg);
//			}
//		},new AjaxLink<Object>("abaConta") {
//			
//			private static final long serialVersionUID = 4409560948251289852L;
//			
//			@Override
//			public void onClick(AjaxRequestTarget target) {
//				target.add(divCt);
//			}
//		},new AjaxLink<Object>("abaTit") {
//			
//			private static final long serialVersionUID = 4409560948251289852L;
//			
//			@Override
//			public void onClick(AjaxRequestTarget target) {
//				target.add(divTit);
//			}
//		});

		add(excModal);
	}

	private void listViewUser() {

		/*
		 * final WebMarkupContainer divUser = new WebMarkupContainer("divUser"); final
		 * ModalWindow cadMdUser = new ModalWindow("cadMdUser");
		 */
		final User user = new User();
		CompoundPropertyModel<User> formModel = new CompoundPropertyModel<User>(user);
		final Form<User> searchUser = new Form<User>("searchUser", formModel);

		final TextField<String> userAccess = new TextField<String>("userAccess");
		ChoiceRenderer<Perfis> perfilRender = new ChoiceRenderer<Perfis>("text");
		final DropDownChoice<Perfis> perfil = new DropDownChoice<Perfis>("perfil",
				new PropertyModel<Perfis>(user, "perfil"), Arrays.asList(Perfis.values()), perfilRender);

		searchUser.add(userAccess, perfil);

		searchUser.add(new AjaxButton("btnSearchUs") {

			private static final long serialVersionUID = 3865918601254958016L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				if (userAccess != null && perfil != null || user.getUserAccess().equals("")
						|| user.getPerfil().toString().equals("")) {
					if (userAccess.getValue().equals("") && perfil.getValue().equals("")) {
						listUser = userService.findAll();
					} else {
						listUser = userService.search(user.getUserAccess(), user.getPerfil());
					}
				}

				target.add(divUser, searchUser);
			}

		});

		divUser.add(searchUser);

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
						// divUser.setVisible(true);
						divUser.setVisibilityAllowed(true);
						target.add(divUser);
						cadMdUser.close(target);
					}
				};
				cadMdUser.setContent(userPanel).setOutputMarkupId(true);
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

						cadMdUser.setContent(editForm).setOutputMarkupId(true);
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
						excModal.setContent(excPanel).setOutputMarkupId(true);
						excModal.show(target);
					};

				});

			}
		};

		lvUser.getReuseItems();

		final GeradorRelatorio<User> relUser = new GeradorRelatorio<User>();
		final HashMap<String, Object> usParam = new HashMap<String, Object>();
		usParam.put("listUser", listUser);

		divUser.add(new Link<Object>("pdfUser") {

			private static final long serialVersionUID = -5958698290102968565L;

			@Override
			public void onClick() {

				try {

					final byte[] pdfBytes = relUser.geraPdf(usParam, "Usuarios", listUser);

					if (pdfBytes != null) {
						AbstractResourceStreamWriter stream = new AbstractResourceStreamWriter() {

							private static final long serialVersionUID = -1194480532924626151L;

							@Override
							public void write(OutputStream output) throws IOException {
								output.write(pdfBytes);
								output.close();
							}
						};

						ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(stream);
						handler.setContentDisposition(ContentDisposition.ATTACHMENT);
						handler.setFileName("Relatório de Usuários.pdf");
						getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		divUser.setOutputMarkupId(true);
		divUser.add(lvUser);

		add(divUser, cadMdUser);
	}

	private void listViewFunc() {

		final Funcionario func = new Funcionario();
		CompoundPropertyModel<Funcionario> formModel = new CompoundPropertyModel<Funcionario>(func);
		final Form<Funcionario> searchFunc = new Form<Funcionario>("searchFunc", formModel);

		final TextField<String> nome = new TextField<String>("nome");
		final TextField<String> cpf = new TextField<String>("cpf");
		final TextField<String> funcao = new TextField<String>("funcao");

		searchFunc.add(nome, cpf, funcao);

		searchFunc.add(new AjaxButton("btnSearchFunc") {

			private static final long serialVersionUID = 3865918601254958016L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (nome != null && cpf != null && funcao != null || func.getNome().equals("")
						|| func.getCpf().equals("") || func.getFuncao().equals("")) {
					if (nome.getValue().equals("") && cpf.getValue().equals("") && funcao.getValue().equals("")) {
						listFunc = funcService.findAll();
					}
					listFunc = funcService.search(func.getNome(), func.getCpf(), func.getFuncao());
				}
				target.add(divFunc);
			}

		});

		divFunc.add(searchFunc);

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
				cadMdFunc.setContent(funcPanel).setOutputMarkupId(true);
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

						cadMdFunc.setContent(editForm).setOutputMarkupId(true);
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
						excModal.setContent(excPanel).setOutputMarkupId(true);
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

	private void listViewBanco() {

		final Banco banco = new Banco();
		CompoundPropertyModel<Banco> formModel = new CompoundPropertyModel<Banco>(banco);
		final Form<Banco> searchBc = new Form<Banco>("searchBc", formModel);

		final TextField<String> nome = new TextField<String>("nome");
		final TextField<String> codCompensacao = new TextField<String>("codCompensacao");

		searchBc.add(nome, codCompensacao);

		searchBc.add(new AjaxButton("btnSearchBc") {

			private static final long serialVersionUID = 3865918601254958016L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (nome != null && codCompensacao != null || banco.getNome().equals("")
						|| banco.getCodCompensacao().equals("")) {
					if (nome.getValue().equals("") && codCompensacao.getValue().equals("")) {
						listBc = bancoService.findAll();
					}
					listBc = bancoService.search(banco.getCodCompensacao(), banco.getNome());
				}
				target.add(divBc);
			}

		});

		divBc.add(searchBc);

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
						target.add(divBc, divAg);
						cadMdBc.close(target);
					}
				};

				cadMdBc.setContent(bcPanel).setOutputMarkupId(true);
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

						cadMdBc.setContent(editForm).setOutputMarkupId(true);
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
						excModal.setContent(excPanel).setOutputMarkupId(true);
						excModal.show(target);
					};

				});

			}
		};
		divBc.setOutputMarkupId(true);
		divBc.add(lvBc);

		add(divBc, cadMdBc);
	}

	private void listViewAg() {

		final Agencia agencia = new Agencia();
		/*
		 * final Banco banco = new Banco(); agencia.setBanco(banco);
		 */

		CompoundPropertyModel<Agencia> formModel = new CompoundPropertyModel<Agencia>(agencia);
		final Form<Agencia> searchAg = new Form<Agencia>("searchAg", formModel);

		final TextField<Integer> codAg = new TextField<Integer>("codAg");
		ChoiceRenderer<Banco> bcCodRender = new ChoiceRenderer<Banco>("codCompensacao");
		final DropDownChoice<Banco> dropCodBc = new DropDownChoice<Banco>("banco",
				new PropertyModel<Banco>(agencia, "banco"), listBc, bcCodRender);

		ChoiceRenderer<Banco> bcNmRender = new ChoiceRenderer<Banco>("nome");
		final DropDownChoice<Banco> dropNmBc = new DropDownChoice<Banco>("banco.nome",
				new PropertyModel<Banco>(agencia, "banco"), listBc, bcNmRender);

		searchAg.add(codAg, dropCodBc, dropNmBc);

		searchAg.add(new AjaxButton("btnSearchAg") {

			private static final long serialVersionUID = 3865918601254958016L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				if (dropCodBc.getValue().equals("") || dropNmBc.getValue().equals("")) {
					agencia.setBanco(new Banco());
				}

				if (codAg != null && dropCodBc != null && dropNmBc != null || agencia.getCodAg() == 0
						|| agencia.getBanco().getCodCompensacao().equals("")
						|| agencia.getBanco().getNome().equals("")) {

					if (codAg.getValue().equals("") && dropCodBc.getValue().equals("")
							&& dropNmBc.getValue().equals("")) {
						listAg = agenciaService.findAll();
					} else {
						listAg = agenciaService.search(agencia.getCodAg(), agencia.getBanco().getCodCompensacao(),
								agencia.getBanco().getNome());
					}
				}

				target.add(divAg, searchAg);
			}

		});

		divAg.add(searchAg);

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
				cadMdAg.setContent(agPanel).setOutputMarkupId(true);
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

						cadMdAg.setContent(editForm).setOutputMarkupId(true);
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
						excModal.setContent(excPanel).setOutputMarkupId(true);
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

	private void listViewConta() {

		final Conta conta = new Conta();
		CompoundPropertyModel<Conta> formModel = new CompoundPropertyModel<Conta>(conta);
		final Form<Conta> searchCt = new Form<Conta>("searchCt", formModel);

		final TextField<Integer> codAg = new TextField<Integer>("agencia.codAg");
		final TextField<Integer> numConta = new TextField<Integer>("numConta");
		ChoiceRenderer<Status> statusRender = new ChoiceRenderer<Status>("text");
		final DropDownChoice<Status> status = new DropDownChoice<Status>("status",
				new PropertyModel<Status>(conta, "status"), Arrays.asList(Status.values()), statusRender);
		ChoiceRenderer<Contas> tipoCtRender = new ChoiceRenderer<Contas>("text");
		final DropDownChoice<Contas> tipoCt = new DropDownChoice<Contas>("tipoConta",
				new PropertyModel<Contas>(conta, "tipoConta"), Arrays.asList(Contas.values()), tipoCtRender);

		searchCt.add(codAg, numConta, status, tipoCt);

		searchCt.add(new AjaxButton("btnSearchCt") {

			private static final long serialVersionUID = 3865918601254958016L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				if (conta.getAgencia() == null) {
					conta.setAgencia(new Agencia());
				}

				if (codAg != null && numConta != null && status != null && tipoCt != null
						|| conta.getAgencia().getCodAg() == 0 || conta.getNumConta() == 0
						|| conta.getStatus().toString().equals("") || conta.getTipoConta().toString().equals("")) {
					if (codAg.getValue().equals("") && numConta.getValue().equals("")
							&& status.getValue().toString().equals("") && tipoCt.getValue().toString().equals("")) {
						listCt = contaService.findAll();
					} else {
						listCt = contaService.search(conta.getAgencia().getCodAg(), conta.getNumConta(),
								conta.getStatus(), conta.getTipoConta());
					}
				}

				target.add(divCt, searchCt);
			}

		});

		divCt.add(searchCt);

		divCt.add(new AjaxLink<Conta>("btnAddCt") {

			private static final long serialVersionUID = 2044861581184497470L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				CadContaPanel ctPanel = new CadContaPanel(cadMdCt.getContentId(), cadMdCt) {

					private static final long serialVersionUID = -1866501526720516765L;

					@Override
					public void atualizaAoModificar(AjaxRequestTarget target, Conta object) {
						contaService.saveOrUpdate(object);
						listTit = titularService.findAll();
						listCt = contaService.findAll();

						cadMdCt.close(target);
						target.add(divCt, divTit);
					}
				};
				cadMdCt.setContent(ctPanel).setOutputMarkupId(true);
				cadMdCt.show(target);
			}

		});

		LoadableDetachableModel<List<Conta>> loader = new LoadableDetachableModel<List<Conta>>(listCt) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4390746698318909640L;

			@Override
			protected List<Conta> load() {
				return listCt;
			}
		};

		ListView<Conta> lvCt = new ListView<Conta>("lvCt", loader) {

			private static final long serialVersionUID = 7253438382745424326L;

			@Override
			protected void populateItem(ListItem<Conta> item) {

				final Conta conta = item.getModelObject();
				final Titular titular = conta.getTitular();
				final User user = titular.getUser();

				item.add(new Label("agencia", conta.getAgencia().getCodAg()),
						new Label("numConta", conta.getNumConta()), new Label("status", conta.getStatus()));
				item.add(new AjaxLink<Conta>("edit") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						CadContaPanel editForm = new CadContaPanel(cadMdCt.getContentId(), conta, titular, user,
								cadMdCt) {

							private static final long serialVersionUID = -2360017131168195435L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Conta object) {
								userService.saveOrUpdate(object.getTitular().getUser());
								titularService.saveOrUpdate(object.getTitular());
								contaService.saveOrUpdate(object);
								cadMdCt.close(target);
								target.add(divCt);
							}

						};

						cadMdCt.setContent(editForm).setOutputMarkupId(true);
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
								listTit = titularService.findAll();
								listCt = contaService.findAll();

								target.add(divCt, divTit);
							}

						};
						excPanel.setService(contaService);
						excModal.setContent(excPanel).setOutputMarkupId(true);
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

		final Titular titular = new Titular();
		CompoundPropertyModel<Titular> formModel = new CompoundPropertyModel<Titular>(titular);
		final Form<Titular> searchTit = new Form<Titular>("searchTit", formModel);

		final TextField<String> cpfCnpj = new TextField<String>("cpfCnpj");
		final TextField<String> nome = new TextField<String>("nome");

		searchTit.add(cpfCnpj, nome);

		searchTit.add(new AjaxButton("btnSearchTit") {

			private static final long serialVersionUID = 3865918601254958016L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				if (cpfCnpj != null && nome != null || titular.getNome().equals("")
						|| titular.getCpfCnpj().equals("")) {
					if (cpfCnpj.getValue().equals("") && nome.getValue().equals("")) {
						listTit = titularService.findAll();
					} else {
						listTit = titularService.search(titular.getCpfCnpj(), titular.getNome());
					}
				}

				target.add(divTit, searchTit);
			}

		});

		divTit.add(searchTit);

		LoadableDetachableModel<List<Titular>> loader = new LoadableDetachableModel<List<Titular>>(listTit) {

			/**
			 * 
			 */
			private static final long serialVersionUID = -4653797931296348739L;

			@Override
			protected List<Titular> load() {
				return listTit;
			}
		};

		ListView<Titular> lvTit = new ListView<Titular>("lvTit", loader) {

			private static final long serialVersionUID = 7253438382745424326L;

			@Override
			protected void populateItem(ListItem<Titular> item) {

				final Titular titular = item.getModelObject();
				item.add(new Label("nomeRzSocial", titular.getNome()), new Label("cpfCnpj", titular.getCpfCnpj()));
				item.add(new AjaxLink<Titular>("edit") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						CadTitularPanel editForm = new CadTitularPanel(cadMdTit.getContentId(), titular, cadMdTit) {

							private static final long serialVersionUID = -2360017131168195435L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Titular object) {
								titularService.saveOrUpdate(object);
								cadMdTit.close(target);
								target.add(divTit);
							}

						};

						cadMdTit.setContent(editForm).setOutputMarkupId(true);
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
						excModal.setContent(excPanel).setOutputMarkupId(true);
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
