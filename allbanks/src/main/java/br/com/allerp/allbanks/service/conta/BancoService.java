package br.com.allerp.allbanks.service.conta;

import java.util.List;

import br.com.allerp.allbanks.dao.conta.BancoDao;
import br.com.allerp.allbanks.entity.conta.Banco;
import br.com.allerp.allbanks.service.GenericService;

public class BancoService extends GenericService<Banco> {

	private BancoDao bancoDao;

	public void setBancoDao(BancoDao bancoDao) {
		super.setDao(bancoDao);
		this.bancoDao = bancoDao;
	}

	public List<String> listBcNames() {
		return bancoDao.listBcNames();
	}

}
