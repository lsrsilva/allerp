package br.com.allerp.allbanks.service.conta;

import java.util.List;

import org.springframework.stereotype.Service;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import br.com.allerp.allbanks.dao.conta.TitularDao;
import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.service.GenericService;

@Service
public class TitularService extends GenericService<Titular> {

	private TitularDao titularDao;

	private Search search;
	private Filter filter;

	public void setTitularDao(TitularDao titularDao) {
		setDao(titularDao);
		this.titularDao = titularDao;
	}

	public List<Titular> search(String cpf, String nome) {

		search = new Search(Titular.class);

		filter = Filter.or(Filter.ilike("cpf", "%" + cpf + "%"), Filter.ilike("nome", "%" + nome + "%"));

		search.addFilter(filter);

		return titularDao.search(search);
	}

}
