package br.com.allerp.allbanks.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.allerp.allbanks.dao.GenericDao;

@Service
public class GenericService<Entity> {

	private GenericDao<Entity, Long> dao;

	public void setDao(GenericDao<Entity, Long> dao) {
		this.dao = dao;
	}

	public void saveOrUpdate(Entity entity) {
		dao.saveOrUpdate(entity);
	}

	public List<Entity> findAll() {
		return dao.findAll();
	}

	public void deleteById(Serializable... id) {
		dao.deleteById(id);
	}

	@Transactional
	public void delete(Entity entity) {
		dao.remove(entity);
	}

	@SuppressWarnings("unchecked")
	public void update(Entity... entities) {
		dao.update(entities);
	}/*
		 * 
		 * public List<Entity> search(Search search) { return dao.search(search); }
		 */

}
