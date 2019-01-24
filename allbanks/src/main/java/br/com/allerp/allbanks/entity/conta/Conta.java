package br.com.allerp.allbanks.entity.conta;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import br.com.allerp.allbanks.entity.GenericEntity;
import br.com.allerp.allbanks.entity.enums.Contas;
import br.com.allerp.allbanks.entity.enums.Status;

@Entity
@Table(name = "CONTA")
@Inheritance(strategy = InheritanceType.JOINED)
public class Conta extends GenericEntity {

	private static final long serialVersionUID = 1750152236850386281L;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_ag", referencedColumnName = "codigo", nullable = false)
	@ForeignKey(name = "FK_AG_CT")
	private Agencia agencia;

	@Column(nullable = false, unique = true, length = 10)
	private Integer numConta;

	@Column(nullable = false, length = 7)
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Contas tipoConta;

	@Column(nullable = false, precision = 20, scale = 2)
	private BigDecimal saldo = BigDecimal.ZERO;

	private ExtratoBancario extrato;

	@ManyToOne
	@JoinColumn(name = "banco_cod", referencedColumnName = "codigo", nullable = false)
	@ForeignKey(name = "FK_CT_BC")
	private Banco banco;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tit_cod", referencedColumnName = "tit_cod")
	@ForeignKey(name = "FK_CT_TIT")
	private Titular titular;

	@Override
	public boolean equals(Object conta) {
		if (this == conta) {
			return true;
		}

		if (!(conta instanceof Conta)) {
			return false;
		}

		Conta conta2 = (Conta) conta;
		return this.getCodigo() == conta2.getCodigo();
	}

	public Conta() {
		status = Status.ATIVO;
	}

	public Titular getTitular() {
		return titular;
	}

	public void setTitular(Titular titular) {
		this.titular = titular;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public ExtratoBancario getExtrato() {
		return extrato;
	}

	public void setExtrato(ExtratoBancario extrato) {
		this.extrato = extrato;
	}

	public Contas getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(Contas tipoConta) {
		this.tipoConta = tipoConta;
	}

}
