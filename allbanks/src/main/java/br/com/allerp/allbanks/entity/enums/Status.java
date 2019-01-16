package br.com.allerp.allbanks.entity.enums;

public enum Status {
	ATIVO("Ativo"),
	INATIVO("Inativo");
	
	private String text;
	
	Status(String text){
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
