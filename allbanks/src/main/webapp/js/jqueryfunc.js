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
	$(".dinheiro").maskMoney({thousands:'.', decimal:','});
	
	/*$("#cpfcnpj").keydown(function(){
	    try {
	    	$("#cpfcnpj").unmask();
	    } catch (e) {}
	    
	    var tamanho = $("#cpfcnpj").val().length;
		
	    if(tamanho < 11){
	        $("#cpfcnpj").mask("999.999.999-99");
	    } else if(tamanho >= 11){
	        $("#cpfcnpj").mask("99.999.999/9999-99");
	    }
	    
	    // ajustando foco
	    var elem = this;
	    setTimeout(function(){
	    	// mudo a posição do seletor
	    	elem.selectionStart = elem.selectionEnd = 10000;
	    }, 0);
	    // reaplico o valor para mudar o foco
	    var currentValue = $(this).val();
	    $(this).val('');
	    $(this).val(currentValue);
	});
	*/
});