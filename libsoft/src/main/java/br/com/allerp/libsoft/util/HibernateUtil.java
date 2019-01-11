package br.com.allerp.libsoft.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil<T> {
	private static final SessionFactory sessionFactory = buildSessionFactory();// Guardará a fábrica de sessões
	
	/* Config Hibernate 3 */
	
	// 1º ler o hibernate.cfg.xml
	private static SessionFactory buildSessionFactory() {
		/**
		 * 1º Criar a SessionFactory
		 * 2º Criar sessões da SessionFactory
		 * 3º Usar a sessão para salvar os objetos
		 */
		try {
			// Carrega as propriedades do hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch(HibernateException he) {
			System.err.println("Falha na criação da SessionFactory." + he);
			throw new ExceptionInInitializerError(he);
		} catch(Throwable th) {
			System.err.println("Falha na criação da SessionFactory." + th);
			throw new ExceptionInInitializerError(th);
		}
		
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	
}
