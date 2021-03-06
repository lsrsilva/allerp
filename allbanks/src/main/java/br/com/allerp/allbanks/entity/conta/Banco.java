package br.com.allerp.allbanks.entity.conta;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import br.com.allerp.allbanks.entity.GenericEntity;

@Entity
@Table(name = "BANCO")
public class Banco extends GenericEntity {

	private static final long serialVersionUID = -4966934917325692377L;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "banco_contas", joinColumns = @JoinColumn(name = "banco_cod"), inverseJoinColumns = @JoinColumn(name = "ct_cod"))
	@ForeignKey(name = "FK_BC_COD", inverseName = "FK_CONTA")
	private List<Conta> conta;

	@OneToMany(mappedBy = "banco", cascade=CascadeType.REMOVE)
	private List<Agencia> agencia;

	@Column(name = "cod_compensacao", nullable = false, unique = true, length = 5)
	private String codCompensacao;

	@Column(nullable = false, unique = true, length = 65)
	private String nome;

	public List<Conta> getConta() {
		return conta;
	}

	public void setConta(List<Conta> conta) {
		this.conta = conta;
	}

	public String getCodCompensacao() {
		if(codCompensacao == null) {
			return "";
		}
		return codCompensacao;
	}

	public void setCodCompensacao(String codCompensacao) {
		this.codCompensacao = codCompensacao;
	}

	public String getNome() {
		if(nome == null) {
			return "";
		}
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
