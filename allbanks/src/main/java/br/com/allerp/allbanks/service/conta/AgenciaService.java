package br.com.allerp.allbanks.service.conta;

import org.springframework.stereotype.Service;

import br.com.allerp.allbanks.dao.conta.AgenciaDao;
import br.com.allerp.allbanks.entity.conta.Agencia;
import br.com.allerp.allbanks.service.GenericService;

@Service
public class AgenciaService extends GenericService<Agencia> {

	private AgenciaDao agenciaDao;

	public void setAgenciaDao(AgenciaDao agenciaDao) {
		super.setDao(agenciaDao);
	}

}
