// Se a página já estiver carregada ele executa os scripts
$(document).ready(function() {

	$(".somenteNumero").bind("keypress", function(e) {
		var key = (window.event) ? event.keyCode : e.which;
		if ((key > 47 && key < 58 || key == 13 || key == 44)) {
			return true;
		} else {
			return (key == 8 || key == 0) ? true : false;
		}
	});

	$(".cpf").mask("999.999.999-99");
	$(".cnpj").mask("99.999.999/9999-99");
	$(".CEP").mask("99.999-999");
	$(".data").mask("99/99/99");
	$(".telefone").mask("(999) 9999-9999");
	$(".celular").mask("(999) 99999-9999");
	$(".dinheiro").maskMoney({
		prefix : 'R$ ',
		allowNegative : true,
		thousands : '.',
		decimal : ',',
		affixesStay : false
	});

	/*
	 * $("#cpfcnpj").keydown(function(){ try { $("#cpfcnpj").unmask(); } catch
	 * (e) {}
	 * 
	 * var tamanho = $("#cpfcnpj").val().length;
	 * 
	 * if(tamanho < 11){ $("#cpfcnpj").mask("999.999.999-99"); } else if(tamanho >=
	 * 11){ $("#cpfcnpj").mask("99.999.999/9999-99"); } // ajustando foco var
	 * elem = this; setTimeout(function(){ // mudo a posição do seletor
	 * elem.selectionStart = elem.selectionEnd = 10000; }, 0); // reaplico o
	 * valor para mudar o foco var currentValue = $(this).val();
	 * $(this).val(''); $(this).val(currentValue); });
	 */
});

function somenteNumero(clazz) {
	$("." + clazz).bind("keypress", function(e) {
		var key = (window.event) ? event.keyCode : e.which;
		if ((key > 47 && key < 58 || key == 13 || key == 44)) {
			return true;
		} else {
			return (key == 8 || key == 0) ? true : false;
		}
	});
}

function mostraNotificações(componentId, qtdMensagens) {
	timeout = 100

	setTimeout("$('div#" + componentId + "').fadeTo('normal', 1)", timeout);

	timeout += 2000;

	var classes = new Array("feedback-ERROR", "feedback-WARNING",
			"feedback-INFO");

	if ($('div#' + componentId + ' #messages #message').hasClass(
			"feedback-ERROR")
			|| $('div#' + componentId + ' #messages #message').hasClass(
					"feedback-WARNING")
			|| $('div#' + componentId + ' #messages #message').hasClass(
					"feedback-INFO")) {
		$('div#' + componentId).fadeTo('normal');
	} else {
		setTimeout("$('div#" + componentId + "').fadeTo('normal', 0)", timeout);
	}

	$('#some').click(function() {
		$(this).fadeTo('normal', 0);
		$(this).hide();
	});

}