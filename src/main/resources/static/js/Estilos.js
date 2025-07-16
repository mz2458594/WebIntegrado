//QUe LOS INPUTS DE CODIGO Y DOCUMENTO SOLO PERMITAN NUMEROS

function validarNumero(input) {
    input.value = input.value.replace(/[^0-9.]/g, '');
    input.value = input.value.replace(/(\..*?)\..*/g, '$1');
}
function activarInput() {
    const input = document.getElementById("efectivo");
    input.disabled = !input.disabled;

    if (!input.disabled) {
        input.focus();
    }
    document.getElementById("efectivo").value = "";
}



//PARA TIPO DE DOCUMENTO
const select = document.getElementById("documentoTipo");
const inputContainer = document.getElementById("input-doc-container");
const inputNumero = document.getElementById("numeroDoc");

select.addEventListener("change", function () {
    if (select.value === "dni") {
        inputContainer.style.display = "none";
        
    } else if (select.value === "FACTURA") {
        inputNumero.placeholder = "Ingrese RUC";
        inputNumero.maxLength = 11;
        inputContainer.style.display = "inline";
        inputContainer.focus();
    } else {
        inputContainer.style.display = "none";
        inputNumero.value = "";
    }
});


//PARA LA ANULACION DE TRANSACCION
let cerrar = document.querySelectorAll(".cancelar-anul")[0];
let abrir = document.querySelectorAll(".btn-anular")[0];
let VentModal = document.querySelectorAll(".contenedor-modal-anular")[0];
let modalCont = document.querySelectorAll(".modal-container")[0];

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