const estaFil = document.getElementById("estadoFiltro");
const tableRows = document.querySelectorAll("#productList");

function filterEstado() {
    const query = estaFil.value.toLowerCase();

    tableRows.forEach(row => {
        // Filtrar basándonos en las celdas relevantes (e.g., nombre del producto)
        // const productId = row.querySelector("td:nth-child(1)").textContent.toLowerCase();
        // const productFecha = row.querySelector("td:nth-child(2)").textContent.toLowerCase();
        // const productUsuario = row.querySelector("td:nth-child(3)").textContent.toLowerCase();
        const estado = row.querySelector("td:nth-child(6)").textContent.toLowerCase();

        if (estado.includes(query)) {
            row.style.display = ""; // Mostrar fila
        } else {
            row.style.display = "none"; // Ocultar fila
            tableRows.innerHTML = '<tr colspan="6">NO SE ENCUENTRAN REGISTROS</tr>'
        }

    });
}

estaFil.addEventListener("input", filterEstado);
estaFil.addEventListener('change', function () {
    const selectedOption = estaFil.options[estaFil.selectedIndex];
    const backgroundColor = selectedOption.style.backgroundColor;
    estaFil.style.backgroundColor = backgroundColor;
});