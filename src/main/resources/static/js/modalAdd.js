let cerrar = document.querySelectorAll(".btn-cancelar")[0];
let abrir = document.querySelectorAll(".addition")[0];
let VentModal = document.querySelectorAll(".ventana__modal")[0];
let modalCont = document.querySelectorAll(".modal__container--add")[0];

var camposCategoria = document.getElementById("camposCategoria");
abrir.addEventListener("click", function (e) {
    e.preventDefault();
    modalCont.style.opacity = "1";
    modalCont.style.visibility = "visible";
    VentModal.classList.toggle("ventana-close");
});

cerrar.addEventListener("click", function (e) {
    VentModal.classList.toggle("ventana-close");
    setTimeout(function () {
        modalCont.style.opacity = "0";
        modalCont.style.visibility = "hidden";
    }, 600);

    camposCategoria.innerHTML = '';
    camposCategoria.style.display = 'none';
});

window.addEventListener("click", function (e) {
    console.log(e.target);
    if (e.target == modalCont) {
        VentModal.classList.toggle("ventana-close");
        setTimeout(function () {
            modalCont.style.opacity = "0";
            modalCont.style.visibility = "hidden";
            camposCategoria.innerHTML = '';
            camposCategoria.style.display = 'none';
            document.getElementById('formulario_produc').reset();
        }, 600);
    }
});



/*  */



let cerrardel = document.querySelectorAll(".button-cancelar")[0];
let VentDelete = document.querySelectorAll(".ventana_modal--delete")[0];
let modalContDele = document.querySelectorAll(".container_modal--delete")[0];



cerrardel.addEventListener("click", function (e) {
    VentDelete.classList.toggle("ventana-close-delete");
    setTimeout(function () {
        modalContDele.style.opacity = "0";
        modalContDele.style.visibility = "hidden";
    }, 600);
});

window.addEventListener("click", function (e) {
    console.log(e.target);
    if (e.target == modalContDele) {
        VentDelete.classList.toggle("ventana-close-delete");
        setTimeout(function () {
            modalContDele.style.opacity = "0";
            modalContDele.style.visibility = "hidden";
        }, 600);
    }
});


/*  */

let cerrarEdit = document.querySelector(".btn-cancelar-edit");
let modalContEdit = document.querySelector(".container-modal--edit");
let VentEdit = document.querySelector(".ventana_modal--edit");


cerrarEdit.addEventListener("click", function (e) {
    VentEdit.classList.toggle("ventana-close-edit");
    setTimeout(function () {
        modalContEdit.style.opacity = "0";
        modalContEdit.style.visibility = "hidden";
    }, 600);
});

window.addEventListener("click", function (e) {
    console.log(e.target);
    if (e.target == modalContEdit) {
        VentEdit.classList.toggle("ventana-close-edit");
        setTimeout(function () {
            modalContEdit.style.opacity = "0";
            modalContEdit.style.visibility = "hidden";
        }, 600);
    }
});

let abrirOpciones = document.querySelectorAll(".action_checkbox");
document.querySelectorAll(".container_action").forEach(actionContainer => {
    const label = actionContainer.querySelector(".action_checkbox");
    const optionsContainer = actionContainer.querySelector(".cont_opciones");

    // Toggle display for options container on label click
    label.addEventListener("click", () => {
        const isVisible = optionsContainer.style.display === "flex";
        // Hide any other open options
        document.querySelectorAll(".cont_opciones").forEach(container => {
            container.style.display = "none";
        });
        // Toggle current options container
        optionsContainer.style.display = isVisible ? "none" : "flex";
    });
});

