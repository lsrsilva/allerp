package br.com.allerp.libsoft.entity.user;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table
@PrimaryKeyJoinColumn(referencedColumnName = "id_user")
@ForeignKey(name="FK_ID_PF")
public class Reservista extends PessoaFisica{

	private static final long serialVersionUID = -7208045964604441766L;

	public Reservista() {
		super.setPerfil("Reservista");
	}
	
}
