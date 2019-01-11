package br.com.allerp.allbanks.dao.conta;

import br.com.allerp.allbanks.dao.GenericDao;
import br.com.allerp.allbanks.entity.conta.Agencia;

public class AgenciaDao extends GenericDao<Agencia, Long> {

	public AgenciaDao() {
		super(Agencia.class);
	}
}
