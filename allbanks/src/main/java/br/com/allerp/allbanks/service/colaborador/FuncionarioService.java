package br.com.allerp.allbanks.service.colaborador;

import br.com.allerp.allbanks.dao.colaborador.FuncionarioDao;
import br.com.allerp.allbanks.entity.colaborador.Funcionario;
import br.com.allerp.allbanks.service.GenericService;

public class FuncionarioService extends GenericService<Funcionario> {
	private FuncionarioDao funcDao;
	
	public void setFuncDao(FuncionarioDao funcDao) {
		super.setDao(funcDao);
		this.funcDao = funcDao;
	}
}
