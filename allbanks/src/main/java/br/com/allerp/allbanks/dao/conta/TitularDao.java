package br.com.allerp.allbanks.dao.conta;

import org.springframework.stereotype.Repository;

import br.com.allerp.allbanks.dao.GenericDao;
import br.com.allerp.allbanks.entity.conta.Titular;

@Repository
public class TitularDao extends GenericDao<Titular, Long> {

	public TitularDao() {
		super(Titular.class);
		
	}
	
}
