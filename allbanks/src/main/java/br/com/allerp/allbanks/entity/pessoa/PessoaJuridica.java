package br.com.allerp.allbanks.entity.pessoa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import br.com.allerp.allbanks.entity.conta.Titular;

@Entity
@Table(name = "PJ")
@ForeignKey(name = "FK_PES_COD")
@PrimaryKeyJoinColumn(name = "pj_cod", referencedColumnName = "codigo")
public class PessoaJuridica extends Pessoa {

	private static final long serialVersionUID = 7348650644568929564L;

	@Column(nullable = false, length = 20)
	private String cnpj;

	@Column(nullable = false, length = 200)
	private String razaoSocial;

	@Column(nullable = false, length = 200)
	private String ie;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tit_cod", referencedColumnName = "codigo", unique = true)
	@ForeignKey(name = "FK_TITU_PJ")
	private Titular titular;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}
}
