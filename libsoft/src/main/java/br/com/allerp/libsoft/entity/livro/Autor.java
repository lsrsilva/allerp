package br.com.allerp.libsoft.entity.livro;

import java.io.Serializable;

public class Autor implements Serializable {

	private static final long serialVersionUID = 4328438271216791661L;
	
	private String nome;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
