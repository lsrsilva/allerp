package br.com.allerp.allbanks.view.cadastros.panels;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.conta.Agencia;
import br.com.allerp.allbanks.service.conta.BancoService;
import br.com.allerp.allbanks.view.Util;

public class CadAgPanel extends Util<Agencia> {

	private static final long serialVersionUID = 5152936004070002278L;

	private Agencia agAux;

	@SpringBean(name = "bancoService")
	private BancoService bancoService;
	
	private List<String> bancos = bancoService.listBcNames();

	public CadAgPanel(String id, ModalWindow modal) {
		this(id, new Agencia(), modal);
	}

	public CadAgPanel(String id, Agencia agencia, ModalWindow modal) {
		super(id);

		CompoundPropertyModel<Agencia> modelCadAg = new CompoundPropertyModel<Agencia>(agencia);

		final Form<Agencia> formCadAg = new Form<Agencia>("formCadAg", modelCadAg);

		NumberTextField<Integer> codAg = new NumberTextField<Integer>("codAg");
		DropDownChoice<String> banco = new DropDownChoice<String>("banco", bancos);
		
		agAux = agencia;
		formCadAg.add(new AjaxButton("salvar") {

			private static final long serialVersionUID = -7557597292953590474L;
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				atualizaAoModificar(target, agAux);
				
				agAux = new Agencia();
				formCadAg.clearInput();
				formCadAg.modelChanged();
				formCadAg.setModelObject(agAux);
				target.add(formCadAg);
			}
			
		});
		
		formCadAg.add(codAg, banco, btnCan("btnCanc", agencia, modal));
		add(formCadAg);

	}

}
