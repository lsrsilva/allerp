package br.com.allerp.allbanks.entity.conta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ForeignKey;

import br.com.allerp.allbanks.entity.pessoa.Pessoa;

public class TitularPoli implements Serializable {

	private static final long serialVersionUID = 7382969791372290127L;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tit_ct", joinColumns = @JoinColumn(name = "tit_cod"), inverseJoinColumns = @JoinColumn(name = "ct_cod"))
	@ForeignKey(name = "FK_TIT_COD", inverseName = "FK_CT_COD")
	private List<Conta> conta;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pessoa_cod", referencedColumnName = "codigo", unique = true)
	@ForeignKey(name = "FK_TIT_")
	private Pessoa pessoa;
	
	private ArrayList<Pessoa> listaPessoa = new ArrayList<Pessoa>();

	public List<Conta> getConta() {
		return conta;
	}

	public void setConta(List<Conta> conta) {
		this.conta = conta;
	}

	public void addPessoa(Pessoa p) {
		pessoa = p;
		listaPessoa.add(p);
	}

	public ArrayList<Pessoa> getListaPessoa() {
		return listaPessoa;
	}
}
