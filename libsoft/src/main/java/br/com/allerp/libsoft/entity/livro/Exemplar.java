package br.com.allerp.libsoft.entity.livro;

public class Exemplar extends Livro {

	private static final long serialVersionUID = 2383720732862711325L;

	public static int qtd;
	
	public Exemplar() {
		qtd += 1;
	}
	
}
