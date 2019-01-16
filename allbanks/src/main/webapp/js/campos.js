function alteraClasse(id){
	var i;
	var elementos = document.getElementsByClassName("troca");
	var element = document.getElementById(id);
	
	for(i = 0; i < elementos.length; i++){
		if(i == id){
			break;
		}
		elementos[i].className = "tab-pane fade";
	}
	
	element.className += " show";
	
}