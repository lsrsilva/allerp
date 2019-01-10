package TESTE.polimorfismo;

public class Cliente {
	
	private Pessoa pessoa;
	
	public void adicionaCliente(Pessoa p) { //Aqui entra um dos conceitos de polimorfismo... estamos pedindo pra receber uma pessoa aqui. Uma PessoaFisica é uma Pessoa? E uma Pessoa Juridica é uma Pessoa? No nosso caso sim!
		pessoa = p;
		System.out.println(p.getNome() + " adicionado com sucesso na lista de Clientes!");
	}	
	public Pessoa getListaDeClientes() {
		return pessoa;
	}	
	
	
	public static void main(String[] args) {
		//Pessoa p = new Pessoa(); //não instancia, pois a classe Pessoa é abstrata
				PF pf = new PF();
				PJ pj = new PJ();
				pf.setNome("Pessoa Física");
				pf.setCpf("cpf");
				pj.setNome("Pessoa Juridica");
				pj.setCnpj("cnpj");
				Cliente c = new Cliente();
				c.adicionaCliente(pf);
				c.adicionaCliente(pj);
				
				pf = (PF) c.getListaDeClientes();
				
				System.out.println(pf.getCpf());
	}
	
}
