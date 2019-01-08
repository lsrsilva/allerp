package br.com.allerp.allbanks.entity.conta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.allerp.allbanks.entity.GenericEntity;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Conta extends GenericEntity {

	private static final long serialVersionUID = 1750152236850386281L;

	@Column(nullable = false)
	private String agencia;

	@Column(nullable = false, unique = true)
	private String numConta;

	@Column(length = 1, nullable = false)
	private Character status;

	@ManyToOne
	@JoinColumn(name = "banco_cod")
	private Banco banco;

	@ManyToOne
	@JoinColumn(name = "tit_cod")
	private Titular titular;

	public Conta() {
		status = 'A';
	}

	public Titular getTitular() {
		return titular;
	}

	public void setTitular(Titular titular) {
		this.titular = titular;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getNumConta() {
		return numConta;
	}

	public void setNumConta(String numConta) {
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
