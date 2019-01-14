package br.com.allerp.allbanks.entity.conta;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "CT_SALARIO")
@PrimaryKeyJoinColumn(name = "ctsal_cod", referencedColumnName = "codigo")
public class CtSalario extends Conta {

	private static final long serialVersionUID = 2994805495139607703L;

	public CtSalario() {
		super();
	}
	
}
