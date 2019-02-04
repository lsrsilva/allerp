package br.com.allerp.allbanks.dao.conta;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.allerp.allbanks.dao.GenericDao;
import br.com.allerp.allbanks.entity.conta.Conta;

@Repository
public class ContaDao extends GenericDao<Conta, Long> {

	public ContaDao() {
		super(Conta.class);
	}

	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation =  Propagation.SUPPORTS)
	public BigDecimal consultaSaldo(Integer numConta) {
		setHsql("SELECT ct.saldo FROM conta ct WHERE ct.numConta = :numConta");
		setSqlQuery(getSession().createSQLQuery(getHsql()));
		getSqlQuery().setParameter("numConta", numConta);

		return (BigDecimal) getSqlQuery().uniqueResult();
	}
	
	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
	public Conta consultaConta(Conta conta) {
		setHsql("FROM Conta ct WHERE ct.numConta :numConta");
		setQuery(getSession().createQuery(getHsql()));
		getQuery().setParameter("numConta", conta.getNumConta());
		
		Conta ct = (Conta) getQuery().uniqueResult();
		return ct;
	}

}
