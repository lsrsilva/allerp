package br.com.allerp.allbanks.view.titular;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.AppendingStringBuffer;

import br.com.allerp.allbanks.entity.conta.Contato;
import br.com.allerp.allbanks.service.conta.ContatoService;
import br.com.allerp.allbanks.service.conta.TitularService;
import br.com.allerp.allbanks.view.DashboardPage;
import br.com.allerp.allbanks.view.cadastros.panels.ExcluirPanel;
import br.com.allerp.allbanks.view.titular.panel.CadContatoPanel;

public class ContatosPage extends DashboardPage {

	private static final long serialVersionUID = 1655107947145982466L;

	@SpringBean(name = "contatoService")
	private ContatoService contatoService;

	@SpringBean(name = "titularService")
	private TitularService titularService;

	private ModalWindow mdAddContato;
	private ModalWindow excMd;
	private WebMarkupContainer divLvContatos;

	private List<Contato> contatos;

	public ContatosPage() {
		if (!getUserPerfil("Titular")) {
			setResponsePage(DashboardPage.class);
		}

		mdAddContato = new ModalWindow("mdAddContato") {

			private static final long serialVersionUID = -3715864176875914229L;

			@Override
			protected AppendingStringBuffer postProcessSettings(AppendingStringBuffer settings) {
				appendAssignment(settings, "settings.minWidth", 380);
				appendAssignment(settings, "settings.minHeight", 276);
				appendAssignment(settings, "settings.width", 380);
				appendAssignment(settings, "settings.height", 276);

				return settings;
			}
		};
		mdAddContato.setResizable(false);

		excMd = new ModalWindow("excMd") {

			private static final long serialVersionUID = -3715864176875914229L;

			@Override
			protected AppendingStringBuffer postProcessSettings(AppendingStringBuffer settings) {
				appendAssignment(settings, "settings.minWidth", 380);
				appendAssignment(settings, "settings.minHeight", 122);
				appendAssignment(settings, "settings.width", 380);
				appendAssignment(settings, "settings.height", 122);

				return settings;
			}
		};
		excMd.setResizable(false);

		divLvContatos = new WebMarkupContainer("divLvContatos");
		divLvContatos.setOutputMarkupId(true);

		contatos = contatoService.searchContatos(getSessao().getUser().getTitular());

		listContatos();

		add(new AjaxLink<Contato>("btnAddContato") {

			private static final long serialVersionUID = 8563812137679492688L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				CadContatoPanel cadContato = new CadContatoPanel(mdAddContato.getContentId(),
						getSessao().getUser().getTitular(), mdAddContato) {

					private static final long serialVersionUID = 1603533575683340602L;

					@Override
					public void atualizaAoModificar(AjaxRequestTarget target, Contato object) {
						mdAddContato.close(target);
						contatos = contatoService.searchContatos(getSessao().getUser().getTitular());
						target.add(divLvContatos);
					}

				};

				mdAddContato.setContent(cadContato);
				mdAddContato.show(target);
			}

		});

		add(mdAddContato, excMd);

	}

	public void listContatos() {
		ListDataProvider<Contato> ldp = new ListDataProvider<Contato>(contatos) {

			private static final long serialVersionUID = -1393522592527007555L;

			@Override
			protected List<Contato> getData() {
				return contatos;
			}
		};
		final DataView<Contato> listContatos = new DataView<Contato>("listContatos", ldp) {

			private static final long serialVersionUID = 3285188868111645398L;

			@Override
			protected void populateItem(Item<Contato> item) {
				final Contato contato = item.getModelObject();
				item.add(new Label("id", item.getIndex() + 1));
				item.add(new Label("titular", contato.getCtContato().getTitular().getNome()));
				item.add(new Label("numConta", contato.getCtContato().getNumConta()));
				item.add(new AjaxLink<Contato>("delete") {

					private static final long serialVersionUID = -984734035789687817L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						// verificar a questão da tipagem
						// util.addExcPanel("excluir", agencia, "agência", "Exluir a Agência" +
						// agencia.getCodigo() + "?", excModal, divAg, target);
						ExcluirPanel<Contato> excPanel = new ExcluirPanel<Contato>(excMd.getContentId(), contato,
								"contato", "Excluir o contato " + contato.getCtContato().getTitular().getNome() + "?") {

							private static final long serialVersionUID = -2564309581427741392L;

							@Override
							public void atualizaAoModificar(AjaxRequestTarget target, Contato object) {
								excMd.close(target);
								contatos = contatoService.searchContatos(getSessao().getUser().getTitular());
								target.add(divLvContatos);
							}

						};

						excPanel.setService(contatoService);
						excMd.setContent(excPanel).setOutputMarkupId(true);
						excMd.show(target);
					};

				});

			}

		};

		listContatos.setItemsPerPage(20L);

		divLvContatos.add(listContatos);

		add(divLvContatos, new PagingNavigator("paginator", listContatos));
	}

	private void appendAssignment(final AppendingStringBuffer buffer, final CharSequence key, final int value) {
		buffer.append(key).append("=");
		buffer.append(value);
		buffer.append(";\n");
	}

}