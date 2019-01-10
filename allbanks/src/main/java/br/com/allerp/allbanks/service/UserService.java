package br.com.allerp.allbanks.service;

import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.AllbanksSession;
import br.com.allerp.allbanks.dao.UserDao;
import br.com.allerp.allbanks.entity.user.User;

public class UserService extends GenericService<User> {

	@SpringBean(name = "userDao")
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public boolean autentica(String username, String psw) {
		if (username != null && psw != null) {
			User user = userDao.findUser(username);
			if (user != null) {
				if (user.getPsw().equals(psw)) {
					AllbanksSession.get().setUser(user);
					return true;
				}

			}
		}

		return false;
	}
}
