package br.com.allerp.allbanks.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import br.com.allerp.allbanks.dao.GenericDao;

@Service
public class GenericService<Entity> {

	private GenericDao<Entity, Long> dao;

	protected ArrayList<String> mensagens = new ArrayList<String>();

	public void setDao(GenericDao<Entity, Long> dao) {
		this.dao = dao;
	}

	@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
	public void saveOrUpdate(Entity entity) {
		if (entity == null) {
			throw new NullPointerException("Entidade nula");
		}
		dao.saveOrUpdate(entity);
	}

	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
	public List<Entity> findAll() {
		return dao.findAll();
	}

	@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
	public void deleteById(Serializable... id) {
		dao.deleteById(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Entity entity) {
		dao.remove(entity);
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Entity... entities) {
		dao.update(entities);
	}

	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
	public List<Entity> search(Search search) {
		return dao.search(search);
	}

	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
	public Entity searchUnique(Search search) {
		return dao.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void merge(Entity entity) {
		dao.merge(entity);
	}

	public ArrayList<String> getMensagens() {
		return mensagens;
	}

	public void setMensagens(ArrayList<String> mensagens) {
		this.mensagens = mensagens;
	}

}
