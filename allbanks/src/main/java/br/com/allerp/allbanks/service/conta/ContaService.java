package br.com.allerp.allbanks.service.conta;

import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.dao.conta.ContaDao;
import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.service.GenericService;

public class ContaService extends GenericService<Titular> {
	
	@SpringBean(name = "contaDao")
	private ContaDao contaDao;
	
	public void setContaDao(ContaDao contaDao) {
		this.contaDao = contaDao;
	}
	
	public Float saque(Float qtdSaque) {
		//contaDao.consultaSaldo(numConta);
		
		return 0F;
	}
	
}
