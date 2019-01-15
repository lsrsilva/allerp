package br.com.allerp.allbanks.entity.pessoa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.allerp.allbanks.entity.GenericEntity;

@Entity
@Table(name = "PESSOA")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa extends GenericEntity {

	private static final long serialVersionUID = 4325236325700108451L;
	
	@Column(nullable = false, length = 14)
	private String telefone;

	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
}
