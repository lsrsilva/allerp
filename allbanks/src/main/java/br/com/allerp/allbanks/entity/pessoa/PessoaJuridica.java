package br.com.allerp.allbanks.entity.pessoa;

public class PessoaJuridica extends Pessoa {
	
	private static final long serialVersionUID = 7348650644568929564L;
	
	private String cnpj;
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
