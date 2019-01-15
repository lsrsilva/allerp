// Se a página já estiver carregada ele executa os scripts
$(document).ready(function() {
	$(".somenteNumero").bind("keypress", function(e) {
		var key = (window.event) ? event.keyCode : e.which;
		if ((key > 47 && key < 58 || key == 13)) {
			return true;
		} else {
			return (key == 8 || key == 0) ? true : false;
		}
	});

	$(".cpf").mask("999.999.999-99");
	$("input.cnpj").mask("99.999.999/9999-99");
	$("input.data").mask("99/99/99");
	$("input.telefone").mask("(999) 9999-9999");
	$("input.celular").mask("(999) 99999-9999");
});

function mudaClasse(id) {
	var i;
	var x = document.getElementsByClassName("teste");
	for (i = 0; i < x.length; i++) {
		x[i].style.display = "none";
	}
	document.getElementById(aba).style.display = "block";
}