package br.com.allerp.allbanks.entity.colaborador;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import br.com.allerp.allbanks.entity.pessoa.PessoaFisica;

public class Funcionario extends PessoaFisica {

	private static final long serialVersionUID = -7294170664540633593L;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "func_dep", joinColumns = @JoinColumn(name = "func_cod"), inverseJoinColumns = @JoinColumn(name = "dep_cod"))
	private List<Dependente> dependente;
	
	@Column(nullable = false, precision = 7, scale = 2)
	private Float salario;
	
	@Column(nullable = false)
	private String formacao;
}
