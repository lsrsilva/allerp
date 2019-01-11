package br.com.allerp.allbanks.service.conta;

import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.dao.conta.ContaDao;
import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.service.GenericService;

public class ContaService extends GenericService<Conta> {
	
	@SpringBean(name = "contaDao")
	private ContaDao contaDao;
	
	public void setContaDao(ContaDao contaDao) {
		super.setDao(contaDao);
		this.contaDao = contaDao;
	}
	
	public Float saque(Float qtdSaque) {
		contaDao.consultaSaldo(qtdSaque);
		
		return 0F;
	}
	
}
