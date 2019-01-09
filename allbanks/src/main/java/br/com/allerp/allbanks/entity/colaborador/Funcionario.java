package br.com.allerp.allbanks.entity.colaborador;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.allerp.allbanks.entity.pessoa.PessoaFisica;

@Entity
@Table
public class Funcionario extends PessoaFisica {

	private static final long serialVersionUID = -7294170664540633593L;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "func_dep", joinColumns = @JoinColumn(name = "func_cod"), inverseJoinColumns = @JoinColumn(name = "dep_cod"))
	private List<Dependente> dependente;

	@ManyToOne
	@JoinColumn(name = "dpto_cod", referencedColumnName = "codigo")
	private Departamento departamento;

	@Column(nullable = false, precision = 7, scale = 2)
	private Float salario;

	@Column(nullable = false, length = 20)
	private String formacao;

	public List<Dependente> getDependente() {
		return dependente;
	}

	public void setDependente(List<Dependente> dependente) {
		this.dependente = dependente;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Float getSalario() {
		return salario;
	}

	public void setSalario(Float salario) {
		this.salario = salario;
	}

	public String getFormacao() {
		return formacao;
	}

	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}

}
