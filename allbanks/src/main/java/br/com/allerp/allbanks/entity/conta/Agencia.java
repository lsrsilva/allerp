package br.com.allerp.allbanks.entity.conta;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import br.com.allerp.allbanks.entity.Endereco;
import br.com.allerp.allbanks.entity.GenericEntity;

@Entity
@Table
public class Agencia extends GenericEntity {

	private static final long serialVersionUID = 8177936448973490472L;

	@Column(nullable = false, unique = true, length = 65)
	private Integer codAg;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_banco", referencedColumnName = "cod_compensacao")
	@ForeignKey(name = "FK_BC_AG")
	private Banco banco;
	/*
	 * @OneToOne(cascade = CascadeType.ALL)
	 * 
	 * @ForeignKey(name = "FK_AG_END") private Endereco endereco;
	 * 
	 * public Endereco getEndereco() { return endereco; }
	 * 
	 * public void setEndereco(Endereco endereco) { this.endereco = endereco; }
	 */

	public Integer getCodAg() {
		return codAg;
	}

	public void setCodAg(Integer codAg) {
		this.codAg = codAg;
	}
	
	public Banco getBanco() {
		return banco;
	}

}
