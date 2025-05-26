// Crear un objeto de fecha
const fecha = new Date();
// Obtener día, mes y año
const dia = fecha.getDate();
const mes = fecha.getMonth() + 1; // Los meses van de 0 a 11
const anio = fecha.getFullYear();
// Formatear la fecha (ejemplo: 21/05/2025)
const fechaFormateada = `${dia.toString().padStart(2, '0')}/${mes.toString().padStart(2, '0')}/${anio}`;
// Mostrarla en el HTML
document.getElementById("fecha").textContent = fechaFormateada;


function actualizarHora() {
    const ahora = new Date();
    const horas = ahora.getHours().toString().padStart(2, '0');
    const minutos = ahora.getMinutes().toString().padStart(2, '0');
    const segundos = ahora.getSeconds().toString().padStart(2, '0');

    const horaFormateada = `${horas}:${minutos}:${segundos}`;
    document.getElementById("hora").textContent = horaFormateada;
}

// Actualizar hora inmediatamente al cargar
actualizarHora();

// Actualizar cada segundo (1000 milisegundos)
setInterval(actualizarHora, 1000);