package br.com.allerp.allbanks.entity.conta;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.allerp.allbanks.entity.GenericEntity;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Conta extends GenericEntity {

	private static final long serialVersionUID = 1750152236850386281L;

	@Column(nullable = false, length = 5)
	private String agencia;

	@Column(nullable = false, unique = true, length = 10)
	private Integer numConta;

	@Column(nullable = false, length = 1)
	private Character status;

	@ManyToOne
	@JoinColumn(name = "banco_cod", referencedColumnName = "cod_compensacao")
	private Banco banco;

	@ManyToOne
	@JoinColumn(name = "tit_cod", referencedColumnName = "codigo")
	private Titular<?> titular;
	
	@ManyToMany(mappedBy = "ctContato")
	private List<ListaContatos>listaCont;

	public Conta() {
		status = 'A';
	}

	public Titular<?> getTitular() {
		return titular;
	}

	public void setTitular(Titular<?> titular) {
		this.titular = titular;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public Integer getNumConta() {
		return numConta;
	}

	public void setNumConta(Integer numConta) {
		this.numConta = numConta;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

}
