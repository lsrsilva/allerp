package br.com.allerp.allbanks.service;

import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import br.com.allerp.allbanks.AllbanksSession;
import br.com.allerp.allbanks.dao.UserDao;
import br.com.allerp.allbanks.entity.enums.Perfis;
import br.com.allerp.allbanks.entity.user.User;

public class UserService extends GenericService<User> {

	@SpringBean(name = "userDao")
	private UserDao userDao;

	private Search search;
	private Filter filter;

	public void setUserDao(UserDao userDao) {
		super.setDao(userDao);
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

	/**
	 * 
	 * @param acesso Acesso do usuário
	 * @param perfil Perfil do usuários
	 * @return Uma lista de usuários de acordo com a pesquisa
	 */
	public List<User> search(String acesso, Perfis perfil) {
		search = new Search(User.class);

		filter = (Filter.and(Filter.ilike("userAccess", "%" + acesso + "%"),
				Filter.equal("perfil", perfil)));

		search.addFilter(filter);
		
		return userDao.search(search);
	}
}
