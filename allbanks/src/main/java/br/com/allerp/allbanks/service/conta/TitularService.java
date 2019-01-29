package br.com.allerp.allbanks.service.conta;

import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Service;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import br.com.allerp.allbanks.dao.conta.TitularDao;
import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Contato;
import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.exceptions.FeedbackException;
import br.com.allerp.allbanks.service.GenericService;

@Service
public class TitularService extends GenericService<Titular> {

	private TitularDao titularDao;

	private Search search;
	private Filter filter;

	@SpringBean(name = "contatoDao")
	private ContatoService contatoService;

	public void setContatoService(ContatoService contatoService) {
		this.contatoService = contatoService;
	}

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

	/**
	 * Verifica se o titular já possui a conta em sua lista de contatos
	 * 
	 * @param ctContato conta a ser verificada
	 * @throws FeedbackException
	 */
	public boolean existeContato(Titular titular, Conta ctContato) throws FeedbackException {

		search = new Search(Contato.class);

		System.out.println("Titular: " + titular.getCodigo());
		System.out.println("Conta contato: " + ctContato.getNumConta());

		filter = Filter.and(Filter.equal("titular.codigo", titular.getCodigo()),
				Filter.equal("ctContato.numConta", ctContato.getNumConta()));

		search.addFilter(filter);

		Contato contato = contatoService.searchUnique(search);

		if (contato == null) {
			return false;
		}
		return true;
	}

}