package br.com.allerp.allbanks.entity.conta;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import br.com.allerp.allbanks.entity.GenericEntity;
import br.com.allerp.allbanks.entity.enums.Contas;

@Entity
@Table(name = "CONTA")
@Inheritance(strategy = InheritanceType.JOINED)
public class Conta extends GenericEntity {

	private static final long serialVersionUID = 1750152236850386281L;

	@Column(nullable = false, length = 5)
	private String agencia;

	@Column(nullable = false, unique = true, length = 10)
	private Integer numConta;

	@Column(nullable = false, length = 1)
	private Character status;
	
	@Column(nullable = false, precision = 20, scale = 2)
	private BigDecimal saldo = BigDecimal.ZERO;
	
	/*
	 * @Column(nullable = false) private Contas tipo;
	 */
	private ExtratoBancario extrato;

	@ManyToOne
	@JoinColumn(name = "banco_cod", referencedColumnName = "codigo")
	@ForeignKey(name = "FK_CT_BC")
	private Banco banco;

	@ManyToOne
	@JoinColumn(name = "tit_cod", referencedColumnName = "codigo")
	@ForeignKey(name = "FK_CT_TIT")
	private Titular titular;

	@ManyToMany(mappedBy = "ctContato")
	private List<ListaContatos> listaCont;

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

	public String getStatus() {
		if(status == 'A') {
			return "Ativa";
		} else {
			return "Inativa";
		}
	}

	public void setStatus(Character status) {
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

}
