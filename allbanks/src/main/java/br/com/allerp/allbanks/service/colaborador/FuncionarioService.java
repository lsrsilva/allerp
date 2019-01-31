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

	public boolean camposSaoValidos(Funcionario func) {
		
		if (existeFunc(func)) {
			mensagens.add("Funcionário já cadastrado.");
		}

		if (func.getCelular().equals("") && func.getNome().equals("") && func.getCpf().equals("")
				&& func.getDtNasc() == null && func.getFormacao().equals("") && func.getFuncao().equals("")
				&& func.getRg().equals("") && func.getSexo().equals('\u0000') && func.getTelefone().equals("")
				&& func.getSalario() == 0F || func.getSalario() == null) {
			mensagens.add("Favor preencher os campos obrigatórios.");
			return false;
		}

		if (func.getCelular().equals("")) {
			mensagens.add("Campo celular é obrigatório.");
		}

		if (func.getNome().equals("")) {
			mensagens.add("Campo nome é obrigatório.");
		}

		if (func.getCpf().equals("")) {
			mensagens.add("Campo celular é obrigatório.");
		}

		if (func.getDtNasc() == null) {
			mensagens.add("Campo data de nascimento é obrigatório.");
		}

		if (func.getFormacao().equals("")) {
			mensagens.add("Campo formação é obrigatório.");
		}

		if (func.getFuncao().equals("")) {
			mensagens.add("Campo função é obrigatório.");
		}

		if (func.getRg().equals("")) {
			mensagens.add("Campo rg é obrigatório.");
		}

//		if(func.getSexo().equals('\u0000')) {
//			mensagens.add("Campo sexo é obrigatório.");
//		}

		if (func.getTelefone().equals("")) {
			mensagens.add("Campo telefone é obrigatório.");
		}
		
		if (mensagens.size() > 0) {
			return false;
		}

		mensagens.add("Funcionário " + func.getNome() + " realizado com sucesso!");
		return true;
	}

	public boolean existeFunc(Funcionario func) {

		List<Funcionario> funcExistentes = funcDao.findAll();
		for (Funcionario f : funcExistentes) {
			if (f.getCpf().equals(func.getCpf())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param nome
	 * @param cpf
	 * @param funcao
	 * @return Uma lista de funcionarios de acordo com a pesquisa
	 */
	public List<Funcionario> search(String nome, String cpf, String funcao) {
		search = new Search(Funcionario.class);

		filter = Filter.or(Filter.ilike("nome", "%" + nome + "%"), Filter.ilike("cpf", "%" + cpf + "%"),
				Filter.ilike("funcao", "%" + funcao + "%"));

		search.addFilter(filter);

		return funcDao.search(search);
	}
}
