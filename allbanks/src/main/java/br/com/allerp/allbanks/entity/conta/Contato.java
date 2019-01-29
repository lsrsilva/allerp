package br.com.allerp.allbanks.entity.conta;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import br.com.allerp.allbanks.entity.GenericEntity;

@Entity
@Table(name = "CONTATOS")
public class Contato extends GenericEntity {

	private static final long serialVersionUID = 7292792173768982050L;

	@ManyToOne
	@JoinColumn(name = "tit_cod")
	@ForeignKey(name = "FK_TIT_CONTATO")
	private Titular titular;

	@OneToOne
	@JoinColumn(name = "ct_contato")
	@ForeignKey(name = "FK_CT_CONTATO")
	private Conta ctContato;

	public Titular getTitular() {
		return titular;
	}

	public void setTitular(Titular titular) {
		this.titular = titular;
	}

	public Conta getCtContato() {
		return ctContato;
	}

	public void setCtContato(Conta ctContato) {
		this.ctContato = ctContato;
	}

}
