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

import org.hibernate.annotations.ForeignKey;

import br.com.allerp.allbanks.entity.pessoa.PessoaFisica;

@Entity
@Table(name = "FUNCIONARIO")
@ForeignKey(name = "FK_FUNC_PF")
public class Funcionario extends PessoaFisica {

	private static final long serialVersionUID = -7294170664540633593L;

	@OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
	private List<Dependente> dependente;

	@ManyToOne
	@JoinColumn(name = "dpto_cod", referencedColumnName = "codigo")
	@ForeignKey(name = "FK_FUNC_DPTO")
	private Departamento departamento;

	@Column(nullable = false, precision = 7, scale = 2)
	private Float salario;

	@Column(nullable = false, length = 20)
	private String formacao;
	
	@Column(nullable = false, length = 20)
	private String funcao;

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

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

}
