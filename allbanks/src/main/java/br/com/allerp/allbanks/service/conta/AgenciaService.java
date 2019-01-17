package br.com.allerp.allbanks.service.conta;

import java.util.List;

import org.springframework.stereotype.Service;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import br.com.allerp.allbanks.dao.conta.AgenciaDao;
import br.com.allerp.allbanks.entity.conta.Agencia;
import br.com.allerp.allbanks.service.GenericService;

@Service
public class AgenciaService extends GenericService<Agencia> {

	private AgenciaDao agenciaDao;

	private Search search;
	private Filter filter;

	public void setAgenciaDao(AgenciaDao agenciaDao) {
		super.setDao(agenciaDao);
	}

	/**
	 * 
	 * @param codAg  Código da Agência
	 * @param codBc  Código de Compensação do Banco
	 * @param nomeBc Nome do Banco
	 * @return Uma lista das agências de acordo com a pesquisa realizada
	 */
	public List<Agencia> search(Integer codAg, String codBc, String nomeBc) {
		search = new Search(Agencia.class);
		
		search.addFetch("banco");
		
		filter = Filter.or(Filter.equal("codAg", codAg), Filter.ilike("banco.cod_compensacao", "%" + codBc + "%"),
				Filter.ilike("banco.nome", "%" + nomeBc + "%"));

		search.addFilter(filter);

		return agenciaDao.search(search);
	}

}