package br.com.allerp.allbanks.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.classmate.GenericType;

import br.com.allerp.allbanks.entity.conta.Banco;
import br.com.allerp.allbanks.entity.pessoa.Pessoa;

@Entity
@Table
public class Endereco extends GenericEntity {

	private static final long serialVersionUID = -1484560609846703833L;
	
	@Column(nullable = false, length = 60)
	private String bairro;

	@Column(nullable = false, length = 60)
	private String rua;

	@Column(length = 150)
	private String complemento;

	@Column(nullable = false, length = 8)
	private String cep;

	@Column(length = 50)
	private String cidade;

	@Column(length = 2)
	private String uf;

	@Column(length = 30)
	private String pais;

	@Column(length = 11)
	private Integer num;
	
	@OneToOne(mappedBy = "endereco")
	private Pessoa<?> pessoa;
	
	@OneToOne(mappedBy = "endereco")
	private Banco banco;

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
