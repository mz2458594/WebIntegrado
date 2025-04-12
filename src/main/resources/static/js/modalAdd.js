let cerrar = document.querySelectorAll(".btn-cancelar")[0];
let abrir = document.querySelectorAll(".addition")[0];
let VentModal = document.querySelectorAll(".ventana__modal")[0];
let modalCont = document.querySelectorAll(".modal__container--add")[0];

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
});

window.addEventListener("click", function (e) {
    console.log(e.target);
    if (e.target == modalCont) {
        VentModal.classList.toggle("ventana-close");
        setTimeout(function () {
            modalCont.style.opacity = "0";
            modalCont.style.visibility = "hidden";
        }, 600);
        document.getElementById('formulario').reset();
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

let cerrarEdit = document.querySelectorAll(".btn-cancelar-edit")[0];
let VentEdit = document.querySelectorAll(".ventana_modal--edit")[0];
let modalContEdit = document.querySelectorAll(".container-modal--edit")[0];

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

const boton2 = document.querySelectorAll(".editproduc");
boton2.forEach(boton => {
    boton.addEventListener('click', function () {
        // // Encontramos la fila asociada al botón
        // const fila = this.closest('tr');

        // // Recogemos los valores de las celdas de la fila
        // const valores = Array.from(fila.querySelectorAll('td')).map(td => td.textContent);

        // // Mostramos los valores recogidos
        // document.getElementById("id_act").value = valores[0];
        // document.getElementById("nombre_act").value = valores[1];
        // document.getElementById("categoria_act").value = valores[2];
        // document.getElementById("proveedor_act").value = valores[3];
        // document.getElementById("descripcion_act").value = valores[4];
        // document.getElementById("precio_act").value = valores[5];
        // document.getElementById("stock_act").value = valores[6];
        modalContEdit.style.opacity = "1";
        modalContEdit.style.visibility = "visible";
        VentEdit.classList.toggle("ventana-close-edit");
    });
});


const boton3 = document.querySelectorAll(".deleteProduc");
boton3.forEach(boton => {
    boton.addEventListener('click', function () {
        // Encontramos la fila asociada al botón
        const fila = this.closest('tr');

        // Recogemos los valores de las celdas de la fila
        const valores = Array.from(fila.querySelectorAll('td')).map(td => td.textContent);

        // Mostramos los valores recogidos
        document.getElementById("id_eliminar").value = valores[0];
        modalContDele.style.opacity = "1";
        modalContDele.style.visibility = "visible";
        VentDelete.classList.toggle("ventana-close-delete");
    });
});

const boton4 = document.querySelectorAll(".button-confir");
boton4.forEach(boton => {
    boton.addEventListener('click', function () {
        document.getElementById('eliminar_prod').submit();
    });
});