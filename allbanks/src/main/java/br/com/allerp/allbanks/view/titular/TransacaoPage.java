package br.com.allerp.allbanks.view.titular;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.exceptions.FeedbackException;
import br.com.allerp.allbanks.service.conta.ContaService;
import br.com.allerp.allbanks.service.conta.TitularService;
import br.com.allerp.allbanks.view.DashboardPage;

public class TransacaoPage extends DashboardPage {

	private static final long serialVersionUID = -837099547910614195L;

	@SpringBean(name = "titularService")
	private TitularService titularService;

	@SpringBean(name = "contaService")
	private ContaService contaService;

	private Titular titular;
	private Conta conta;

	private Double valorDep;

	public TransacaoPage() {
		setTitle("Transações");
		if (!getUserPerfil("Titular")) {
			setResponsePage(DashboardPage.class);
			return;
		}

		titular = getSessao().getUser().getTitular();
		List<Conta> contas = titular.getContas();
		conta = new Conta();

		// Compara o acesso do usuário do titular com a conta cadastrada para que possa
		// ser trabalhado as transações para esta conta
		for (int i = 0; i < contas.size(); i++) {
			if (titular.getUser().getUserAccess().equals(contas.get(i).getNumConta().toString())) {
				conta = contas.get(i);
				break;
			}
		}

		final Label saldo = new Label("saldo", Model.of("Saldo: R$ " + conta.getSaldo()));
		saldo.setOutputMarkupId(true);

		Form<Double> formConta = new Form<Double>("formConta");
		TextField<Double> valDep = new TextField<Double>("valorDep", new PropertyModel<Double>(this, "valorDep"));

		formConta.add(valDep, new AjaxButton("depositar") {

			private static final long serialVersionUID = 5413163658877175673L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				System.out.println(valorDep);
				try {
					contaService.deposita(conta, valorDep);
				} catch (FeedbackException e) {
					e.printStackTrace();
				}
				target.add(saldo);
			}

		});

		add(saldo, formConta);

	}

	public Double getValorDep() {
		return valorDep;
	}

	public void setValorDep(Double valorDep) {
		this.valorDep = valorDep;
	}

}
