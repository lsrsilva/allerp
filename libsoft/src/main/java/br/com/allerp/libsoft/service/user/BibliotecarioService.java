package br.com.allerp.libsoft.service.user;

import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Service;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import br.com.allerp.libsoft.dao.user.BibliotecarioDao;
import br.com.allerp.libsoft.entity.user.Bibliotecario;
import br.com.allerp.libsoft.entity.user.PessoaFisica;

@Service
public class BibliotecarioService extends UserService {

	@SpringBean(name = "bibliotecarioDao")
	private BibliotecarioDao bibliotecarioDao;
	
	public void setBibliotecarioDao(BibliotecarioDao bibliotecario) {
		this.bibliotecarioDao = bibliotecario;
	}
	
	public void saveOrUpdate(Bibliotecario bibliotecario) {
		bibliotecarioDao.saveOrUpdate(bibliotecario);
	}
	
	public List<Bibliotecario> findAll(){
		return bibliotecarioDao.findAll();
	}
	
	public void update(Bibliotecario... bibliotecario) {
		bibliotecarioDao.update(bibliotecario);
	}
	
	public void delete(Bibliotecario bibliotecario) {
		bibliotecarioDao.delete(bibliotecario);
	}
	
	public List<Bibliotecario> search(String cpf, String userAccess) {

		Search search = new Search(Bibliotecario.class);
		
		Filter filter = Filter.or(Filter.ilike("cpf", "%" + cpf + "%"), Filter.ilike("userAccess", "%" + userAccess + "%"));
		
		search.addFilter(filter);

		return bibliotecarioDao.search(search);

	}
}
