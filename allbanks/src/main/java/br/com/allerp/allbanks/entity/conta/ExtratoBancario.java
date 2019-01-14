package br.com.allerp.allbanks.entity.conta;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.allerp.allbanks.entity.GenericEntity;

@Entity
@Table(name = "EXTRATO_BANCARIO")
public class ExtratoBancario extends GenericEntity {

	private static final long serialVersionUID = 4396459324533468341L;

}
