package br.com.allerp.libsoft.dao.user;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.allerp.libsoft.dao.AbstractGenericDao;
import br.com.allerp.libsoft.entity.user.User;

@Repository
public class UserDao extends AbstractGenericDao<User, Long> {
	
	private Session sessao;
	private String sql;
	private Query query;
	
	@Transactional(readOnly = true)
	public boolean findUser(String userAccess, String psw, String perfil) {
		sessao = getSessionFactory().openSession();
		sql = "SELECT userAccess, psw FROM User WHERE userAccess = :userAccess AND psw = :psw AND perfil = :perfil";
		query = sessao.createQuery(sql);
		query.setParameter("userAccess", userAccess);
		query.setParameter("psw", psw);
		query.setParameter("perfil", perfil);
		if(query.uniqueResult() == null) {
			return false;
		}
		return true;
	}
	
}
