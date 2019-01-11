$(document).ready(function() {
	$(".somenteNumero").bind("keypress", function(e) {
		var key = (window.event) ? event.keyCode : e.which;
		if ((key > 47 && key < 58 || key == 13)) {
			return true;
		} else {
			return (key == 8 || key == 0) ? true : false;
		}
	});
});