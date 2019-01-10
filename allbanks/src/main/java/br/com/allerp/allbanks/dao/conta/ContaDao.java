package br.com.allerp.allbanks.dao.conta;

import java.math.BigDecimal;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.allerp.allbanks.dao.GenericDao;
import br.com.allerp.allbanks.entity.conta.Conta;

@Repository
public class ContaDao extends GenericDao<Conta, Long> {

	/*private Query hqlQuery;
	private String hql;*/
	private SQLQuery sqlQuery;
	private String sql;
	
	private Session session;

	public ContaDao() {
		super(Conta.class);
	}

	@Transactional
	public BigDecimal consultaSaldo(Integer numConta) {
		
		session = getSession();
		sql = "SELECT ct.saldo FROM conta ct WHERE ct.numConta = :numConta";
		sqlQuery = session.createSQLQuery(sql);
		sqlQuery.setParameter("numConta", numConta);

		return (BigDecimal) sqlQuery.uniqueResult();
	}

}
