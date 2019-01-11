package br.com.allerp.libsoft.entity;

import java.io.Serializable;
import java.util.Calendar;

import br.com.allerp.libsoft.entity.livro.Exemplar;
import br.com.allerp.libsoft.entity.user.Reservista;

public class Reserva implements Serializable {

	private static final long serialVersionUID = -1061627680642427250L;

	public static final int MAX_LIV = 3;
	public static final float MULTA_DIA = 0.50F;

	private Calendar dtDev;
	private Calendar dtReserva;
	private Reservista reservista;
	private Exemplar exemplar;

	public Reserva() {
		Exemplar.qtd -= 1;
	}

	public Calendar getDtDev() {
		return dtDev;
	}

	public void setDtDev(Calendar dtDev) {
		this.dtDev = dtDev;
	}

	public Calendar getDtReserva() {
		return dtReserva;
	}

	public void setDtReserva(Calendar dtReserva) {
		this.dtReserva = dtReserva;
	}

	public Reservista getReservista() {
		return reservista;
	}

	public void setReservista(Reservista reservista) {
		this.reservista = reservista;
	}

	public Exemplar getExemplar() {
		return exemplar;
	}

	public void setExemplar(Exemplar exemplar) {
		this.exemplar = exemplar;
	}

}
