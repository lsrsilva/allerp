package br.com.allerp.libsoft.entity.user;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table
@PrimaryKeyJoinColumn(referencedColumnName = "id_user")
@ForeignKey(name = "FK_ID_PF")
public class Bibliotecario extends PessoaFisica {
	
	private static final long serialVersionUID = -6820051541417287349L;

	public Bibliotecario() {
		super.setPerfil("Bibliotecario");
	}
}
