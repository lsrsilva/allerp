package br.com.allerp.allbanks.service.conta;

import org.springframework.stereotype.Service;

import br.com.allerp.allbanks.dao.conta.TitularDao;
import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.service.GenericService;

@Service
public class TitularService extends GenericService<Titular> {
	
	private TitularDao titularDao;
	
	public void setTitularDao(TitularDao titularDao) {
		setDao(titularDao);
		this.titularDao = titularDao;
	}
	
}
