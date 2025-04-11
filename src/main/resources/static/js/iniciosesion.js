const inputs = document.querySelectorAll(".input");


function agregarfocus(){
	let parent = this.parentNode.parentNode;
	parent.classList.add("focus");
}

function quitarfocus(){
	let parent = this.parentNode.parentNode;
	if(this.value == ""){
		parent.classList.remove("focus");
	}
}


inputs.forEach(input => {
	input.addEventListener("focus", agregarfocus);
	input.addEventListener("blur", quitarfocus);
});