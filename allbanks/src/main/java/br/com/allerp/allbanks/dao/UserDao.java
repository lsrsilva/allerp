package br.com.allerp.allbanks.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import br.com.allerp.allbanks.entity.user.User;

public class UserDao extends GenericDao<User, Long> {

	public UserDao() {
		super(User.class);
	}

	private Session sessao;
	private String sql;
	private Query query;
	
	@Transactional(readOnly = true)
	public User findUser(String userAccess) {
		sessao = getSessionFactory().openSession();
		sql = "SELECT us FROM User us WHERE userAccess like :userAccess";
		query = sessao.createQuery(sql);
		query.setParameter("userAccess", "%" + userAccess + "%");
		User user = (User) query.uniqueResult();
		if(user == null) {
			return null;
		}
		return user;
	}
}
