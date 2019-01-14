package br.com.allerp.allbanks.entity.conta;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import br.com.allerp.allbanks.entity.GenericEntity;
import br.com.allerp.allbanks.entity.pessoa.PessoaFisica;
import br.com.allerp.allbanks.entity.pessoa.PessoaJuridica;

@Entity
@Table(name = "TITULAR")
public class Titular extends GenericEntity {

	private static final long serialVersionUID = -8605819128606661027L;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tit_ct", joinColumns = @JoinColumn(name = "tit_cod"), inverseJoinColumns = @JoinColumn(name = "ct_cod"))
	@ForeignKey(name = "FK_TIT_COD", inverseName = "FK_CT_COD")
	private List<Conta> conta;

	@OneToOne(mappedBy = "titular")
	private PessoaFisica pf;

	@OneToOne(mappedBy = "titular")
	private PessoaJuridica pj;

	public PessoaFisica getPf() {
		return pf;
	}

	public void setPf(PessoaFisica pf) {
		this.pf = pf;
	}
	
	public PessoaJuridica getPj() {
		return pj;
	}
	
	public void setPj(PessoaJuridica pj) {
		this.pj = pj;
	}

}
