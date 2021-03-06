package br.com.allerp.allbanks.view.cadastros.panels;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.conta.Agencia;
import br.com.allerp.allbanks.entity.conta.Banco;
import br.com.allerp.allbanks.service.conta.AgenciaService;
import br.com.allerp.allbanks.service.conta.BancoService;
import br.com.allerp.allbanks.view.Util;
import br.com.allerp.allbanks.view.panel.NotificacaoPanel;

public class CadAgPanel extends Util<Agencia> {

	private static final long serialVersionUID = 5152936004070002278L;

	private Agencia agAux;
	protected boolean valido;
	
	@SpringBean(name="agenciaService")
	private AgenciaService agenciaService;

	@SpringBean(name = "bancoService")
	private BancoService bancoService;

	private List<Banco> bancos = bancoService.findAll();

	public CadAgPanel(String id, ModalWindow modal) {
		this(id, new Agencia(), modal);
	}

	public CadAgPanel(String id, Agencia agencia, ModalWindow modal) {
		super(id);
		
		final NotificacaoPanel agenciaNotificacao = new NotificacaoPanel("agenciaNotificacao");
		
		CompoundPropertyModel<Agencia> modelCadAg = new CompoundPropertyModel<Agencia>(agencia);

		final Form<Agencia> formCadAg = new Form<Agencia>("formCadAg", modelCadAg);

		TextField<Integer> codAg = new TextField<Integer>("codAg");

		ChoiceRenderer<Banco> bcRender = new ChoiceRenderer<Banco>("nome");
		final DropDownChoice<Banco> nomeBanco = new DropDownChoice<Banco>("banco",
				new PropertyModel<Banco>(agencia, "banco"), bancos, bcRender);

		agAux = agencia;
		formCadAg.add(new AjaxButton("salvar") {

			private static final long serialVersionUID = -7557597292953590474L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				valido = agenciaService.camposSaoValido(agAux);
				
				if(valido) {
					target.appendJavaScript("mostraTabCad('agCad');");
					for(String mensagem : agenciaService.getMensagens()) {
						success(mensagem);
					}
					agenciaNotificacao.refresh(target);
					
					atualizaAoModificar(target, agAux);
	
					agAux = new Agencia();
					formCadAg.clearInput();
					formCadAg.modelChanged();
					formCadAg.setModelObject(agAux);
					agenciaService.getMensagens().clear();
					target.add(formCadAg);
				} else {
					for(String mensagem : agenciaService.getMensagens()) {
						error(mensagem);
					}
					
					agenciaNotificacao.refresh(target);
					agenciaService.getMensagens().clear();
				}
			}

		});

		formCadAg.add(codAg, nomeBanco, btnCan("btnCanc", modal));
		add(formCadAg, agenciaNotificacao);

	}

}
