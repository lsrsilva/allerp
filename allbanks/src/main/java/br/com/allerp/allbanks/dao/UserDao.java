package br.com.allerp.allbanks.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Propagation;
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
		query.setParameter("userAccess", userAccess);
		User user = (User) query.uniqueResult();
		if (user == null) {
			return null;
		}
		return user;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public User consultaUser(User user) {

		setHsql("FROM User us WHERE us.userAccess like :userAccess");
		setQuery(getSession().createQuery(getHsql()));
		getQuery().setParameter("userAccess", user.getUserAccess());

		User u = (User) getQuery().uniqueResult();

		return u;

	}
}
