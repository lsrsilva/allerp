package br.com.allerp.allbanks.entity.conta;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

import br.com.allerp.allbanks.entity.Endereco;
import br.com.allerp.allbanks.entity.pessoa.Pessoa;

@Entity
@Table(name = "TITULAR")
@ForeignKey(name = "FK_PES_COD")
@PrimaryKeyJoinColumn(name = "tit_cod", referencedColumnName = "codigo")
public class Titular extends Pessoa {

	private static final long serialVersionUID = -8605819128606661027L;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tit_ct", joinColumns = @JoinColumn(name = "tit_cod"), inverseJoinColumns = @JoinColumn(name = "ct_cod"))
	@ForeignKey(name = "FK_TIT_COD", inverseName = "FK_CT_COD")
	private List<Conta> conta;

	@Column(length = 200)
	private String ie;
	
	@Column
	private String celular;

	@Column( length = 14)
	private String cpfCnpj;

	@Column
	@Temporal(TemporalType.DATE)
	private Date dtNasc;

	@Column(nullable = false, length = 200)
	private String nome;

	@Column(length = 20)
	private String rg;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "end_codigo")
	@ForeignKey(name = "FK_PESS_END")
	private Endereco endereco;

	@Column(nullable = false)
	private String tipoPessoa;

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	
	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String razaoSocial) {
		this.nome = razaoSocial;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}
	
	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Date getDtNasc() {
		return dtNasc;
	}

	public void setDtNasc(Date dtNasc) {
		this.dtNasc = dtNasc;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

}
