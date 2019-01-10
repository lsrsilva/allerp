package br.com.allerp.allbanks.entity.colaborador;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table
@ForeignKey(name = "FK_GER_PF")
public class Gerente extends Funcionario {

	private static final long serialVersionUID = -6999200984936878729L;

	@OneToOne(mappedBy = "gerente")
	private Departamento dpto;

	public Departamento getDpto() {
		return dpto;
	}

	public void setDpto(Departamento dpto) {
		this.dpto = dpto;
	}

}
