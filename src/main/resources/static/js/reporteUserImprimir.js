// async function generarPDF() {
//     const { jsPDF } = window.jspdf;
//     const pdf = new jsPDF();

//     // Datos de encabezado
//     const usuario = "Kenneth Garrido";
//     const fechaImpresion = new Date().toLocaleString();
//     const titulo = "REPORTE DE USUARIOS";

//     // Dibuja fondo del encabezado
//     pdf.setFillColor(230, 230, 230);
//     pdf.rect(10, 10, 190, 30, 'F');

//     // Cargar logo real desde imagen (reemplaza esta ruta si tienes otra)
//     const logoUrl = "/images/logo.png"; // tu imagen local en la misma carpeta que el HTML
//     const logoImg = await loadImage(logoUrl);

//     const canvasLogo = document.createElement("canvas");
//     canvasLogo.width = logoImg.width;
//     canvasLogo.height = logoImg.height;

//     const ctx = canvasLogo.getContext("2d");
//     ctx.drawImage(logoImg, 0, 0);

//     const logoBase64 = canvasLogo.toDataURL("image/png");
//     pdf.addImage(logoBase64, "PNG", 15, 12, 25, 25); // x, y, ancho, alto

//     // Texto del encabezado
//     pdf.setFontSize(10);
//     pdf.setFont(undefined, "normal");
//     pdf.text(`Fecha de impresión: ${fechaImpresion}`, 60, 18);
//     pdf.text(`Usuario: ${usuario}`, 60, 24);

//     pdf.setFontSize(14);
//     pdf.setFont(undefined, "bold");
//     pdf.text(titulo, 105, 35, null, null, 'center');

//     // Posición inicial para el resto del contenido
//     let y = 50;

//     // Captura del gráfico y tabla resumen
//     const contenedorGrafico = document.getElementById("contenedor");
//     const canvasGrafico = await html2canvas(contenedorGrafico, { scale: 2 });
//     const imgGrafico = canvasGrafico.toDataURL("image/png");
//     const imgProps = pdf.getImageProperties(imgGrafico);
//     const pdfWidth = 190;
//     const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;

//     pdf.addImage(imgGrafico, "PNG", 10, y, pdfWidth, pdfHeight);
//     y += pdfHeight + 10;

//     // Captura de la tabla detallada
//     const tablaDetalle = document.querySelector(".contenedor-tabla-report");
//     const canvasTabla = await html2canvas(tablaDetalle, { scale: 2 });
//     const imgTabla = canvasTabla.toDataURL("image/png");
//     const imgProps2 = pdf.getImageProperties(imgTabla);
//     const tablaHeight = (imgProps2.height * pdfWidth) / imgProps2.width;

//     // Salto de página si no entra
//     if (y + tablaHeight > 280) {
//         pdf.addPage();
//         y = 10;
//     }

//     pdf.addImage(imgTabla, "PNG", 10, y, pdfWidth, tablaHeight);

//     // Descargar
//     pdf.save("reporte_usuarios.pdf");
// }

// //  Función auxiliar para cargar imagen desde ruta local o URL externa
// function loadImage(url) {
//     return new Promise((resolve) => {
//         const img = new Image();
//         img.crossOrigin = "anonymous"; // Por si es una URL externa
//         img.onload = () => resolve(img);
//         img.src = url;
//     });
// }


async function generarPDF() {
    const { jsPDF } = window.jspdf;
    const pdf = new jsPDF();

    // Datos de encabezado
    const usuario = document.getElementById('username').value || "";
    const fechaImpresion = new Date().toLocaleString();
    const titulo = "REPORTE DE USUARIOS";

    // Dibuja fondo del encabezado
    pdf.setFillColor(230, 230, 230);
    pdf.rect(10, 10, 190, 30, 'F');

    // Cargar logo real
    const logoUrl = "/images/logo.png";
    const logoImg = await loadImage(logoUrl);

    const canvasLogo = document.createElement("canvas");
    canvasLogo.width = logoImg.width;
    canvasLogo.height = logoImg.height;
    const ctx = canvasLogo.getContext("2d");
    ctx.drawImage(logoImg, 0, 0);
    const logoBase64 = canvasLogo.toDataURL("image/png");
    pdf.addImage(logoBase64, "PNG", 15, 12, 25, 25);

    // Texto del encabezado
    pdf.setFontSize(10);
    pdf.setFont(undefined, "normal");
    pdf.text(`Fecha de impresión: ${fechaImpresion}`, 60, 18);
    pdf.text(`Responsable: ${usuario}`, 60, 24);

    pdf.setFontSize(14);
    pdf.setFont(undefined, "bold");
    pdf.text(titulo, 105, 35, null, null, 'center');

    let y = 50;

    // Captura del gráfico
    const contenedorGrafico = document.getElementById("contenedor");
    const canvasGrafico = await html2canvas(contenedorGrafico, { scale: 2 });
    const imgGrafico = canvasGrafico.toDataURL("image/png");
    const imgProps = pdf.getImageProperties(imgGrafico);
    const pdfWidth = 190;
    const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;

    pdf.addImage(imgGrafico, "PNG", 10, y, pdfWidth, pdfHeight);
    y += pdfHeight + 10;

    // Captura de la tabla detallada como imagen
    const tablaDetalle = document.querySelector(".contenedor-tabla-report");
    const canvasTabla = await html2canvas(tablaDetalle, { scale: 2 });
    const imgTabla = canvasTabla.toDataURL("image/png");
    const imgProps2 = pdf.getImageProperties(imgTabla);
    const imgTablaAncho = 190;
    const imgTablaAlto = (imgProps2.height * imgTablaAncho) / imgProps2.width;

    const pageHeight = 297; // Altura de A4 en mm
    const marginTop = 10;
    const availablePageHeight = pageHeight - marginTop * 2;
    let remainingHeight = imgTablaAlto;
    let position = 0;

    // Crear un canvas temporal para recortes de la tabla
    const originalCanvas = canvasTabla;
    const fullHeight = originalCanvas.height;
    const pagePixelHeight = (availablePageHeight * originalCanvas.height) / imgTablaAlto;

    while (remainingHeight > 0) {
        const tempCanvas = document.createElement("canvas");
        const sliceHeight = Math.min(pagePixelHeight, fullHeight - position);
        tempCanvas.width = originalCanvas.width;
        tempCanvas.height = sliceHeight;

        const ctx = tempCanvas.getContext("2d");
        ctx.drawImage(originalCanvas, 0, position, originalCanvas.width, sliceHeight, 0, 0, originalCanvas.width, sliceHeight);

        const imgSlice = tempCanvas.toDataURL("image/png");
        const sliceHeightMM = (sliceHeight * imgTablaAncho) / originalCanvas.width;

        if (position !== 0) {
            pdf.addPage();
            y = marginTop;
        }

        pdf.addImage(imgSlice, "PNG", 10, y, imgTablaAncho, sliceHeightMM);

        position += sliceHeight;
        remainingHeight -= sliceHeightMM;
    }

    pdf.save("reporte_usuarios.pdf");
}

// Función auxiliar
function loadImage(url) {
    return new Promise((resolve) => {
        const img = new Image();
        img.crossOrigin = "anonymous";
        img.onload = () => resolve(img);
        img.src = url;
    });
}
