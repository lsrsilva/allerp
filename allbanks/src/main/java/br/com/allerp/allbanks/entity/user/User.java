package br.com.allerp.allbanks.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.allerp.allbanks.entity.GenericEntity;
import br.com.allerp.allbanks.entity.enums.Perfis;

@Entity
@Table
// Esta anotação identifica que a estratégia de herança será feita através de chave estrangeira
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends GenericEntity {

	private static final long serialVersionUID = -3417915934417211134L;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String psw;

	@Column(nullable = false, unique = true)
	private String userAccess;

	@Enumerated(EnumType.ORDINAL)
	private Perfis perfil;

	// -----------------------

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

	public String getUserAccess() {
		return userAccess;
	}

	public void setUserAccess(String userAccess) {
		this.userAccess = userAccess;
	}

	public Perfis getPerfil() {
		return perfil;
	}

	protected void setPerfil(Perfis perfil) {
		this.perfil = perfil;
	}
}
