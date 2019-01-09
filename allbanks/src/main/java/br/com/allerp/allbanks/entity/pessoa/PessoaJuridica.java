package br.com.allerp.allbanks.entity.pessoa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table
@PrimaryKeyJoinColumn(name="pj_cod", referencedColumnName="codigo")
public class PessoaJuridica extends Pessoa<PessoaJuridica> {
	
	private static final long serialVersionUID = 7348650644568929564L;
	
	@Column(nullable = false, length = 20)
	private String cnpj;
	
	@Column(nullable = false, length = 200)
	private String razaoSocial;

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
}
