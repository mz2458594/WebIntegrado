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
