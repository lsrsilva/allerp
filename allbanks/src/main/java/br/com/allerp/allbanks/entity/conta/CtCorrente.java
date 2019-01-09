package br.com.allerp.allbanks.entity.conta;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "ctcrr_cod", referencedColumnName = "codigo")
@ForeignKey(name = "FK_CRR_CT")
public class CtCorrente extends Conta {

	private static final long serialVersionUID = -4673635140990291884L;

	public CtCorrente() {
		super();
	}
	
}
