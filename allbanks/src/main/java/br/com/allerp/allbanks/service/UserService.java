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

	private List<User> usersExistentes;
	
	public void setUserDao(UserDao userDao) {
		super.setDao(userDao);
		this.userDao = userDao;
	}
	
	public void saveOrUpdate(User user) {

		if (user.getEmail() == null || user.getEmail().equals("")
				&& user.getUserAccess() == null || user.getUserAccess().equals("")
				&& user.getPsw() == null || user.getPsw().equals("")
				&& user.getPerfil() == null) {
			mensagens.add("Favor preencher os campos obrigatórios.");
			return;
		}

		if (user.getEmail() == null) {
			mensagens.add("O campo E-mail deve ser preenchido.");
			return;
		}

		if (user.getUserAccess() == null) {
			mensagens.add("O campo Acesso deve ser preenchido.");
			return;
		}

		if (user.getPerfil() == null) {
			mensagens.add("O campo Perfil deve ser preenchido.");
			return;
		}

		if (user.getPsw() == null) {
			mensagens.add("O campo Senha deve ser preenchido.");
			return;
		}

		if (existeUser(user)) {
			mensagens.add("Já existe um usuário " + user.getUserAccess());
			return;
		}

		super.saveOrUpdate(user);
		mensagens.add("Usuário " + user.getUserAccess() + " salvo com sucesso!");
	}

	public boolean existeUser(User user) {

		usersExistentes = userDao.findAll();
		for (User u : usersExistentes) {
			if (u.getUserAccess().equals(user.getUserAccess())) {
				return true;
			}
		}
		return false;
	}

	public boolean existeUser(String username, String psw) {
		if(username.equals("") || username == null && psw.equals("") || psw == null) {
			mensagens.add("Favor preencher os campos Username e Senha.");
			return false;
		}
		
		if (username != null && psw != null) {
			User user = userDao.findUser(username);
			if (user != null) {
				if (user.getPsw().equals(psw)) {
					AllbanksSession.get().setUser(user);
					return true;
				}
			}
		}
		mensagens.add("Usuário ou senha inválidos!");
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

		filter = (Filter.and(Filter.ilike("userAccess", "%" + acesso + "%"), Filter.equal("perfil", perfil)));

		search.addFilter(filter);

		return userDao.search(search);
	}
}
