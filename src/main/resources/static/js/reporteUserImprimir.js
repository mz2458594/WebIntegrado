async function generarPDF() {
    const { jsPDF } = window.jspdf;
    const pdf = new jsPDF();

    // Datos de encabezado
    const usuario = "Kenneth Garrido";
    const fechaImpresion = new Date().toLocaleString();
    const titulo = "REPORTE DE USUARIOS";

    // Dibuja fondo del encabezado
    pdf.setFillColor(230, 230, 230);
    pdf.rect(10, 10, 190, 30, 'F');

    // Cargar logo real desde imagen (reemplaza esta ruta si tienes otra)
    const logoUrl = "/images/logo.png"; // tu imagen local en la misma carpeta que el HTML
    const logoImg = await loadImage(logoUrl);

    const canvasLogo = document.createElement("canvas");
    canvasLogo.width = logoImg.width;
    canvasLogo.height = logoImg.height;

    const ctx = canvasLogo.getContext("2d");
    ctx.drawImage(logoImg, 0, 0);

    const logoBase64 = canvasLogo.toDataURL("image/png");
    pdf.addImage(logoBase64, "PNG", 15, 12, 25, 25); // x, y, ancho, alto

    // Texto del encabezado
    pdf.setFontSize(10);
    pdf.setFont(undefined, "normal");
    pdf.text(`Fecha de impresión: ${fechaImpresion}`, 60, 18);
    pdf.text(`Usuario: ${usuario}`, 60, 24);

    pdf.setFontSize(14);
    pdf.setFont(undefined, "bold");
    pdf.text(titulo, 105, 35, null, null, 'center');

    // Posición inicial para el resto del contenido
    let y = 50;

    // Captura del gráfico y tabla resumen
    const contenedorGrafico = document.getElementById("contenedor");
    const canvasGrafico = await html2canvas(contenedorGrafico, { scale: 2 });
    const imgGrafico = canvasGrafico.toDataURL("image/png");
    const imgProps = pdf.getImageProperties(imgGrafico);
    const pdfWidth = 190;
    const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;

    pdf.addImage(imgGrafico, "PNG", 10, y, pdfWidth, pdfHeight);
    y += pdfHeight + 10;

    // Captura de la tabla detallada
    const tablaDetalle = document.querySelector(".contenedor-tabla-report");
    const canvasTabla = await html2canvas(tablaDetalle, { scale: 2 });
    const imgTabla = canvasTabla.toDataURL("image/png");
    const imgProps2 = pdf.getImageProperties(imgTabla);
    const tablaHeight = (imgProps2.height * pdfWidth) / imgProps2.width;

    // Salto de página si no entra
    if (y + tablaHeight > 280) {
        pdf.addPage();
        y = 10;
    }

    pdf.addImage(imgTabla, "PNG", 10, y, pdfWidth, tablaHeight);

    // Descargar
    pdf.save("reporte_usuarios.pdf");
}

// 🔁 Función auxiliar para cargar imagen desde ruta local o URL externa
function loadImage(url) {
    return new Promise((resolve) => {
        const img = new Image();
        img.crossOrigin = "anonymous"; // Por si es una URL externa
        img.onload = () => resolve(img);
        img.src = url;
    });
}
