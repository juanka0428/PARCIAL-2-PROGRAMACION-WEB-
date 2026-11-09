# ECOSALUD

Aplicación web para gestionar consultas médicas, hecha con Spring Boot, JPA, Spring Security, PostgreSQL, JSP y Swagger.

Tiene tres roles:

- Administrador: crea, edita y elimina médicos y registra los consultorios.
- Médico: solo ve sus consultas.
- Paciente: ve sus citas y puede cambiar el horario.

## Cómo ejecutarlo

1. Ejecutar el archivo sql/script.sql en PostgreSQL para crear la base de datos.
2. En application.properties poner la clave de postgres donde dice TU_CLAVE.
3. Abrir el proyecto en Eclipse y correrlo como Spring Boot App.
4. Entrar a http://localhost:8080/login

La documentación de los servicios está en http://localhost:8080/swagger-ui/index.html

## Usuarios de prueba

- administrador / admin2026
- juanmayor / juan2026 (médico)
- diego / diego2026 (médico)
- sofia, mateo y camila / paciente2026 (pacientes)
