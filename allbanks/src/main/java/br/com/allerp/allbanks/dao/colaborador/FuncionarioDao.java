package br.com.allerp.allbanks.dao.colaborador;

import br.com.allerp.allbanks.dao.GenericDao;
import br.com.allerp.allbanks.entity.colaborador.Funcionario;

public class FuncionarioDao extends GenericDao<Funcionario, Long>{

	public FuncionarioDao() {
		super(Funcionario.class);
	}
	
}
