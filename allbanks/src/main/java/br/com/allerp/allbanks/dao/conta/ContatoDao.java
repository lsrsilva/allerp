package br.com.allerp.allbanks.dao.conta;

import br.com.allerp.allbanks.dao.GenericDao;
import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Contato;

public class ContatoDao extends GenericDao<Contato, Long> {
	public ContatoDao() {
		super(Contato.class);
	}

	public void saveOrUpdate(Contato entity, Conta conta) {
		super.saveOrUpdate(entity);
	}
}
