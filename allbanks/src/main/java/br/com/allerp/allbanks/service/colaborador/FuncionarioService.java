package br.com.allerp.allbanks.service.colaborador;

import java.util.List;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import br.com.allerp.allbanks.dao.colaborador.FuncionarioDao;
import br.com.allerp.allbanks.entity.colaborador.Funcionario;
import br.com.allerp.allbanks.service.GenericService;

public class FuncionarioService extends GenericService<Funcionario> {
	private FuncionarioDao funcDao;

	private Search search;
	private Filter filter;
	
	public void setFuncDao(FuncionarioDao funcDao) {
		super.setDao(funcDao);
		this.funcDao = funcDao;
	}
	
	/**
	 * 
	 * @param nome
	 * @param cpf
	 * @param funcao
	 * @return Retornar uma lista de funcionarios de acordo com a pesquisa
	 */
	public List<Funcionario> search(String nome, String cpf, String funcao){
		search = new Search(Funcionario.class);
		
		filter = Filter.or(Filter.ilike("nome", "%" + nome + "%"),
				Filter.ilike("cpf",  "%" + cpf + "%"),
				Filter.ilike("funcao",  "%" + funcao + "%"));
		
		search.addFilter(filter);
		
		return funcDao.search(search);
	}
}
