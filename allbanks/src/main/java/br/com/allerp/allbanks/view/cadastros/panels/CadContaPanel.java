package br.com.allerp.allbanks.view.cadastros.panels;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.conta.Agencia;
import br.com.allerp.allbanks.entity.conta.Banco;
import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.enums.Contas;
import br.com.allerp.allbanks.entity.enums.Status;
import br.com.allerp.allbanks.service.conta.AgenciaService;
import br.com.allerp.allbanks.service.conta.BancoService;
import br.com.allerp.allbanks.view.Util;

public class CadContaPanel extends Util<Conta> {

	private static final long serialVersionUID = 1L;

	private Conta ctAux;

	@SpringBean(name = "agenciaService")
	private AgenciaService agenciaService;
	@SpringBean(name = "bancoService")
	private BancoService bancoService;

	private List<Agencia> agencias = agenciaService.findAll();
	private List<Banco> bancos = bancoService.findAll();
	private List<Status> ltStatus = Arrays.asList(Status.values());
	private List<Contas> contas = Arrays.asList(Contas.values());

	public CadContaPanel(String id, ModalWindow modal) {
		this(id, new Conta(), modal);
	}

	public CadContaPanel(String id, Conta conta, ModalWindow modal) {
		super(id);
		
		CadTitularPanel cadTit = new CadTitularPanel("cadTit");

		CompoundPropertyModel<Conta> modelCadCt = new CompoundPropertyModel<Conta>(conta);

		final Form<Conta> formCadCt = new Form<Conta>("formCadCt", modelCadCt);

		ChoiceRenderer<Agencia> agRender = new ChoiceRenderer<Agencia>("codAg");
		DropDownChoice<Agencia> agencia = new DropDownChoice<Agencia>("agencia", new PropertyModel<Agencia>(conta, "agencia"), agencias, agRender);
		
		ChoiceRenderer<Banco> bcRender = new ChoiceRenderer<Banco>("nome");
		DropDownChoice<Banco> banco = new DropDownChoice<Banco>("banco", new PropertyModel<Banco>(conta, "banco"), bancos, bcRender);
		
		ChoiceRenderer<Contas> ctRender = new ChoiceRenderer<Contas>("text");
		DropDownChoice<Contas> dropConta = new DropDownChoice<Contas>("conta", new PropertyModel<Contas>(conta, "tipoConta"), contas, ctRender);
		TextField<String> numConta = new TextField<String>("numConta");
		
		ChoiceRenderer<Status> statusRender = new ChoiceRenderer<Status>("text");
		RadioChoice<Status> status= new RadioChoice<Status>("status", new PropertyModel<Status>(conta, "status"), ltStatus, statusRender);
		status.setSuffix("&nbsp;&nbsp;");
		ctAux = conta;
		formCadCt.add(new AjaxButton("salvar") {

			private static final long serialVersionUID = -7557597292953590474L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				atualizaAoModificar(target, ctAux);

				ctAux = new Conta();
				formCadCt.clearInput();
				formCadCt.modelChanged();
				formCadCt.setModelObject(ctAux);
				target.add(formCadCt);
			}

		});

		formCadCt.add(agencia, banco, dropConta, numConta, status, btnCan("btnCanc", conta, modal));
		add(formCadCt, cadTit);

	}

}
