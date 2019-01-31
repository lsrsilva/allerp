package br.com.allerp.allbanks.view.cadastros.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.colaborador.Funcionario;
import br.com.allerp.allbanks.service.colaborador.FuncionarioService;
import br.com.allerp.allbanks.view.Util;
import br.com.allerp.allbanks.view.panel.NotificacaoPanel;

public class CadFuncPanel extends Util<Funcionario> {

	private static final long serialVersionUID = -8353854020520248600L;

	private Funcionario funcAux;
	protected boolean valido;

	@SpringBean(name = "funcService")
	private FuncionarioService funcService;
	
	private NotificacaoPanel funcNotificacao;

	public CadFuncPanel(String id, ModalWindow modal) {
		this(id, new Funcionario(), modal);
	}

	public CadFuncPanel(String id, Funcionario funcionario, final ModalWindow modal) {
		super(id);
		
		funcNotificacao = funcNotificacao();

		CompoundPropertyModel<Funcionario> modelCadFunc = new CompoundPropertyModel<Funcionario>(funcionario);

		final Form<Funcionario> formCadFunc = new Form<Funcionario>("formCadFunc", modelCadFunc);

		TextField<String> nome = new TextField<String>("nome");
		TextField<String> rg = new TextField<String>("rg");
		TextField<String> cpf = new TextField<String>("cpf");
		DateTextField dtNasc = new DateTextField("dtNasc");
		DatePicker datePicker = new DatePicker() {

			private static final long serialVersionUID = -3701264872352758583L;

			@Override
			protected String getIconStyle() {
				return "cursor: pointer; border: none;";
			}

		};
		datePicker.setAutoHide(true);
		dtNasc.setMarkupId("dtNasc");
		dtNasc.add(datePicker);
		TextField<String> celular = new TextField<String>("celular");
		TextField<String> telefone = new TextField<String>("telefone");
		TextField<String> funcao = new TextField<String>("funcao");
		TextField<String> formacao = new TextField<String>("formacao");
		TextField<String> salario = new TextField<String>("salario");

		// Adicionar Departamento

		funcAux = funcionario;
		formCadFunc.add(new AjaxButton("salvar") {

			private static final long serialVersionUID = 4850648916902160346L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				valido = funcService.camposSaoValidos(funcAux);
				if(valido) {
					for (String mensagem : funcService.getMensagens()) {
						funcNotificacao.success(mensagem);
					}

					funcNotificacao.refresh(target);
					
					target.appendJavaScript("mostraTabCad('funcCad');");
					atualizaAoModificar(target, funcAux);
	
					funcAux = new Funcionario();
					formCadFunc.clearInput();
					formCadFunc.modelChanged();
					formCadFunc.setModelObject(funcAux);
					target.add(formCadFunc);

					funcService.getMensagens().clear();
					modal.close(target);
				} else {
					for (String mensagem : funcService.getMensagens()) {
						funcNotificacao.error(mensagem);
					}

					funcNotificacao.refresh(target);
					funcService.getMensagens().clear();
				}
			}

		});

		formCadFunc.add(nome, rg, cpf, dtNasc, celular, telefone, funcao, formacao, salario, btnCan("btnCanc", modal));

		add(formCadFunc, funcNotificacao);
	}
	
	private NotificacaoPanel funcNotificacao() {
		funcNotificacao = new NotificacaoPanel("funcNotificacao");
		return funcNotificacao;
	}
	
}
