# 4SRealStateApplication

Una aplicación web MVC para la gestión, visualización de datos y automatización de procesos que se utilizan en los diferentes departamentos, que permite interactuar con servicios web y exportar información.

---

## **Descripción**
Este proyecto proporciona una plataforma donde los usuarios pueden:
- Visualizar datos obtenidos desde un servicio web.
- Ordenar y ocultar columnas en tablas.
- Realizar procesos que les tomaban mucho tiempo de forma más automatizada.
- Exportar datos a un archivo Excel.

### **Tecnologías Principales**
- **Backend**: Spring Boot (v3.3.5)
- **Frontend**: Thymeleaf, Bootstrap 5, JavaScript
- **Servidor de Aplicaciones**: Tomcat (v10.1, configurado como `provided`)
- **Java**: Versión 21

---

## **Instalación**

### **Requisitos**
- Java 21
- Maven 3.x

### **Pasos**
1. Clona el repositorio:
   ```bash
   git clone https://github.com/MrVChema/4SRealStateApplication.git
   ```
2. Ve al directorio del proyecto:
   ```bash
   cd 4SRealStateApplication
   ```
3. Compila y construye el proyecto:
   ```bash
   mvn clean install
   ```
4. Ejecuta la aplicación:
   ```bash
   mvn spring-boot:run
   ```

5. Accede a la aplicación en tu navegador:
   ```
   http://localhost:8084
   ```

---

## **Estructura del Proyecto**

```
Grupo4SApplication
├── src
   ├── main
   │   ├── java/com/cuatroSReal
   │   │   ├── controller        # Controladores del proyecto
   │   │   ├── init              # Iniciador de aplicación
   │   │   ├── service           # Lógica de negocio
   │   │   └── model             # Modelos de datos
   │   ├── resources
   │       ├── templates          # Vistas HTML (Thymeleaf)
   │       └── static             # Recursos estáticos (CSS, JS, imágenes)
   ├── test
       └── java/com/cuatroSReal
```

---

## **Licencia**
Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.

---

## **Contacto**
- **Autor**: Chema Sánchez (MrVergil) - Equipo Desarrollo 4S Real State
- **Correo**: jose.sanchez@grupo4s.com
- **Repositorio**: [https://github.com/usuario/Grupo4SApplication](https://github.com/MrVChema/4SRealStateApplication.git)

