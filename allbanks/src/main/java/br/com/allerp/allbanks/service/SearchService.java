package br.com.allerp.allbanks.service;

import java.util.List;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import br.com.allerp.allbanks.dao.conta.ContaDao;
import br.com.allerp.allbanks.dao.conta.TitularDao;
import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Titular;

public class SearchService {

	private Search search;

	private Filter filter;

	public SearchService() {
		search = new Search(Titular.class);
	}

	/* ================================ TITULAR ========================================= */
	private TitularDao titularDao;

	public List<Titular> buscaTitular(String cpfCnpj, String nome) {
		filter = Filter.or(Filter.ilike("pf.cpf", "%" + cpfCnpj + "%"), Filter.ilike("pj.cnpj", "%" + cpfCnpj + "%"),
				Filter.ilike("pf.nome", "%" + nome + "%"), Filter.ilike("pj.razaoSocial", "%" + nome + "%"));

		search.addFilter(filter);

		return titularDao.search(search);
	}
	
	/* ================================================================================== */
	
	/* ================================ TITULAR ========================================= */
	private ContaDao contaDao;

	public List<Conta> buscaConta(String agencia, String numConta, String titularNome) {
		filter = Filter.or(Filter.ilike("agencia", "%" + agencia + "%"), Filter.ilike("numConta", "%" + numConta + "%"),
				Filter.ilike("numConta", "%" + titularNome + "%"), Filter.ilike("titular.pj.razaoSocial", "%" + numConta + "%"));

		search.addFilter(filter);

		return contaDao.search(search);
	}
	
	/* ================================================================================== */

}
