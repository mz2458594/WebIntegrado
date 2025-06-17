function mostrarCamposCategoria() {
        const categoria = document.getElementById('categoria').value;
        const camposCategoria = document.getElementById('camposCategoria');

        // Limpiar campos anteriores
        camposCategoria.innerHTML = '';
        camposCategoria.style.display = 'none';

        switch(categoria){
            case "Laptops":
                camposCategoria.style.display = 'block';
                camposCategoria.innerHTML = `
                <h3>Características de Laptop</h3>
                <div class="input__box">
                    <label for="procesador">Procesador</label>
                    <input type="text" id="procesador" name="procesador" required>
                </div>
                <div class="input__box">
                    <label for="tarjetaGrafica">Tarjeta grafica</label>
                    <input type="text" id="tarjetaGrafica" name="tarjetaGrafica" required>
                </div>
                <div class="input__box">
                    <label for="sistemaOperativo">Sistema Operativo</label>
                    <input type="text" id="sistemaOperativo" name="sistemaOperativo" required>
                </div>
                <div class="input__box">
                    <label for="tamañoPantalla">Tamaño de pantalla</label>
                    <input type="text" id="tamañoPantalla" name="tamañoPantalla" required>
                </div>
                <div class="input__box">
                    <label for="ram">Memoria RAM</label>
                    <input type="text" id="ram" name="ram" required>
                </div>
                <div class="input__box">
                    <label for="almacenamiento">Almacenamiento</label>
                    <input type="text" id="almacenamiento" name="almacenamiento" required>
                </div>
                `;
                break;
            case "Smartphones":
                camposCategoria.style.display = 'block';
                camposCategoria.innerHTML = `
                <h3>Características de Smartphones</h3>
                <div class="input__box">
                    <label for="tamañoPantalla">Tamaño de pantalla</label>
                    <input type="text" id="tamañoPantalla" name="tamañoPantalla" required>
                </div>
                <div class="input__box">
                    <label for="ram">Memoria RAM</label>
                    <input type="text" id="ram" name="ram" required>
                </div>
                <div class="input__box">
                    <label for="almacenamientoInterno">Almacenamiento interno</label>
                    <input type="text" id="almacenamientoInterno" name="almacenamientoInterno" required>
                </div>
                <div class="input__box">
                    <label for="resolucionCamara">Resolucion de camara</label>
                    <input type="text" id="resolucionCamara" name="resolucionCamara" required>
                </div>
                <div class="input__box">
                    <label for="capacidadBateria">Capacidad de bateria</label>
                    <input type="text" id="capacidadBateria" name="capacidadBateria" required>
                </div>
                <div class="input__box">
                    <label for="sistemaOperativo">Sistema operativo</label>
                    <input type="text" id="sistemaOperativo" name="sistemaOperativo" required>
                </div>
                `;
                break;
            case "Auriculares":
                camposCategoria.style.display = 'block';
                camposCategoria.innerHTML = `
                <h3>Características de Auriculares</h3>
                <div class="input__box">
                    <label for="tipo">Tipo</label>
                    <input type="text" id="tipo" name="tipo" required>
                </div>
                <div class="input__box">
                    <label for="duracion">Duracion</label>
                    <input type="text" id="duracion" name="duracion" required>
                </div>
                <div class="input__box">
                    <label for="cancelacionRuido">Cancelacion de ruido</label>
                    <input type="text" id="cancelacionRuido" name="cancelacionRuido" required>
                </div>
                <div class="input__box">
                    <label for="conector">Conector</label>
                    <input type="text" id="conector" name="conector" required>
                </div>
                `;
                break;
            case "Monitores":
                camposCategoria.style.display = 'block';
                camposCategoria.innerHTML = `
                <h3>Características de Monitores</h3>
                <div class="input__box">
                    <label for="tamañoPantalla">Tamaño de pantalla</label>
                    <input type="text" id="tamañoPantalla" name="tamañoPantalla" required>
                </div>
                <div class="input__box">
                    <label for="resolucion">Resolucion</label>
                    <input type="text" id="resolucion" name="resolucion" required>
                </div>
                <div class="input__box">
                    <label for="tipoPanel">Tipo de panel</label>
                    <input type="text" id="tipoPanel" name="tipoPanel" required>
                </div>
                <div class="input__box">
                    <label for="frecuenciaActualizacion">Frecuencia de actualizacion</label>
                    <input type="text" id="frecuenciaActualizacion" name="frecuenciaActualizacion" required>
                </div>
                <div class="input__box">
                    <label for="puertosEntrada">Puertos de entrada</label>
                    <input type="text" id="puertosEntrada" name="puertosEntrada" required>
                </div>
                `;
                break;
            case "Teclados":
                camposCategoria.style.display = 'block';
                camposCategoria.innerHTML = `
                <h3>Características de Teclados</h3>
                <div class="input__box">
                    <label for="tipo">Tipo</label>
                    <input type="text" id="tipo" name="tipo" required>
                </div>
                <div class="input__box">
                    <label for="conectividad">Conectividad</label>
                    <input type="text" id="conectividad" name="conectividad" required>
                </div>
                <div class="input__box">
                    <label for="distribucion">Distribucion</label>
                    <input type="text" id="distribucion" name="distribucion" required>
                </div>
                <div class="input__box">
                    <label for="retroiluminacion">Retroiluminacion</label>
                    <input type="text" id="retroiluminacion" name="retroiluminacion" required>
                </div>
                `;
                break;
            case "Mouses":
                camposCategoria.style.display = 'block';
                camposCategoria.innerHTML = `
                <h3>Características de Mouses</h3>
                <div class="input__box">
                    <label for="tipo">Tipo</label>
                    <input type="text" id="tipo" name="tipo" required>
                </div>
                <div class="input__box">
                    <label for="conectividad">Conectividad</label>
                    <input type="text" id="conectividad" name="conectividad" required>
                </div>
                <div class="input__box">
                    <label for="dpi">DPI</label>
                    <input type="text" id="dpi" name="dpi" required>
                </div>
                <div class="input__box">
                    <label for="cantidadBotones">Cantidad de botones</label>
                    <input type="text" id="cantidadBotones" name="cantidadBotones" required>
                </div>
                `;
                break;
            case "Smartwatches":
                camposCategoria.style.display = 'block';
                camposCategoria.innerHTML = `
                <h3>Características de SmartWatches</h3>
                <div class="input__box">
                    <label for="compatibilidad">Compatibilidad</label>
                    <input type="text" id="compatibilidad" name="compatibilidad" required>
                </div>
                <div class="input__box">
                    <label for="monitoreoSalud">Monitoreo de salud</label>
                    <input type="text" id="monitoreoSalud" name="monitoreoSalud" required>
                </div>
                <div class="input__box">
                    <label for="resistenciaAgua">Resistencia al agua</label>
                    <input type="text" id="dpi" name="dpi" required>
                </div>
                <div class="input__box">
                    <label for="duracion">Duracion</label>
                    <input type="text" id="duracion" name="duracion" required>
                </div>
                `;
                break;
            case "Tablets":
                camposCategoria.style.display = 'block';
                camposCategoria.innerHTML = `
                <h3>Características de Tablets</h3>
                <div class="input__box">
                    <label for="tamañoPantalla">Tamaño de pantalla</label>
                    <input type="text" id="tamañoPantalla" name="tamañoPantalla" required>
                </div>
                <div class="input__box">
                    <label for="ram">Memoria Ram</label>
                    <input type="text" id="ram" name="ram" required>
                </div>
                <div class="input__box">
                    <label for="almacenamientoInterno">Almacenamiento interno</label>
                    <input type="text" id="almacenamientoInterno" name="almacenamientoInterno" required>
                </div>
                <div class="input__box">
                    <label for="resolucionCamara">Resolucion de la camara</label>
                    <input type="text" id="resolucionCamara" name="resolucionCamara" required>
                </div>
                `;
                break;
            case "Cámaras":
                camposCategoria.style.display = 'block';
                camposCategoria.innerHTML = `
                <h3>Características de Camara</h3>
                <div class="input__box">
                    <label for="tipo">Tipo</label>
                    <input type="text" id="tipo" name="tipo" required>
                </div>
                <div class="input__box">
                    <label for="resolucion">Resolución</label>
                    <input type="text" id="resolucion" name="resolucion" required>
                </div>
                <div class="input__box">
                    <label for="zoomOptico">Zoom Optico</label>
                    <input type="text" id="zoomOptico" name="zoomOptico" required>
                </div>
                <div class="input__box">
                    <label for="estabilizacionImagen">Estabilización de imagen</label>
                    <input type="text" id="estabilizacionImagen" name="estabilizacionImagen" required>
                </div>
                `;
                break;
            case "Impresoras":
                camposCategoria.style.display = 'block';
                camposCategoria.innerHTML = `
                <h3>Características de Impresoras</h3>
                <div class="input__box">
                    <label for="tipo">Tipo</label>
                    <input type="text" id="tipo" name="tipo" required>
                </div>
                <div class="input__box">
                    <label for="funciones">Funciones</label>
                    <input type="text" id="funciones" name="funciones" required>
                </div>
                <div class="input__box">
                    <label for="conectividad">Conectividad</label>
                    <input type="text" id="conectividad" name="conectividad" required>
                </div>
                <div class="input__box">
                    <label for="velocidadImpresion">Velocidad de Impresion</label>
                    <input type="text" id="velocidadImpresion" name="velocidadImpresion" required>
                </div>
                <div class="input__box">
                    <label for="dobleCaraAutomatica">Doble cara automatica</label>
                    <input type="text" id="dobleCaraAutomatica" name="dobleCaraAutomatica" required>
                </div>
                `;
                break;
            case "":
                break;
        }

        // if (categoria === 'Laptops') {
        //     camposCategoria.style.display = 'block';
        //     camposCategoria.innerHTML = `
        //         <h3>Características de Laptop</h3>
        //         <div class="input__box"">
        //             <label for="procesador">Procesador</label>
        //             <input type="text" id="procesador" name="procesador" required>
        //         </div>
        //         <div class="input__box"">
        //             <label for="ram">Memoria RAM</label>
        //             <input type="text" id="ram" name="ram" required>
        //         </div>
        //         <div class="input__box"">
        //             <label for="almacenamiento">Almacenamiento</label>
        //             <input type="text" id="almacenamiento" name="almacenamiento" required>
        //         </div>
        //     `;
        // } 
        // else if (categoria === 'audifonos') {
        //     camposCategoria.style.display = 'block';
        //     camposCategoria.innerHTML = `
        //         <h3>Características de Audífonos</h3>
        //         <div class="input__box"">
        //             <label for="tipoConexion">Tipo de Conexión</label>
        //             <input type="text" id="tipoConexion" name="tipoConexion" required>
        //         </div>
        //         <div class="input__box"">
        //             <label for="inalambrico">¿Es inalámbrico? (Sí/No)</label>
        //             <input type="text" id="inalambrico" name="inalambrico" required>
        //         </div>
        //     `;
        // } else if (categoria === 'celular') {
        //     camposCategoria.style.display = 'block';
        //     camposCategoria.innerHTML = `
        //         <h3>Características de Celular</h3>
        //         <div class="input__box"">
        //             <label for="pantalla">Tamaño de Pantalla</label>
        //             <input type="text" id="pantalla" name="pantalla" required>
        //         </div>
        //         <div class="input__box"">
        //             <label for="bateria">Capacidad de Batería (mAh)</label>
        //             <input type="text" id="bateria" name="bateria" required>
        //         </div>
        //         <div class="input__box"">
        //             <label for="camara">Resolución de Cámara</label>
        //             <input type="text" id="camara" name="camara" required>
        //         </div>
        //     `;
        // }
    }

    // Ejemplo de cómo puedes manejar el submit
    // document.getElementById('productForm').addEventListener('submit', function(e) {
    //     e.preventDefault();
    //     alert('Producto guardado correctamente!');
    //     this.reset();
    //     document.getElementById('camposCategoria').innerHTML = '';
    //     document.getElementById('camposCategoria').style.display = 'none';
    // });