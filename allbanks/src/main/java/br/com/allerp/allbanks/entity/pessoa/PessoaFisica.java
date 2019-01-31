package br.com.allerp.allbanks.entity.pessoa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "PF")
@ForeignKey(name = "FK_PES_COD")
@PrimaryKeyJoinColumn(name = "pf_cod", referencedColumnName = "codigo")
public class PessoaFisica extends Pessoa {

	private static final long serialVersionUID = 1479524506413698898L;

	@Column
	private String celular;

	@Column(nullable = false, length = 14)
	private String cpf;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dtNasc;

	@Column(nullable = false, length = 200)
	private String nome;

	@Column(nullable = false, length = 20)
	private String rg;

	@Column(nullable = true)
	private Character sexo;
	
	/*
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tit_cod", referencedColumnName = "tit_cod", unique = true)
	@ForeignKey(name = "FK_TITU_PF")
	private Titular titular;*/

	public String getCelular() {
		if(celular == null) {
			return "";
		}
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCpf() {
		if(cpf == null) {
			return "";
		}
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDtNasc() {
		return dtNasc;
	}

	public void setDtNasc(Date dtNasc) {
		this.dtNasc = dtNasc;
	}

	public String getNome() {
		if(nome == null) {
			return "";
		}
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		if(rg == null) {
			return "";
		}
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Character getSexo() {
		if(sexo == null) {
			return '\u0000';
		}
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

}
