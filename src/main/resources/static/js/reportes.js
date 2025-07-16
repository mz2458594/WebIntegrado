

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

    const usuario = document.getElementById('username').value || "";
    const fechaImpresion = new Date().toLocaleString();
    const titulo = "REPORTE DE PEDIDOS";

    // Encabezado con fondo
    doc.setFillColor(230, 230, 230);
    doc.rect(10, 10, 190, 30, 'F');

    // Cargar e insertar logo
    const logoUrl = "/images/logo.png";
    const logoImg = await loadImage(logoUrl);
    const canvasLogo = document.createElement("canvas");
    canvasLogo.width = logoImg.width;
    canvasLogo.height = logoImg.height;
    const ctxLogo = canvasLogo.getContext("2d");
    ctxLogo.drawImage(logoImg, 0, 0);
    const logoBase64 = canvasLogo.toDataURL("image/png");
    doc.addImage(logoBase64, "PNG", 15, 12, 25, 25);

    // Texto del encabezado
    doc.setFontSize(10);
    doc.setFont(undefined, "normal");
    doc.text(`Fecha de impresión: ${fechaImpresion}`, 60, 18);
    doc.text(`Responsable: ${usuario}`, 60, 24);
    doc.setFontSize(14);
    doc.setFont(undefined, "bold");
    doc.text(titulo, 105, 35, null, null, 'center');

    // Captura del gráfico
    const canvas = document.getElementById("graficoPedidos");
    const chartImage = canvas.toDataURL("image/png", 1.0);
    doc.addImage(chartImage, "PNG", 15, 45, 180, 60);

    // Resumen de estados
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

    // Captura la tabla y la divide en múltiples páginas si es necesario
    const tabla = document.querySelector(".tabla-filtro");
    const canvasTabla = await html2canvas(tabla, { scale: 2 }); // mejor calidad
    const imgData = canvasTabla.toDataURL("image/png");

    const imgProps = doc.getImageProperties(imgData);
    const pdfWidth = 180;
    const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;

    let tablaY = textoY + 10;
    const pageHeight = doc.internal.pageSize.height;

    // Si la tabla no entra en la página actual, pasa a una nueva
    if (tablaY + pdfHeight > pageHeight) {
        doc.addPage();
        tablaY = 20;
    }

    const fragmentHeight = pageHeight - tablaY - 10;
    const fragmentCount = Math.ceil(pdfHeight / fragmentHeight);

    for (let i = 0; i < fragmentCount; i++) {
        const sY = i * fragmentHeight * (canvasTabla.height / pdfHeight);
        const fragmentCanvas = document.createElement("canvas");
        fragmentCanvas.width = canvasTabla.width;
        fragmentCanvas.height = fragmentHeight * (canvasTabla.height / pdfHeight);
        const ctx = fragmentCanvas.getContext("2d");
        ctx.drawImage(canvasTabla, 0, sY, canvasTabla.width, fragmentCanvas.height, 0, 0, canvasTabla.width, fragmentCanvas.height);
        const fragmentData = fragmentCanvas.toDataURL("image/png");
        doc.addImage(fragmentData, "PNG", 15, tablaY, pdfWidth, fragmentHeight);

        if (i < fragmentCount - 1) {
            doc.addPage();
            tablaY = 20;
        }
    }

    doc.save("reporte_pedidos.pdf");
}

// Función auxiliar para cargar la imagen del logo
function loadImage(url) {
    return new Promise((resolve) => {
        const img = new Image();
        img.crossOrigin = "anonymous";
        img.onload = () => resolve(img);
        img.src = url;
    });
}

// async function generarPDF() {
//     const { jsPDF } = window.jspdf;
//     const doc = new jsPDF();

//     const usuario = "Kenneth Garrido";
//     const fechaImpresion = new Date().toLocaleString();
//     const titulo = "REPORTE DE PEDIDOS";

//     // Encabezado
//     doc.setFillColor(230, 230, 230);
//     doc.rect(10, 10, 190, 30, 'F');
//     doc.setFontSize(16);
//     doc.text("LOGO", 15, 20); // Cambiar por imagen si deseas
//     doc.setFontSize(10);
//     doc.text(`Fecha de impresión: ${fechaImpresion}`, 60, 18);
//     doc.text(`Usuario: ${usuario}`, 60, 24);
//     doc.setFontSize(14);
//     doc.setFont(undefined, "bold");
//     doc.text(titulo, 105, 35, null, null, 'center');
    

//     // Captura del gráfico
//     const canvas = document.getElementById("graficoPedidos");
//     const chartImage = canvas.toDataURL("image/png", 1.0);
//     doc.addImage(chartImage, "PNG", 15, 45, 180, 60);

//     // Resumen de estados
//     const estados = ["PENDIENTE", "CONFIRMADO", "EN_CAMINO", "ENTREGADO", "CANCELADO"];
//     const resumen = estados.map(est => {
//         const count = pedidos.filter(p => p.estado === est).length;
//         return `${est}: ${count}`;
//     });

//     doc.setFontSize(12);
//     doc.setFont(undefined, "normal");
//     let textoY = 110;
//     doc.text("Resumen de estados:", 105, textoY, null, null, 'center');
//     resumen.forEach(linea => {
//         textoY += 6;
//         doc.text(linea, 105, textoY, null, null, 'center');
//     });

//     // Captura la tabla y la divide en múltiples páginas si es necesario
//     const tabla = document.querySelector(".tabla-filtro");
//     const canvasTabla = await html2canvas(tabla, { scale: 2 }); // mejor calidad
//     const imgData = canvasTabla.toDataURL("image/png");

//     const imgProps = doc.getImageProperties(imgData);
//     const pdfWidth = 180;
//     const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;

//     let tablaY = textoY + 10;
//     const pageHeight = doc.internal.pageSize.height;

//     // Si la tabla no entra en la página actual, pasa a una nueva
//     if (tablaY + pdfHeight > pageHeight) {
//         doc.addPage();
//         tablaY = 20;
//     }

//     const fragmentHeight = pageHeight - tablaY - 10;
//     const fragmentCount = Math.ceil(pdfHeight / fragmentHeight);

//     for (let i = 0; i < fragmentCount; i++) {
//         const sY = i * fragmentHeight * (canvasTabla.height / pdfHeight);
//         const fragmentCanvas = document.createElement("canvas");
//         fragmentCanvas.width = canvasTabla.width;
//         fragmentCanvas.height = fragmentHeight * (canvasTabla.height / pdfHeight);
//         const ctx = fragmentCanvas.getContext("2d");
//         ctx.drawImage(canvasTabla, 0, sY, canvasTabla.width, fragmentCanvas.height, 0, 0, canvasTabla.width, fragmentCanvas.height);
//         const fragmentData = fragmentCanvas.toDataURL("image/png");
//         doc.addImage(fragmentData, "PNG", 15, tablaY, pdfWidth, fragmentHeight);

//         if (i < fragmentCount - 1) {
//             doc.addPage();
//             tablaY = 20;
//         }
//     }

//     doc.save("reporte_pedidos.pdf");
// }