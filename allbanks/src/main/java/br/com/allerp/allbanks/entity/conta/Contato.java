package br.com.allerp.allbanks.entity.conta;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import br.com.allerp.allbanks.entity.GenericEntity;

@Entity
@Table(name = "LISTA_CONTATOS")
public class Contato extends GenericEntity {

	private static final long serialVersionUID = 7292792173768982050L;

	@ManyToMany
	@JoinTable(name = "list_ct_bc", joinColumns = @JoinColumn(name = "list_ct_id"), inverseJoinColumns = @JoinColumn(name = "ct_cod"))
	@ForeignKey(name = "FK_LIST_CT", inverseName = "FK_CT_CONTATO")
	private List<Conta> ctContato;

	public List<Conta> getCtContato() {
		return ctContato;
	}

	public void setCtContato(List<Conta> ctContato) {
		this.ctContato = ctContato;
	}

}
