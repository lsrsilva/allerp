package br.com.allerp.allbanks.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;

@Repository
public class GenericDao<Entity, ID extends Serializable> extends GenericDAOImpl<Entity, Serializable> {

	private Class<Entity> entityClass;

	private SQLQuery sqlQuery;
	private Query query;

	private String hsql;

	public GenericDao() {
	}

	public GenericDao(Class<Entity> entityClass) {
		this.entityClass = entityClass;
	}

	public Class<Entity> getEntityClass() {
		return entityClass;
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
	public void saveOrUpdate(Entity entity) {
		super.save(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void merge(Entity entity) {
		super._merge(entity);
	}

	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
	public List<Entity> findAll() {
		List<Entity> allResults = super.findAll();

		return allResults;
	}

	@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
	public void deleteById(Serializable... id) {
		super.removeById(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Entity entity) {
		super.remove(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(@SuppressWarnings("unchecked") Entity... entities) {
		super._update(entities);
	}

	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
	public List<Entity> search(Search search) {
		return super.search(search);
	}

	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
	public Entity searchUnique(Search search) {
		return super.searchUnique(search);
	}

	protected String getHsql() {
		return hsql;
	}

	protected void setHsql(String hsql) {
		this.hsql = hsql;
	}

	protected SQLQuery getSqlQuery() {
		return sqlQuery;
	}

	protected void setSqlQuery(SQLQuery sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	protected Query getQuery() {
		return query;
	}

	protected void setQuery(Query query) {
		this.query = query;
	}

}
