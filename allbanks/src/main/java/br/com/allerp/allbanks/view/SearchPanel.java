package br.com.allerp.allbanks.view;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.allerp.allbanks.entity.colaborador.Funcionario;
import br.com.allerp.allbanks.entity.conta.Agencia;
import br.com.allerp.allbanks.entity.conta.Banco;
import br.com.allerp.allbanks.entity.conta.Conta;
import br.com.allerp.allbanks.entity.conta.Titular;
import br.com.allerp.allbanks.entity.enums.Perfis;
import br.com.allerp.allbanks.entity.user.User;
import br.com.allerp.allbanks.service.UserService;
import br.com.allerp.allbanks.service.colaborador.FuncionarioService;
import br.com.allerp.allbanks.service.conta.AgenciaService;
import br.com.allerp.allbanks.service.conta.BancoService;
import br.com.allerp.allbanks.service.conta.ContaService;
import br.com.allerp.allbanks.service.conta.TitularService;

public class SearchPanel extends Panel {

	private static final long serialVersionUID = 8676439514370170905L;
	
	// ---------------------------- SERVICES --------------------------------//

	@SpringBean(name = "agenciaService")
	private AgenciaService agenciaService;
	@SpringBean(name = "userService")
	private UserService userService;
	@SpringBean(name = "funcService")
	private FuncionarioService funcService;
	@SpringBean(name = "bancoService")
	private BancoService bancoService;
	@SpringBean(name = "contaService")
	private ContaService contaService;
	@SpringBean(name = "titularService")
	private TitularService titularService;

	// ----------------------------------------------------------------------//
	
	// ----------------------------- OUTROS ----------------------------------//
	
	private User user;
	private Funcionario func;
	private Banco banco;
	private Agencia agencia;
	private Conta conta;
	private Titular titular;
	
	private List<Perfis> perfis = Arrays.asList(Perfis.values());
	private List<User> users;
	private List<Funcionario> funcionarios;
	private List<Banco> bancos;
	private List<Agencia> agencias;
	private List<Conta> contas;
	private List<Titular> titulares;
	
	// ----------------------------------------------------------------------//
	
	public SearchPanel(String id) {
		super(id);
	}
	
	public void atualiza(AjaxRequestTarget target) {}

	public SearchPanel searchUser(List<User> ltUsers, String idPanel) {
		CompoundPropertyModel<User> formModel = new CompoundPropertyModel<User>(user);
		Form<User> form = new Form<User>("searchUser", formModel);
		
		final TextField<String> userAccess = Util.textField("userAccess");
		final DropDownChoice<Perfis> perfil = Util.dropDown("perfis", perfis);
		
		form.add(userAccess, perfil);
		
		users = ltUsers;
		form.add(new AjaxButton("search") {

			private static final long serialVersionUID = 3865918601254958016L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(userAccess != null && perfil != null || user.getUserAccess().equals("") || user.getPerfil().equals("")) {
					users = userService.search(user.getUserAccess(), user.getPerfil().toString());
				}
				if(userAccess == null && perfil == null) {
					users = userService.findAll();
				}
				atualiza(target);
			}

		});
		
		return new SearchPanel(idPanel);
	}
	
	public SearchPanel searchFunc(List<Funcionario> funcionarios) {
		CompoundPropertyModel<Funcionario> formModel = new CompoundPropertyModel<Funcionario>(func);
		Form<Funcionario> form = new Form<Funcionario>("serchFunc", formModel);
	}
	
	public SearchPanel searchBanco(List<Banco> bancos) {
		
	}
	
	public SearchPanel searchAg(List<Agencia> agencias) {
		
	}
	
	public SearchPanel searchCt(List<Conta> contas) {
		
	}
	
	public SearchPanel searchTit(List<Titular> titulares) {
		
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	
	public List<Banco> getBancos() {
		return bancos;
	}
	
	public List<Agencia> getAgencias() {
		return agencias;
	}
	
	public List<Conta> getContas() {
		return contas;
	}
	
	public List<Titular> getTitulares() {
		return titulares;
	}
	
}
