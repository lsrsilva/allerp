package br.com.allerp.libsoft.dao;

import java.io.Serializable;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface IAbstractGenericDao<Entity, ID extends Serializable> extends GenericDAO<Entity, ID> {
	public void salvar(Entity entity);
}
