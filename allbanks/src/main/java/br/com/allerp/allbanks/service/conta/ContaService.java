package br.com.allerp.allbanks.service.conta;

import java.math.BigDecimal;
import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import br.com.allerp.allbanks.dao.conta.ContaDao;
import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.enums.Contas;
import br.com.allerp.allbanks.entity.enums.Status;
import br.com.allerp.allbanks.exceptions.FeedbackException;
import br.com.allerp.allbanks.service.GenericService;

public class ContaService extends GenericService<Conta> {

	@SpringBean(name = "contaDao")
	private ContaDao contaDao;

	@SpringBean(name = "titularService")
	private TitularService titularService;

	private Search search;
	private Filter filter;

	/**
	 * Limite máximo por saque.
	 */
	private final BigDecimal LIM_SAQUE = new BigDecimal(700.00);

	/**
	 * Limite máximo de saque diário.
	 */
	private final BigDecimal LIM_SAQUE_D = new BigDecimal(1500.00);

	/**
	 * Limite máximo para depósito.
	 */
	private final BigDecimal LIM_DEPO = new BigDecimal(2000.00);

	/**
	 * Limite máximo para transferência
	 */
	private final BigDecimal LIM_DOC = new BigDecimal(4999.99);

	/**
	 * Taxa para transferência entre bancos diferentes
	 */
	private final Double TAXA_TRANSF = 10D;

	private BigDecimal saldo;

	public void setContaDao(ContaDao contaDao) {
		super.setDao(contaDao);
		this.contaDao = contaDao;
	}

	public void setTitularService(TitularService titularService) {
		this.titularService = titularService;
	}

	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
	public List<Conta> search(Integer codAg, Integer numConta, Status status, Contas tipoConta) {
		search = new Search(Conta.class);
		search.addFetch("agencia");

		filter = Filter.or(Filter.equal("agencia.codAg", codAg), Filter.equal("numConta", numConta),
				Filter.equal("status", status), Filter.equal("tipoConta", tipoConta));

		search.addFilter(filter);

		return contaDao.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deposita(Conta conta, Double valDep) throws FeedbackException {
		saldo = contaDao.consultaSaldo(conta.getNumConta());
		if (valDep == null || valDep <= 0) {
			throw new FeedbackException("Valor informado para depósito deve ser maior que 0.");
		} else if (valDep > LIM_DEPO.doubleValue()) {
			throw new FeedbackException(
					"Valor informado para depósito deve ser menor que " + LIM_DEPO.doubleValue() + ".");
		} else {
			saldo = saldo.add(new BigDecimal(valDep));
			conta.setSaldo(saldo);
			merge(conta);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void saque(Conta conta, Double valSaque) throws FeedbackException {
		saldo = contaDao.consultaSaldo(conta.getNumConta());

		if (valSaque == null || valSaque <= 0) {
			throw new FeedbackException("Valor informado para saque deve ser maior que 0.");
		} else if (valSaque > LIM_SAQUE.doubleValue()) {
			throw new FeedbackException(
					"Valor informado excede o limite por saque de R$ " + LIM_SAQUE.doubleValue() + ".");
		} else if (saldo == null || saldo == BigDecimal.ZERO || saldo.doubleValue() < valSaque) {
			throw new FeedbackException("Saldo insuficiente. Saldo: R$ " + saldo.doubleValue());
		} else {
			saldo = saldo.subtract(new BigDecimal(valSaque));
			conta.setSaldo(saldo);
			merge(conta);
		}

	}

	/**
	 * 
	 * @param conta     Conta origem
	 * @param ctBen     Conta do beneficiário
	 * @param valTransf Valor que será transferido para o destinatário
	 * @throws FeedbackException
	 */
	public void transfere(Conta conta, Conta ctBen, Double valTransf) throws FeedbackException {
		saldo = contaDao.consultaSaldo(conta.getNumConta());

		if (conta.equals(ctBen)) {
			throw new FeedbackException("Não é possível transferir para a mesma conta.");
		} else if (valTransf == null || valTransf <= 0) {
			throw new FeedbackException("Valor informado para transferência deve ser maior que 0.");
		} else if (valTransf > LIM_DOC.doubleValue()) {
			throw new FeedbackException(
					"Valor informado para transferência deve ser menor que R$" + LIM_DOC.doubleValue() + ".");
		} else if (saldo == null || saldo == BigDecimal.ZERO || saldo.doubleValue() < valTransf) {
			throw new FeedbackException("Saldo insuficiente para transferência. Saldo: R$ " + saldo.doubleValue());
		} else if (!ctBen.getBanco().getCodCompensacao().equals(conta.getBanco().getCodCompensacao())) {
			saque(conta, valTransf + TAXA_TRANSF);
			deposita(ctBen, valTransf);
		} else {
			saque(conta, valTransf);
			deposita(ctBen, valTransf);
		}

	}

	/**
	 * Verifica se uma conta existe para um titular.
	 * 
	 * @param numConta
	 * @param cpfCnpjTit
	 * @return A conta caso exista.
	 * @throws FeedbackException
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Conta verifExisteConta(Integer numConta, String cpfCnpjTit, Integer codAg) {

		Conta conta;
		search = new Search(Conta.class);

		filter = Filter.and(Filter.equal("numConta", numConta), Filter.ilike("titular.cpfCnpj", cpfCnpjTit),
				Filter.equal("agencia.codAg", codAg));
		search.addFilter(filter);
		conta = contaDao.searchUnique(search);

		if (conta == null) {
			mensagens.add("Conta para transferência inexistente.");
		}

		return conta;
	}

	/**
	 * Verifica se uma conta existe para um titular.
	 * 
	 * @param nomeTitular
	 * @param numConta
	 * @return A conta caso exista.
	 * @throws FeedbackException
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Conta verifExisteConta(String nomeTitular, Integer numConta) {

		Conta conta;
		search = new Search(Conta.class);

		filter = Filter.and(Filter.ilike("titular.nome", nomeTitular), Filter.equal("numConta", numConta));
		search.addFilter(filter);
		conta = contaDao.searchUnique(search);

		if (conta == null) {
			mensagens.add("Conta inexistente.");
		}

		return conta;
	}

	public boolean camposSaoValidos(Conta conta) {

		boolean validTit = titularService.camposSaoValidos(conta.getTitular());

		if (existeConta(conta)) {
			mensagens.add("Já existe uma conta com o código " + conta.getNumConta() + "!");
			return false;
		}

		try {
			if (conta.getAgencia() == null && conta.getBanco() == null && conta.getNumConta() == null
					&& conta.getTipoConta() == null && validTit == false) {
				mensagens.add("Favor preencher os campos obrigatórios.");
				return false;
			}
		} catch (NullPointerException ne) {
			mensagens.add("Favor preencher os campos obrigatórios.");
			return false;
		}

		if (conta.getAgencia() == null) {
			mensagens.add("Favor selecionar uma agência.");
		}

		if (conta.getBanco() == null) {
			mensagens.add("Favor selecionar um Banco.");
		}

		if (conta.getTipoConta() == null) {
			mensagens.add("Favor selecione o tipo da conta.");
		}

		if (conta.getNumConta() == null || conta.getNumConta().toString().equals("")) {
			mensagens.add("Campo Número da Conta deve ser preenchido.");
		} else if (conta.getNumConta() == 0) {
			mensagens.add("Número para a conta inválido.");
		}

		if (!validTit) {
			mensagens.addAll(titularService.getMensagens());
			titularService.getMensagens().clear();
		}

		if (mensagens.size() > 0) {
			return false;
		}

		mensagens.add("Conta " + conta.getNumConta() + " salva com sucesso!");
		return true;
	}

	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE, propagation = Propagation.SUPPORTS)
	public boolean existeConta(Conta conta) {

		Conta ct = contaDao.consultaConta(conta);

		if (ct != null) {
			if (ct.getCodigo() == conta.getCodigo()) {
				return false;
			} else if (ct.getNumConta().equals(conta.getNumConta())) {
				mensagens.add("Conta " + ct.getNumConta() + " já cadastrada!");
				return true;
			}
		}

		return false;
	}

}