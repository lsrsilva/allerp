package br.com.allerp.allbanks.dao.colaborador;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.allerp.allbanks.dao.GenericDao;
import br.com.allerp.allbanks.entity.colaborador.Funcionario;

public class FuncionarioDao extends GenericDao<Funcionario, Long>{

	public FuncionarioDao() {
		super(Funcionario.class);
	}
	
	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
	public Funcionario consultaFuncionario(Funcionario funcionario) {
		setHsql("FROM Funcionario f WHERE f.cpf like :cpf");
		setQuery(getSession().createQuery(getHsql()));
		getQuery().setParameter("cpf", funcionario.getCpf());
		
		Funcionario func = (Funcionario) getQuery().uniqueResult();
		
		return func;
	}
	
}
