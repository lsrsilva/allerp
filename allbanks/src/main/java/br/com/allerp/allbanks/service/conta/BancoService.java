package br.com.allerp.allbanks.service.conta;

import java.util.List;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import br.com.allerp.allbanks.dao.conta.BancoDao;
import br.com.allerp.allbanks.entity.conta.Banco;
import br.com.allerp.allbanks.service.GenericService;

public class BancoService extends GenericService<Banco> {

	private BancoDao bancoDao;

	private Search search;
	private Filter filter;

	public void setBancoDao(BancoDao bancoDao) {
		super.setDao(bancoDao);
		this.bancoDao = bancoDao;
	}

	public List<String> listBcNames() {
		return bancoDao.listBcNames();
	}

	public boolean camposSaoValidos(Banco banco) {

		if(existeBanco(banco)) {
			
		}

		if (banco.getCodCompensacao().equals("") && banco.getNome().equals("")) {
			mensagens.add("Favor preencher os campos obrigatórios.");
			return false;
		}

		if (banco.getCodCompensacao().equals("")) {
			mensagens.add("O campo Código de Compensação deve ser preenchido.");
		}

		if (banco.getCodCompensacao().equals("0")) {
			mensagens.add("O campo Código de Compensação não deve ser 0.");
		}

		if (banco.getNome().equals("")) {
			mensagens.add("O campo nome deve ser preenchido.");
		}

		if (mensagens.size() > 0) {
			return false;
		}

		mensagens.add("Banco salvo com sucesso!");
		return true;
	}

	public boolean existeBanco(Banco banco) {
		Banco bc = bancoDao.consultaBanco(banco);

		if (bc != null) {
			if(bc.getCodigo() == banco.getCodigo()) {
				return false;
			} else if (bc.getCodCompensacao().equals(banco.getCodCompensacao())) {
				if (bc.getNome().equals(banco.getNome())) {
					mensagens.add("Já existe um banco cadastrado com o código " + bc.getCodCompensacao()
							+ " e com o nome " + bc.getNome() + "!");
					return true;
				} else {
					mensagens.add("Já existe um banco cadastrado com o código " + bc.getCodCompensacao() + "!");
					return true;
				}
			} else if (bc.getNome().equals(banco.getNome())) {
				mensagens.add("Já existe um banco cadastrado com o nome " + bc.getNome() + "!");
				return true;
			}

		}

		return false;
	}

	public List<Banco> search(String codBc, String nome) {
		search = new Search(Banco.class);

		filter = Filter.or(Filter.ilike("codCompensacao", "%" + codBc + "%"), Filter.ilike("nome", "%" + nome + "%"));

		search.addFilter(filter);

		return bancoDao.search(search);
	}

}
