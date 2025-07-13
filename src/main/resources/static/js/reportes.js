function elegirTipoPedido() {
    const tipoPedid = document.getElementById("tipoPedido").value;
    const userCli = document.getElementById("user-cliente");
    const f1 = document.getElementById("fil1");
    const cont = document.getElementById("cont-restoFiltros");
    const f2 = document.getElementById("fil2");

    switch (tipoPedid) {
        case "Ecommerce":
            f1.style.display = "none";
            cont.style.display = "grid";
            cont.style.gridTemplateColumns = "1fr";
            f2.style.gridTemplateColumns = "1fr 1fr";
            userCli.textContent = "Cliente";
            break;
        case "Inventario":
            f1.style.display = "grid";
            cont.style.display = "grid";
            cont.style.gridTemplateColumns = "1fr 1fr";
            f2.style.gridTemplateColumns = "1fr";
            userCli.textContent = "Usuario";
            break;
    }
}

function elegirTipoPedido() {
    const tipoPedid = document.getElementById("tipoPedido").value;
    const userCli = document.getElementById("user-cliente");
    const f1 = document.getElementById("fil1");
    const cont = document.getElementById("cont-restoFiltros");
    const f2 = document.getElementById("fil2");

    switch (tipoPedid) {
        case "Ecommerce":
            f1.style.display = "none";
            cont.style.display = "grid";
            cont.style.gridTemplateColumns = "1fr";
            f2.style.gridTemplateColumns = "1fr 1fr";
            userCli.textContent = "Cliente";
            break;
        case "Inventario":
            f1.style.display = "grid";
            cont.style.display = "grid";
            cont.style.gridTemplateColumns = "1fr 1fr";
            f2.style.gridTemplateColumns = "1fr";
            userCli.textContent = "Usuario";
            break;
    }
}

async function generarPDF() {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    // Datos de encabezado
    const usuario = "Kenneth Garrido";
    const fechaImpresion = new Date().toLocaleString();
    const titulo = "REPORTE DE PEDIDOS";

    // Encabezado
    doc.setFillColor(230, 230, 230);
    doc.rect(10, 10, 190, 30, 'F');

    doc.setFontSize(16);
    doc.text("LOGO", 15, 20); // Puedes reemplazar por imagen
    doc.setFontSize(10);
    doc.text(`Fecha de impresión: ${fechaImpresion}`, 60, 18);
    doc.text(`Usuario: ${usuario}`, 60, 24);

    doc.setFontSize(14);
    doc.setFont(undefined, "bold");
    doc.text(titulo, 105, 35, null, null, 'center');

    // Captura el gráfico
    const canvas = document.getElementById("graficoPedidos");
    const chartImage = canvas.toDataURL("image/png", 1.0);
    doc.addImage(chartImage, "PNG", 15, 45, 180, 60);

    // Texto resumen debajo del gráfico
    const estados = ["PENDIENTE", "CONFIRMADO", "EN_CAMINO", "ENTREGADO", "CANCELADO"];

    const resumen = estados.map(est => {
        const count = pedidos.filter(p => p.estado === est).length;
        return `${est}: ${count}`;
    });

    doc.setFontSize(12);
    doc.setFont(undefined, "normal");
    let textoY = 110;
    doc.text("Resumen de estados:", 105, textoY, null, null, 'center');
    resumen.forEach(linea => {
        textoY += 6;
        doc.text(linea, 105, textoY, null, null, 'center');
    });

    // Captura tabla como imagen
    const tabla = document.querySelector(".tabla-filtro");
    await html2canvas(tabla).then((canvasTabla) => {
        const imgData = canvasTabla.toDataURL("image/png");
        let tablaY = textoY + 10;

        doc.setFont(undefined, "bold");
        doc.text("Tabla de pedidos:", 15, tablaY);
        tablaY += 4;

        if (tablaY + 80 > doc.internal.pageSize.height) {
            doc.addPage();
            tablaY = 20;
        }

        doc.addImage(imgData, "PNG", 15, tablaY, 180, 0);
    });

    doc.save("reporte_pedidos.pdf");
}