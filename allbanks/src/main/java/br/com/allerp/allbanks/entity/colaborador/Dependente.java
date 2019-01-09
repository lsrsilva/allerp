package br.com.allerp.allbanks.entity.colaborador;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.allerp.allbanks.entity.enums.Dependencias;
import br.com.allerp.allbanks.entity.pessoa.PessoaFisica;

@Entity
@Table
public class Dependente extends PessoaFisica {

	private static final long serialVersionUID = 6996876411278286265L;

	@ManyToOne
	@JoinColumn(name = "func_codigo", referencedColumnName = "codigo")
	private Funcionario funcionario;
	
	@Enumerated(EnumType.STRING)
	private Dependencias dependencia;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Dependencias getDependencia() {
		return dependencia;
	}

	public void setDependencia(Dependencias dependencia) {
		this.dependencia = dependencia;
	}

}
