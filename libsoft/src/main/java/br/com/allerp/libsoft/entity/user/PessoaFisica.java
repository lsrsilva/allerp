package br.com.allerp.libsoft.entity.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames= {"cpf"}))
// Identifica qual o campo da tabela PessoaFisica que será feita a junção (name) e qual a coluna que está referenciando (referencedColumnName)
@PrimaryKeyJoinColumn(referencedColumnName = "id_user")
@ForeignKey(name = "FK_ID_USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class PessoaFisica extends User {

	private static final long serialVersionUID = -6496916224739090281L;

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

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNasc() {
		return dtNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dtNasc = dataNasc;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

}
