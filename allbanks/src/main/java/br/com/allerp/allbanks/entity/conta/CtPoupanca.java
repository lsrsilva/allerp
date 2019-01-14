package br.com.allerp.allbanks.entity.conta;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "CT_POUPANCA")
@PrimaryKeyJoinColumn(name = "ctpoup_cod", referencedColumnName = "codigo")
@ForeignKey(name="FK_POUP_CT")
public class CtPoupanca extends Conta {

	private static final long serialVersionUID = -6154753431975629201L;

	public CtPoupanca() {
		super();
	}
	
}
