document.addEventListener("DOMContentLoaded", () => {
    const inputs = document.querySelectorAll("input[required]");

    inputs.forEach(input => {
        // Crear un elemento <small> si no existe ya
        let mensaje = input.nextElementSibling;
        if (!mensaje || !mensaje.classList.contains("mensaje-error")) {
            mensaje = document.createElement("small");
            mensaje.classList.add("mensaje-error");
            input.parentNode.insertBefore(mensaje, input.nextSibling);
        }

        // Validar en tiempo real
        input.addEventListener("input", () => {
            if (input.value.trim() === "") {
                mensaje.textContent = "Este campo es obligatorio.";
                mensaje.style.display = "block";
                mensaje.style.color = "red";

            } else {
                mensaje.textContent = "";
                mensaje.style.display = "none";
            }
        });

        // Validar al salir del campo (por si no escribe nada)
        input.addEventListener("blur", () => {
            if (input.value.trim() === "") {
                mensaje.textContent = "Este campo es obligatorio.";
                mensaje.style.display = "block";
                mensaje.style.color = "red";
            }
        });
    });
});


const limi = document.querySelectorAll(".text");

limi.forEach(input => {
    input.addEventListener("input", function () {
        this.value = this.value.replace(/([A-Za-zÁÉÍÓÚáéíóúÑñ])\1{2,}/g, '$1$1');
        this.value = this.value.replace(/(\d)\1{3,}/g, '$1$1');
    });
});

const inputs = document.querySelectorAll(".soloText");

inputs.forEach(input => {
    input.addEventListener("input", function () {
        this.value = this.value.replace(/[^A-Za-zÁÉÍÓÚáéíóúÑñ\s]/g, "");

        this.value = this.value.replace(/([A-Za-zÁÉÍÓÚáéíóúÑñ])\1{2,}/g, '$1$1');
    });
});


const numeros = document.querySelectorAll(".soloNum");

numeros.forEach(input => {
    input.addEventListener("input", function () {
        // 1. Solo permitir dígitos del 0 al 9
        this.value = this.value.replace(/[^0-9]/g, "");

        // 2. Evitar repeticiones del mismo número más de 2 veces
        this.value = this.value.replace(/(\d)\1{3,}/g, '$1$1');
    });
});
