package br.com.allerp.libsoft.entity.user;

public class PessoaJuridica extends User {

	private static final long serialVersionUID = 6794615948702804666L;

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
