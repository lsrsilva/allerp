package br.com.allerp.allbanks.entity.conta;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.allerp.allbanks.entity.GenericEntity;

@Entity
@Table
public class Banco extends GenericEntity {

	private static final long serialVersionUID = -4966934917325692377L;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "banco_contas", joinColumns = @JoinColumn(name = "banco_cod"), inverseJoinColumns = @JoinColumn(name = "ct_cod"))
	private List<Conta> conta;
	
	public List<Conta> getConta() {
		return conta;
	}
	
	public void setConta(List<Conta> conta) {
		this.conta = conta;
	}
	
}
