package br.com.allerp.allbanks.entity.colaborador;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.allerp.allbanks.entity.enums.Dependencias;
import br.com.allerp.allbanks.entity.pessoa.PessoaFisica;

public class Dependente extends PessoaFisica {

	private static final long serialVersionUID = 6996876411278286265L;

	@ManyToOne
	@JoinColumn(name = "func_codigo")
	private Funcionario funcionario;
	
	@Enumerated(EnumType.STRING)
	private Dependencias dependencia;
	
	

}
