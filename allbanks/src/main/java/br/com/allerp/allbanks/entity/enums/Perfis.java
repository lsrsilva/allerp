package br.com.allerp.allbanks.entity.enums;

public enum Perfis {
	
	GERENTE("Gerente"),
	FUNCIONARIO("Funcionário"),
	TITULAR("Titular");
	
	private final String text;

	Perfis(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
	
}
