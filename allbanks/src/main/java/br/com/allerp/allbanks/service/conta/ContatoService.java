package br.com.allerp.allbanks.service.conta;

import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.dao.conta.ContatoDao;
import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Contato;
import br.com.allerp.allbanks.service.GenericService;

public class ContatoService extends GenericService<Contato> {

	@SpringBean(name = "contatoDao")
	private ContatoDao contatoDao;

	public void setContatoDao(ContatoDao contatoDao) {
		super.setDao(contatoDao);
		this.contatoDao = contatoDao;
	}

}
