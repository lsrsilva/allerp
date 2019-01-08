package br.com.allerp.allbanks.entity.pessoa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class PessoaFisica extends Pessoa {

	private static final long serialVersionUID = 1479524506413698898L;

	@Column
	private String celular;

	@Column(nullable = false)
	private String cpf;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dtNasc;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String rg;

	@Column(nullable = true)
	private String sexo;
	
}
