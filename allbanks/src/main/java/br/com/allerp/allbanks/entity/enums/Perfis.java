package br.com.allerp.allbanks.entity.enums;

public enum Perfis {
	
	GERENTE("Gerente"),
	FUNCIONARIO("Funcionário"),
	TITULAR("Titular");
	
	private String text;

	Perfis(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
	
	public String getText() {
		return toString();
	}
	
}
