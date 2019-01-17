package br.com.allerp.allbanks.service.conta;

import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import br.com.allerp.allbanks.dao.conta.ContaDao;
import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.service.GenericService;

public class ContaService extends GenericService<Conta> {

	@SpringBean(name = "contaDao")
	private ContaDao contaDao;

	private Search search;
	private Filter filter;

	public void setContaDao(ContaDao contaDao) {
		super.setDao(contaDao);
		this.contaDao = contaDao;
	}

	public Float saque(Float qtdSaque) {
		contaDao.consultaSaldo(qtdSaque);

		return 0F;
	}

	public List<Conta> search(String codAg, String numConta, String status) {
		search = new Search(Conta.class);
		search.addFetch("agenci");

		filter = Filter.or(Filter.ilike("agencia.codAg", "%" + codAg + "%"),
				Filter.ilike("numConta", "%" + numConta + "%"),
				Filter.ilike("status", "%" + status + "%"));

		search.addFilter(filter);

		return contaDao.search(search);
	}

}
