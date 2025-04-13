# 💻 Web integrado

Un sistema E-commerce combinado con un gestor de inventario para empleados

---

## 📁 Estructura del Proyecto

```plaintext
src/main/java
├── config/         → Configuraciones de seguridad o emails
├── controllers/ 
    ├──inventario   →  Controladores para el inventario
    ├──rest         →  Controlador para APIs (no usar)
    ├──web          →  Controladores para la web
├── dto/            → Formato para transporte de datos entre conntroladores
├── models/        
    ├──entities     →  las clases del proyecto
├── repositories/          → Las interfaces o modelo de datos de las entidades en la base de datos
├── services/       → Lógica de negocio (métodos CRUD)

src/main/resources
├── static/
    ├──css          →  Archivos css 
    ├──images       →  Imagenes en formato png
    ├──js           →  Archivos js
├── templates/
    ├──commerce     →  HTMLs de la pagina web 
    ├──venta        →  HTMLs del gestor de inventario

```

## 👨‍💻 Flujo de trabajo en equipo

1.  **Clonar el proyecto:**
    Crea tu carpeta local y clona el proyecto dentro de el.

    ```bash
    git clone LINK

2.  **Dirigirse al archivo appplication.properties y configurar la base de datos**
    Realiza los cambios necesarios en el username y password.

    ```bash
    spring.datasource.username=INGRESA TU USERNAME
    spring.datasource.password=INGRESA TU PASSWORD

4.  **Tener abierto el Mysql:**
    Antes de ejecutar el proyecto por primera vez tener abierto MySql en su Local Instance.


5.  **Ejecutar el proyecto:**
    Dirigirse al archivo EcommerceApplication.java para ejecutar el proyecto.

    # Elimina la rama local
    git branch -d feature/nombre-de-tarea

    # Elimina la rama remota
    git push origin --delete feature/nombre-de-tarea
