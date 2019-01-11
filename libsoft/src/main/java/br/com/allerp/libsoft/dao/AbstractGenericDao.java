package br.com.allerp.libsoft.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;

@Repository
public abstract class AbstractGenericDao<Entity, ID extends Serializable>
		extends GenericDAOImpl<Entity, ID>/* implements IAbstractGenericDao<Entity, ID> */ {

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void saveOrUpdate(Entity entity) {
			super.save(entity);
	}

	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
	public List<Entity> findAll() {
			List<Entity> allResults = super.findAll();
			
			return allResults;
	}
	
	@Transactional
	public void deleteById(Serializable... id) {
		super.removeById(id);
	}
	
	@Transactional
	public void delete(Entity entity) {
		super.remove(entity);
	}
	
	@Transactional
	public void update(@SuppressWarnings("unchecked") Entity... entities) {
		super._update(entities);
		
	}
	
	@Transactional
	public List<Entity> search(Search search) {
		return super.search(search);
	}
}
