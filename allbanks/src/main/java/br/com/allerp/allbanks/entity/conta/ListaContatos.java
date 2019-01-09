package br.com.allerp.allbanks.entity.conta;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import br.com.allerp.allbanks.entity.GenericEntity;

@Entity
@Table
public class ListaContatos extends GenericEntity {

	private static final long serialVersionUID = 7292792173768982050L;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "listacontatos", joinColumns=@JoinColumn(name = "list_ct_id"), inverseJoinColumns = @JoinColumn(name = "ct_cod"))
	private List<Conta> ctContato;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "listacontatos", joinColumns=@JoinColumn(name = "list_ct_id"), inverseJoinColumns = @JoinColumn(name = "banco_cod"))
	private List<Banco> banco;

	@Column(nullable = false, length = 200)
	private String nomeContato;

	@Column(nullable = false, unique = true, length = 20)
	private String cpf_cnpj;

	public List<Conta> getCtContato() {
		return ctContato;
	}

	public void setCtContato(List<Conta> ctContato) {
		this.ctContato = ctContato;
	}

	public List<Banco> getBanco() {
		return banco;
	}

	public void setBanco(List<Banco> banco) {
		this.banco = banco;
	}

	public String getNomeContato() {
		return nomeContato;
	}

	public void setNomeContato(String nomeContato) {
		this.nomeContato = nomeContato;
	}

	public String getCpf_cnpj() {
		return cpf_cnpj;
	}

	public void setCpf_cnpj(String cpf_cnpj) {
		this.cpf_cnpj = cpf_cnpj;
	}

}
