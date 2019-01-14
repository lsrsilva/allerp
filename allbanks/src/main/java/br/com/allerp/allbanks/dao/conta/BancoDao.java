package br.com.allerp.allbanks.dao.conta;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.allerp.allbanks.dao.GenericDao;
import br.com.allerp.allbanks.entity.conta.Banco;

@Repository
public class BancoDao extends GenericDao<Banco, Long> {

	public BancoDao() {
		super(Banco.class);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> listBcNames() {
		setHsql("SELECT b.nome FROM BANCO b");
		setSqlQuery(getSession().createSQLQuery(getHsql()));
		return getSqlQuery().list();
	}

}
