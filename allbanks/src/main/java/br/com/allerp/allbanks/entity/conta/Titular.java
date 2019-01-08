package br.com.allerp.allbanks.entity.conta;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import br.com.allerp.allbanks.entity.pessoa.PessoaFisica;

public class Titular extends PessoaFisica {

	private static final long serialVersionUID = -8605819128606661027L;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tit_ct", joinColumns = @JoinColumn(name = "tit_cod"), inverseJoinColumns = @JoinColumn(name = "ct_cod"))
	private List<Conta> conta;

	public List<Conta> getConta() {
		return conta;
	}

	public void setConta(List<Conta> conta) {
		this.conta = conta;
	}

}
