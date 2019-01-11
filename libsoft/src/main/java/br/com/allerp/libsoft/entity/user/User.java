package br.com.allerp.libsoft.entity.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "user")
// Esta anotação identifica que a estratégia de herança será feita através de chave estrangeira
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

	private static final long serialVersionUID = -3417915934417211134L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String psw;

	@Column(nullable = false)
	private String telefone;

	@Column(nullable = false, unique = true)
	private String userAccess;

	@Column
	private String fotoPerfil;
	
	//Endereço
	@Column(nullable = false)
	private String bairro;

	@Column(nullable = false)
	private String rua;

	@Column
	private String complemento;

	@Column(nullable = false)
	private String cep;

	@Column
	private String cidade;

	@Column
	private String uf;

	@Column
	private String pais;

	@Column
	private Integer num;
	
	@Column
	private String perfil;
	
	//-----------------------
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getUserAccess() {
		return userAccess;
	}

	public void setUserAccess(String userAccess) {
		this.userAccess = userAccess;
	}

	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

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

	public String getPerfil() {
		return perfil;
	}

	protected void setPerfil(String perfil) {
		this.perfil = perfil;
	}
}
