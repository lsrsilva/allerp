package br.com.allerp.allbanks.view.panel;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;

import br.com.allerp.allbanks.entity.Endereco;
import br.com.allerp.allbanks.view.Util;

public class EnderecoPanel extends Panel {

	private static final long serialVersionUID = -2512337716680410828L;

	public EnderecoPanel(String id) {
		this(id, new Endereco());
	}

	public EnderecoPanel(String id, Endereco endereco) {
		super(id);

		WebMarkupContainer divEnd = Util.markupContainer("divEnd");

		TextField<String> rua = Util.textField("endereco.rua");
		rua.setRequired(true);
		TextField<String> bairro = Util.textField("endereco.bairro");
		bairro.setRequired(true);
		TextField<String> complemento = Util.textField("endereco.complemento");
		NumberTextField<Integer> num = Util.numberTextField("endereco.num");
		num.setRequired(true);
		TextField<String> pais = Util.textField("endereco.pais");
		pais.setRequired(true);
		TextField<String> uf = Util.textField("endereco.uf");
		uf.setRequired(true);
		TextField<String> cidade = Util.textField("endereco.cidade");
		cidade.setRequired(true);
		TextField<String> cep = Util.textField("endereco.cep");
		cep.setRequired(true);

		divEnd.add(rua, bairro, complemento, num, pais, uf, cidade, cep);

	}

}
