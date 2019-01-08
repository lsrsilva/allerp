package br.com.allerp.allbanks.entity.pessoa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ForeignKey;

import br.com.allerp.allbanks.entity.Endereco;
import br.com.allerp.allbanks.entity.GenericEntity;

public class Pessoa extends GenericEntity {

	private static final long serialVersionUID = 4325236325700108451L;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "end_codigo")
	@ForeignKey(name = "FK_PESS_END")
	private Endereco endereco;
	
	@Column(nullable = false)
	private String telefone;

	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
}
