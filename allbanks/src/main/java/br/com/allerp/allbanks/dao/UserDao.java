package br.com.allerp.allbanks.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import br.com.allerp.allbanks.entity.user.User;

public class UserDao<T> extends GenericDao<User<T>, Long> {

	private Session sessao;
	private String sql;
	private Query query;
	
	@Transactional(readOnly = true)
	public User<T> findUser(String userAccess) {
		sessao = getSessionFactory().openSession();
		sql = "SELECT us FROM User us WHERE userAccess like :userAccess";
		query = sessao.createQuery(sql);
		query.setParameter("userAccess", "%" + userAccess + "%");
		@SuppressWarnings("unchecked")
		User<T> user = (User<T>) query.uniqueResult();
		if(user == null) {
			return null;
		}
		return user;
	}
}
