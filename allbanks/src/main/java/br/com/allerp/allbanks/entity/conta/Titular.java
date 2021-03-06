package br.com.allerp.allbanks.entity.conta;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

import br.com.allerp.allbanks.entity.Endereco;
import br.com.allerp.allbanks.entity.pessoa.Pessoa;
import br.com.allerp.allbanks.entity.user.User;

@Entity
@Table(name = "TITULAR")
@ForeignKey(name = "FK_PES_COD")
@PrimaryKeyJoinColumn(name = "tit_cod", referencedColumnName = "codigo")
public class Titular extends Pessoa {

	private static final long serialVersionUID = -8605819128606661027L;

	@OneToMany(mappedBy = "titular", cascade = CascadeType.REMOVE)
	@Column(nullable = false)
	private List<Conta> contas;

	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "id_user", nullable = false, unique = true)
	@ForeignKey(name = "FK_ID_USER")
	private User user;

	@Column(length = 200)
	private String ie;

	@Column(length = 16)
	private String celular;

	@Column(length = 14, unique = true)
	private String cpfCnpj;

	@Column
	@Temporal(TemporalType.DATE)
	private Date dtNasc;

	@Column(nullable = false, length = 200)
	private String nome;

	@Column(length = 20)
	private String rg;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "end_codigo")
	@ForeignKey(name = "FK_PESS_END")
	private Endereco endereco;

	@Column(nullable = false)
	private String tipoPessoa;

	@OneToMany(mappedBy = "titular", cascade = CascadeType.REMOVE)
	private List<Contato> contato;

	@Override
	public boolean equals(Object titular) {
		if (this == titular) {
			return true;
		}

		if (!(titular instanceof Titular)) {
			return false;
		}

		Titular titular2 = (Titular) titular;
		return this.getCodigo() == titular2.getCodigo();
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public boolean isPf() {
		if (tipoPessoa.equals("Pessoa Física")) {
			return true;
		}
		return false;
	}

	public boolean isPj() {
		if (tipoPessoa.equals("Pessoa Jurídica")) {
			return true;
		}
		return false;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getNome() {
		if (nome == null) {
			return "";
		}
		return nome;
	}

	public void setNome(String razaoSocial) {
		this.nome = razaoSocial;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Date getDtNasc() {
		return dtNasc;
	}

	public void setDtNasc(Date dtNasc) {
		this.dtNasc = dtNasc;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void addContas(Conta conta) {
		this.contas.add(conta);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Contato> getContato() {
		if (contato == null) {
			contato.add(new Contato());
			return contato;
		}
		return contato;
	}

	public void setContato(List<Contato> contato) {
		this.contato = contato;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
