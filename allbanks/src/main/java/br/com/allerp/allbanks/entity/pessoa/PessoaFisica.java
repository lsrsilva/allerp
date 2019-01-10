package br.com.allerp.allbanks.entity.pessoa;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

import br.com.allerp.allbanks.entity.conta.Titular;

@Entity
@Table
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

	@Column(nullable = false, length = 8)
	private String rg;

	@Column(nullable = true)
	private Character sexo;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tit_cod", referencedColumnName = "codigo", unique = true)
	@ForeignKey(name = "FK_TITU_PF")
	private Titular titular;

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCpf() {
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
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Character getSexo() {
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

}
