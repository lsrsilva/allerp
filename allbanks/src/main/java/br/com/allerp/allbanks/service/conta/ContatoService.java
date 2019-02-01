package br.com.allerp.allbanks.service.conta;

import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import br.com.allerp.allbanks.dao.conta.ContatoDao;
import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Contato;
import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.exceptions.FeedbackException;
import br.com.allerp.allbanks.service.GenericService;

public class ContatoService extends GenericService<Contato> {

	@SpringBean(name = "contatoDao")
	private ContatoDao contatoDao;
	
	private Search search;
	private Filter filter;

	public void setContatoDao(ContatoDao contatoDao) {
		super.setDao(contatoDao);
		this.contatoDao = contatoDao;
	}

	public void saveOrUpdate(Contato contato, Conta ctContato, Titular titular){
		contato.setCtContato(ctContato);
		contato.setTitular(titular);
		contatoDao.merge(contato);
	}

	public List<Contato> searchContatos(Titular titular) {
		search = new Search(Contato.class);
		filter = Filter.and(Filter.equal("titular.codigo", titular.getCodigo()));
		search.addFilter(filter);

		return contatoDao.search(search);

	}

}
