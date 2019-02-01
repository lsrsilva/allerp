package br.com.allerp.allbanks.service.conta;

import java.util.List;

import org.springframework.stereotype.Service;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import br.com.allerp.allbanks.dao.conta.AgenciaDao;
import br.com.allerp.allbanks.entity.conta.Agencia;
import br.com.allerp.allbanks.service.GenericService;

@Service
public class AgenciaService extends GenericService<Agencia> {

	private AgenciaDao agenciaDao;

	private Search search;
	private Filter filter;

	public void setAgenciaDao(AgenciaDao agenciaDao) {
		super.setDao(agenciaDao);
		this.agenciaDao = agenciaDao;
	}

	/**
	 * 
	 * @param codAg  Código da Agência
	 * @param codBc  Código de Compensação do Banco
	 * @param nomeBc Nome do Banco
	 * @return Uma lista das agências de acordo com a pesquisa realizada
	 */
	public List<Agencia> search(Integer codAg, String codBc, String nomeBc) {
		search = new Search(Agencia.class);

		filter = Filter.or(Filter.equal("codAg", codAg), Filter.ilike("banco.codCompensacao", "%" + codBc + "%"),
				Filter.ilike("banco.nome", "%" + nomeBc + "%"));

		search.addFilter(filter);

		return agenciaDao.search(search);
	}

	public boolean camposSaoValido(Agencia agencia) {

		existeAgencia(agencia);

		try {
			if (agencia.getBanco() == null && agencia.getCodAg() == null) {
				mensagens.add("Favor preencher os campos obrigatórios.");
				return false;
			}
		} catch (NullPointerException ne) {
			mensagens.add("Favor preencher os campos obrigatórios.");
			return false;
		}

		if (agencia.getBanco() == null) {
			mensagens.add("O campo Banco é obrigatório.");
		}

		if (agencia.getCodAg() == 0) {
			mensagens.add("O campo Código da Agência não pode ser 0.");
		}

		if (mensagens.size() > 0) {
			return false;
		}

		mensagens.add("Agência " + agencia.getCodAg() + " salva com sucesso!");
		return true;
	}

	public boolean existeAgencia(Agencia agencia) {
		List<Agencia> agencias = agenciaDao.findAll();

		for (Agencia ag : agencias) {
			if (ag.getCodigo() == agencia.getCodigo()) {
				mensagens.add("Agência " + ag.getCodAg() + " já cadastrada para este banco.");
				return false;
			}
		}
		return true;
	}

}