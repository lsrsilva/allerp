function openNav() {
	var mySidebar = document.getElementById("mySidebar");
	var logo = document.getElementById("logo");
	var divLogo = document.getElementById("divLogo");
	var divIcons = document.getElementById("divIcons");
	var cadForm = document.getElementById("cadForm");
	var listUser = document.getElementById("listUser");

	// esconde e o else mostra
	if (mySidebar.style.width == "250px") {
		mySidebar.style.width = "60px";

		logo.src = "./logo/logo-coruja-symbol.png";
		logo.style.width = "55px";
		logo.style.heigth = "auto";

		divLogo.style.paddingLeft = "0";
		divLogo.style.height = "55px";

		divIcons.style.paddingLeft = "0";

		if (cadForm != null) {
			cadForm.style.marginLeft = "0";
			cadForm.style.marginRight = "0";
		} else if (listUser != null) {
			listUser.style.marginLeft = "0";
			listUser.style.marginRight = "0";
		}
		document.getElementById("content").style.marginLeft = "60px";

	} else {
		mySidebar.style.width = "250px";

		logo.src = "./logo/logo-coruja-hr.png";
		logo.style.width = "130px";
		logo.style.heigth = "0";

		divLogo.style.paddingLeft = "25%";
		divLogo.style.paddingTop = "0";
		divLogo.style.height = "55px";

		if (cadForm != null) {
			cadForm.style.marginLeft = "5px";
			cadForm.style.marginRight = "5px";
		} else if (listUser != null) {
			listUser.style.marginLeft = "5px";
			listUser.style.marginRight = "5px";
		}

		document.getElementById("content").style.marginLeft = "250px";
	}

}

function changeIconDp(){
	var dtNascIcon = document.getElementById("dtNascIcon");
	dtNascIcon.src = "https://pngtree.com/free-icon";
}