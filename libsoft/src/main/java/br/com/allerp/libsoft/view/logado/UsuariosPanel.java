package br.com.allerp.libsoft.view.logado;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;

import br.com.allerp.libsoft.entity.user.Bibliotecario;
import br.com.allerp.libsoft.relatorio.GeradorRelatorios;
import br.com.allerp.libsoft.service.user.BibliotecarioService;
import br.com.allerp.libsoft.service.user.UserService;
import br.com.allerp.libsoft.view.logado.forms.CadastroForm;
import br.com.allerp.libsoft.view.logado.forms.ConfExcPanel;

public class UsuariosPanel extends DashboardPage {

	private static final long serialVersionUID = -7194697521228564510L;

	private ModalWindow cadModal;
	private ModalWindow excModal;
	private WebMarkupContainer usersContainer;

	private GeradorRelatorios relatorio;

	private DataView<Bibliotecario> userList;

	@SpringBean(name = "bibliotecarioService")
	private BibliotecarioService bibliotecarioService;

	@SpringBean(name = "userService")
	private UserService userService;

	private List<Bibliotecario> bibliotecarios;

	public UsuariosPanel() {
		setTitle("Libsoft - Listagem de Usuários");

		usersContainer = new WebMarkupContainer("usersContainer");
		bibliotecarios = bibliotecarioService.findAll();

		SearchPanel search = new SearchPanel("searchPanel", bibliotecarios) {

			private static final long serialVersionUID = -3764621601009612179L;

			@Override
			public void atualiza(AjaxRequestTarget target) {
				bibliotecarios = getBibliotecarios();
				target.add(usersContainer);
			}
		};

		addDataView();

		relatorio = new GeradorRelatorios();

		cadModal = new ModalWindow("cadModal");
		excModal = new ModalWindow("excModal");

		add(new AjaxLink<Bibliotecario>("cad") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				CadastroForm cadForm = new CadastroForm(cadModal.getContentId()) {

					private static final long serialVersionUID = -7672091853789244222L;

					@Override
					public void executaAoSalvar(AjaxRequestTarget target, Bibliotecario bilbiotecario) {
						bibliotecarioService.saveOrUpdate(bilbiotecario);

						target.add(usersContainer);
						cadModal.close(target);
					}
				};
				cadModal.setContent(cadForm);
				cadModal.show(target);
			}
		});

		cadModal.setInitialHeight(597);
		cadModal.setInitialWidth(1200);
		cadModal.setResizable(false);

		add(cadModal, excModal, usersContainer, search);
		
		final HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("bibliotecarios", bibliotecarios);

		add(new Link<Object>("btnPDF") {

			private static final long serialVersionUID = -5958698290102968565L;

			@Override
			public void onClick() {

				try {

					final byte[] pdfBytes = relatorio.relatorioUser(parametros, "Usuario", bibliotecarios);
					
					if(pdfBytes != null) {
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
						handler.setFileName("Usuarios.pdf");
						getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}, new Link<Object>("btnExcel") {

			private static final long serialVersionUID = -972376922110338293L;

			@Override
			public void onClick() {
				final byte[] excelBytes = relatorio.geraExcel("Usuario", bibliotecarios);
				
				if(excelBytes != null) {
					AbstractResourceStreamWriter stream = new AbstractResourceStreamWriter() {

						private static final long serialVersionUID = -1194480532924626151L;

						@Override
						public void write(OutputStream output) throws IOException {
							output.write(excelBytes);
							output.close();
						}
					};

					ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(stream);
					handler.setContentDisposition(ContentDisposition.ATTACHMENT);
					handler.setFileName("Usuarios.xlsx");
					getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
				}
			}

		});

	}

	private void addDataView() {
		ListDataProvider<Bibliotecario> listUsers = new ListDataProvider<Bibliotecario>() {

			private static final long serialVersionUID = 3761698853210132399L;

			@Override
			protected List<Bibliotecario> getData() {
				return bibliotecarios;
			}

		};
		userList = new DataView<Bibliotecario>("userList", listUsers) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(Item<Bibliotecario> item) {
				final Bibliotecario bibliotecario = item.getModelObject();
				item.add(new Label("nome", bibliotecario.getNome()));
				item.add(new Label("cpf", bibliotecario.getCpf()));
				item.add(new Label("email", bibliotecario.getEmail()));
				item.add(new Label("userAccess", bibliotecario.getUserAccess()));
				item.add(new AjaxLink<Bibliotecario>("edit") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						CadastroForm editForm = new CadastroForm(cadModal.getContentId(), bibliotecario) {

							private static final long serialVersionUID = -2360017131168195435L;

							@Override
							public void executaAoSalvar(AjaxRequestTarget target, Bibliotecario bibliotecario) {
								bibliotecarioService.update(bibliotecario);
								cadModal.close(target);
								target.add(usersContainer);
							}

						};

						cadModal.setContent(editForm);
						cadModal.show(target);
					}

				});

				item.add(new AjaxLink<Bibliotecario>("delete") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						ConfExcPanel excPanel = new ConfExcPanel(excModal.getContentId(), bibliotecario) {

							private static final long serialVersionUID = -2564309581427741392L;

							@Override
							public void updAfterDel(AjaxRequestTarget target, Bibliotecario pessoa) {
								excModal.close(target);
								target.add(usersContainer);
							}

						};

						excModal.setContent(excPanel);
						excModal.show(target);
					}

				});
			}

		};

		usersContainer.setOutputMarkupId(true);
		usersContainer.add(userList);
	}

}
