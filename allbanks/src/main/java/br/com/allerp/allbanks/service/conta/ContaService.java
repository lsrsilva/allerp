package br.com.allerp.allbanks.service.conta;

import java.math.BigDecimal;
import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;

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

	private BigDecimal saldo;

	public void setContaDao(ContaDao contaDao) {
		super.setDao(contaDao);
		this.contaDao = contaDao;
	}

	public List<Conta> search(Integer codAg, Integer numConta, Status status, Contas tipoConta) {
		search = new Search(Conta.class);
		search.addFetch("agencia");

		filter = Filter.or(Filter.equal("agencia.codAg", codAg), Filter.equal("numConta", numConta),
				Filter.equal("status", status), Filter.equal("tipoConta", tipoConta));

		search.addFilter(filter);

		return contaDao.search(search);
	}

	public void deposita(Conta conta, Double valDep) throws FeedbackException {
		saldo = contaDao.consultaSaldo(conta.getNumConta());
		if (valDep <= 0) {
			throw new FeedbackException("Valor informado para depósito deve ser maior que 0.");
		} else if (valDep > LIM_DEPO.doubleValue()) {
			throw new FeedbackException(
					"Valor informado para depósito deve ser menor que " + LIM_DEPO.doubleValue() + ".");
		} else {
			saldo = saldo.add(new BigDecimal(valDep));
			conta.setSaldo(saldo);
			contaDao.merge(conta);
		}
	}

	public void saque(Conta conta, Double valSaque) throws FeedbackException {
		saldo = contaDao.consultaSaldo(conta.getNumConta());

		if (valSaque <= 0) {
			throw new FeedbackException("Valor informado para saque deve ser maior que 0.");
		} else if (valSaque > LIM_SAQUE.doubleValue()) {
			throw new FeedbackException(
					"Valor informado excede o limite por saque de R$ " + LIM_SAQUE.doubleValue() + ".");
		} else if (saldo == null || saldo == BigDecimal.ZERO || saldo.doubleValue() < valSaque) {
			throw new FeedbackException("Saldo insuficiente. Saldo: R$ " + saldo.doubleValue());
		} else {
			saldo = saldo.subtract(new BigDecimal(valSaque));
			conta.setSaldo(saldo);
			saveOrUpdate(conta);
		}

	}

	public void transfere(Conta conta, Conta ctDest, Double valTransf) throws FeedbackException {
		if (valTransf <= 0) {
			throw new FeedbackException("Valor informado para transferência deve ser maior que 0.");
		} else if (valTransf > LIM_DOC.doubleValue()) {
			throw new FeedbackException(
					"Valor informado para transferência deve ser menor que R$" + LIM_DOC.doubleValue() + ".");
		} else if (saldo == null || saldo == BigDecimal.ZERO || saldo.doubleValue() < valTransf) {
			throw new FeedbackException("Saldo insuficiente. Saldo: R$ " + saldo.doubleValue());
		} else {
			saque(conta, valTransf);
			deposita(ctDest, valTransf);
		}

	}
	
	public BigDecimal getCtSaldo(Conta conta) {
		saldo = contaDao.consultaSaldo(conta.getNumConta());
		return saldo;
	}
}