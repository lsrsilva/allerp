package TESTE.composicao;

public class Cliente {

	private PJ pj;

	private PF pf;

	public PJ getPj() {
		return pj;
	}

	public void setPj(PJ pj) {
		this.pj = pj;
	}

	public PF getPf() {
		return pf;
	}

	public void setPf(PF pf) {
		this.pf = pf;
	}

	public static void main(String[] args) {
		PF pf = new PF();
		PJ pj = new PJ();
		pf.setNome("Pessoa Física");
		pf.setCpf("cpf");
		pj.setNome("Pessoa Juridica");
		pj.setCnpj("cnpj");
		Cliente c = new Cliente();
		c.setPf(pf);
		c.setPj(pj);
		
		System.out.println(c.getPf().getCpf());
	}

}
