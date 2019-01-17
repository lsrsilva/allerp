package br.com.allerp.allbanks.service.conta;

import java.util.List;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import br.com.allerp.allbanks.dao.conta.BancoDao;
import br.com.allerp.allbanks.entity.conta.Banco;
import br.com.allerp.allbanks.service.GenericService;

public class BancoService extends GenericService<Banco> {

	private BancoDao bancoDao;

	private Search search;
	private Filter filter;

	public void setBancoDao(BancoDao bancoDao) {
		super.setDao(bancoDao);
		this.bancoDao = bancoDao;
	}

	public List<String> listBcNames() {
		return bancoDao.listBcNames();
	}

	public List<Banco> search(String codBc, String nome) {
		search = new Search(Banco.class);

		filter = Filter.or(Filter.ilike("cod_compensacao", "%" + codBc + "%"), Filter.ilike("nome", "%" + nome + "%"));
		
		search.addFilter(filter);

		return bancoDao.search(search);
	}

}
