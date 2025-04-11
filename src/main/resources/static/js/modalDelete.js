let cerrar = document.querySelectorAll(".button-cancelar")[0];
let VentDelete = document.querySelectorAll(".ventana_modal--delete")[0];
let modalContDele = document.querySelectorAll(".container_modal--delete")[0];



cerrar.addEventListener("click", function (e) {
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


/* */


