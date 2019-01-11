package br.com.allerp.allbanks.dao.conta;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.allerp.allbanks.dao.GenericDao;
import br.com.allerp.allbanks.entity.conta.Conta;

@Repository
public class ContaDao extends GenericDao<Conta, Long> {

	public ContaDao() {
		super(Conta.class);
	}

	@Transactional
	public BigDecimal consultaSaldo(Float qtdSaque) {
		setHsql("SELECT ct.saldo FROM conta ct WHERE ct.numConta = :numConta");
		setSqlQuery(getSession().createSQLQuery(getHsql()));
		getSqlQuery().setParameter("numConta", qtdSaque);

		return (BigDecimal) getSqlQuery().uniqueResult();
	}

}
