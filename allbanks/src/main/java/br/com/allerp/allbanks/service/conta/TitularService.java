package br.com.allerp.allbanks.service.conta;

import java.util.Date;
import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;
import org.springframework.stereotype.Service;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import br.com.allerp.allbanks.dao.conta.TitularDao;
import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Contato;
import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.exceptions.FeedbackException;
import br.com.allerp.allbanks.service.GenericService;

@Service
public class TitularService extends GenericService<Titular> {

	private TitularDao titularDao;

	private Search search;
	private Filter filter;

	@SpringBean(name = "contatoDao")
	private ContatoService contatoService;

	public void setContatoService(ContatoService contatoService) {
		this.contatoService = contatoService;
	}

	public void setTitularDao(TitularDao titularDao) {
		setDao(titularDao);
		this.titularDao = titularDao;
	}

	public List<Titular> search(String cpf, String nome) {

		search = new Search(Titular.class);

		filter = Filter.or(Filter.ilike("cpf", "%" + cpf + "%"), Filter.ilike("nome", "%" + nome + "%"));

		search.addFilter(filter);

		return titularDao.search(search);
	}

	/**
	 * Verifica se o titular já possui a conta em sua lista de contatos
	 * 
	 * @param ctContato conta a ser verificada
	 * @throws FeedbackException
	 */
	public boolean existeContato(Titular titular, Conta ctContato) {

		search = new Search(Contato.class);

		System.out.println("Titular: " + titular.getCodigo());
		System.out.println("Conta contato: " + ctContato.getNumConta());

		filter = Filter.and(Filter.equal("titular.codigo", titular.getCodigo()),
				Filter.equal("ctContato.numConta", ctContato.getNumConta()));

		search.addFilter(filter);

		Contato contato = contatoService.searchUnique(search);

		if (contato == null) {
			return false;
		}
		return true;
	}

	public boolean camposSaoValidos(Titular titular) {

		boolean retorno = true;

		String nome = titular.getNome();
		String rg = titular.getRg();
		String cpfCnpj = titular.getCpfCnpj();
		String telefone = titular.getTelefone();
		Date dtNasc = titular.getDtNasc();
		String celular = titular.getCelular();
		String ie = titular.getIe();

		String email = titular.getUser().getEmail();
		String senha = titular.getUser().getPsw();

		String cep = "";
		String rua = "";
		String bairro = "";
		String uf = "";
		String cidade = "";
		
		if(existeTitular(titular)) {
			mensagens.add("Já existe um titular com o CPF: " + titular.getCpfCnpj() + "!");
		}

		try {
			nome = titular.getNome();
			rg = titular.getRg();
			cpfCnpj = titular.getCpfCnpj();
			telefone = titular.getTelefone();
			dtNasc = titular.getDtNasc();
			celular = titular.getCelular();
			ie = titular.getIe();
		} catch (NullPointerException ne) {
			if (titular.isPf()) {
				mensagens.add("Favor preencher os dados do Titular.");
			} else if (titular.isPj()) {
				mensagens.add("Favor preencher os dados do Titular.");
			}
		}

		try {
			cep = titular.getEndereco().getCep();
			rua = titular.getEndereco().getRua();
			bairro = titular.getEndereco().getBairro();
			uf = titular.getEndereco().getUf();
			cidade = titular.getEndereco().getCidade();
		} catch (NullPointerException ne) {
			if (titular.isPf())
				mensagens.add("Favor preencher os dados de endereço.");
			else if (titular.isPj())
				mensagens.add("Favor preencher os dados de endereço.");
			return false;
		}

		if (nome == null || nome.equals("")) {

			if (titular.isPf()) {
				mensagens.add("O campo Nome deve ser preenchido.");
			}

			if (titular.isPj()) {
				mensagens.add("O campo Razão Social deve ser preenchido.");
			}

		}
		
		if (cpfCnpj == null || cpfCnpj.equals("")) {
			if (titular.isPf()) {
				mensagens.add("O campo cpf deve ser preenchido.");
			}
			
			if (titular.isPj()) {
				mensagens.add("O campo cnpj deve ser preenchido.");
			}
		}

		if (titular.isPf()) {

			if (rg == null || rg.equals("")) {
				mensagens.add("O campo rg deve ser preenchido.");
			}

			if (dtNasc == null) {
				mensagens.add("O campo Data de Nascimento deve ser preenchido.");
			}

			if (celular == null || celular.equals("")) {
				mensagens.add("O campo Celular deve ser preenchido.");
			}
			
		} else if (titular.isPj()) {
			
			if (ie == null || ie.equals("")) {
				mensagens.add("O campo Inscrição Estadual deve ser preenchido.");
			}

		}

		if (telefone == null || telefone.equals("")) {
			mensagens.add("O campo telefone deve ser preenchido.");
		}

		if (cep == null || cep.equals("")) {
			mensagens.add("O campo cep deve ser preenchido.");
		}

		if (rua == null || rua.equals("")) {
			mensagens.add("O campo rua deve ser preenchido.");
		}

		if (bairro == null || bairro.equals("")) {
			mensagens.add("O campo bairro deve ser preenchido.");
		}

		if (uf == null || uf.equals("")) {
			mensagens.add("O campo uf deve ser preenchido.");
		}

		if (cidade == null || cidade.equals("")) {
			mensagens.add("O campo cidade deve ser preenchido.");
		}

		if (email == null || email.equals("")) {
			mensagens.add("O campo E-mail deve ser preenchido.");
		}

		if (senha == null || senha.equals("")) {
			mensagens.add("O campo Senha deve ser preenchido");
		}

		if (mensagens.size() > 0) {
			retorno = false;
			return retorno;
		}

		mensagens.add("Titular " + nome + " salvo com sucesso!");
		return retorno;
	}
	
	public boolean existeTitular(Titular titular) {
		List<Titular> titulares = titularDao.findAll();

		for (Titular t : titulares) {
			if (t.getCpfCnpj().equals(titular.getCpfCnpj())) {
				mensagens.add("Titular " + t.getNome() + " já possui cadastro!");
				return true;
			}
		}

		return false;
	}

}