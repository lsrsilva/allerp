package br.com.allerp.allbanks.entity.colaborador;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.allerp.allbanks.entity.GenericEntity;

@Entity
@Table
public class Departamento extends GenericEntity {

	private static final long serialVersionUID = 7100555135714225709L;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "dpto_funcs", joinColumns = @JoinColumn(name = "dpto_cod"), inverseJoinColumns = @JoinColumn(name = "func_cod"))
	private List<Funcionario> funcionarios;

	@OneToOne(mappedBy = "dpto")
	private Gerente gerente;

	@Column(nullable = false, length = 15)
	private String descricao;

	@Column(nullable = false)
	private Integer qtdFuncDpto;

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public Gerente getGerente() {
		return gerente;
	}

	public void setGerente(Gerente gerente) {
		this.gerente = gerente;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQtdFuncDpto() {
		return qtdFuncDpto;
	}

	public void setQtdFuncDpto(Integer qtdFuncDpto) {
		this.qtdFuncDpto = qtdFuncDpto;
	}
}
