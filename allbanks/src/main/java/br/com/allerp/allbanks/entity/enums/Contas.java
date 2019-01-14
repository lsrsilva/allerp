package br.com.allerp.allbanks.entity.enums;

public enum Contas {

	INDIVIDUAL("Individual"),
	COLETIVA_SOLIDARIA("Coletiva Solidária"),
	COLETIVA_CONJUNTA("Coletiva Contjunta"),
	MISTA("Mista"),
	UNIVERSITARIA("Universitária"),
	DIGITAL("Digital"),
	CORRENTE("Corrente"),
	POUPANCA("Poupança");
	
	private String text;
	
	Contas(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
}
