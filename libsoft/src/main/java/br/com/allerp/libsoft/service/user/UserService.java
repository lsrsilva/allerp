package br.com.allerp.libsoft.service.user;

import java.io.Serializable;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Service;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import br.com.allerp.libsoft.dao.user.UserDao;
import br.com.allerp.libsoft.entity.user.PessoaFisica;

@Service
public class UserService {

	@SpringBean(name = "userDao")
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public boolean autenticaUser(String userAccess, String psw, String perfil) {
		return userDao.findUser(userAccess, psw, perfil);
	}

	public void deleteById(Serializable id) {
		userDao.deleteById(id);
	}
}
