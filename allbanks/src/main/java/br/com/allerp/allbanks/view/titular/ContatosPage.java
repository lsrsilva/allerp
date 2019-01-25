package br.com.allerp.allbanks.view.titular;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
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
import br.com.allerp.allbanks.view.titular.panel.CadContatoPanel;

public class ContatosPage extends DashboardPage {

	private static final long serialVersionUID = 1655107947145982466L;

	@SpringBean(name = "contatoService")
	private ContatoService contatoService;

	@SpringBean(name = "titularService")
	private TitularService titularService;

	private ModalWindow mdAddContato;
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
		divLvContatos = new WebMarkupContainer("divLvContatos");
		contatos = titularService.searchContatos(getSessao().getUser().getTitular());

		add(new AjaxLink<Object>("btnAddContato") {

			private static final long serialVersionUID = 8563812137679492688L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				CadContatoPanel cadContato = new CadContatoPanel(mdAddContato.getContentId(),
						getSessao().getUser().getTitular(), mdAddContato) {

					private static final long serialVersionUID = 1603533575683340602L;

					@Override
					public void atualizaAoModificar(AjaxRequestTarget target, Contato object) {
						mdAddContato.close(target);
						target.add(divLvContatos);
					}

				};

				mdAddContato.setContent(cadContato);
				mdAddContato.show(target);
			}

		});

		listContatos();
		add(mdAddContato);

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
				Contato contato = item.getModelObject();
				item.add(new Label("id", item.getIndex() + 1));
				item.add(new Label("titular", contato.getCtContato().getTitular().getNome()));
				item.add(new Label("numConta", contato.getCtContato().getNumConta()));
			}

		};

		listContatos.setItemsPerPage(20L);

		divLvContatos.add(listContatos);
		divLvContatos.setOutputMarkupId(true);

		add(divLvContatos, new PagingNavigator("paginator", listContatos));
	}

	private void appendAssignment(final AppendingStringBuffer buffer, final CharSequence key, final int value) {
		buffer.append(key).append("=");
		buffer.append(value);
		buffer.append(";\n");
	}

}