package br.com.allerp.libsoft.entity.livro;

import java.io.Serializable;
import java.util.Calendar;

public class Livro implements Serializable {

	private static final long serialVersionUID = 109246501489721424L;

	private Calendar anoLanc;
	private Autor autor;
	private String editora;
	private String titulo;
	private String subtitulo;

	public Calendar getAnoLanc() {
		return anoLanc;
	}

	public void setAnoLanc(Calendar anoLanc) {
		this.anoLanc = anoLanc;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSubtitulo() {
		return subtitulo;
	}

	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}

}
