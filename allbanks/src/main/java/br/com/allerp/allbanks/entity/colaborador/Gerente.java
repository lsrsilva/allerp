package br.com.allerp.allbanks.entity.colaborador;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table
public class Gerente extends Funcionario {

	private static final long serialVersionUID = -6999200984936878729L;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "dpto_cod", referencedColumnName = "codigo")
	@ForeignKey(name = "FK_GER_DPTO")
	private Departamento dpto;

	public Departamento getDpto() {
		return dpto;
	}

	public void setDpto(Departamento dpto) {
		this.dpto = dpto;
	}

}
