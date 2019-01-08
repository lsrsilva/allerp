package br.com.allerp.allbanks.entity.enums;

public enum Dependencias {

	CONJUGUE("Cônjugue"), FILHO("Filho(a) ou Enteadeado(a)"), IRMAO("Irmão(ã)"), PAI("Pai"), MAE("Mãe");

	private final String text;

	Dependencias(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}