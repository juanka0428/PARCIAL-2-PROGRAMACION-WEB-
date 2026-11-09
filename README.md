# ECOSALUD - Gestión de consultas médicas

Aplicación web en Spring Boot para gestionar consultas médicas con tres roles: ADMIN, MEDICO y PACIENTE.

Tecnologías: Spring Boot 3.5, Spring Security (roles desde la base de datos y BCrypt), JPA, PostgreSQL, JSP, Bootstrap y Swagger.

## Qué puede hacer cada rol

- **ADMIN**: crear, editar y eliminar médicos, y registrar consultorios (consultas) con nombre del paciente, motivo, número de consultorio, horario y médico.
- **MEDICO**: solo ve las consultas que tiene asignadas, sin acciones.
- **PACIENTE**: ve sus citas y puede cambiar el horario solo de las suyas.

Si un usuario entra a algo que no le corresponde se muestra la página de acceso denegado (403).

## Cómo ejecutarlo

1. Crear la base en PostgreSQL ejecutando el script (pide la clave del usuario postgres):
   ```
   psql -U postgres -f sql/script.sql
   ```
2. En `src/main/resources/application.properties` cambiar `TU_CLAVE` por la clave del usuario postgres.
3. En Eclipse: *Import → Existing Maven Projects* y luego *Run As → Spring Boot App*.
4. Abrir `http://localhost:8080/login`

## Usuarios de prueba

| Usuario | Clave | Rol |
|---|---|---|
| administrador | admin2026 | ADMIN |
| juanmayor | juan2026 | MEDICO |
| diego | diego2026 | MEDICO |
| sofia | paciente2026 | PACIENTE |
| mateo | paciente2026 | PACIENTE |
| camila | paciente2026 | PACIENTE |

Nota: las citas del paciente se buscan por su nombre, entonces al registrar una consulta el nombre del paciente debe ser igual al nombre del usuario (ej: `Sofia Herrera`).

## Swagger

`http://localhost:8080/swagger-ui/index.html`

Primero se da clic en **Authorize** y se pone un usuario de la tabla. Sin credenciales los servicios responden 401 y con un rol sin permiso responden 403.

| Método | Ruta | Rol |
|---|---|---|
| GET, POST | /api/medicos | ADMIN |
| GET, PUT, DELETE | /api/medicos/{id} | ADMIN |
| GET, POST | /api/consultas | ADMIN |
| GET, PUT, DELETE | /api/consultas/{id} | ADMIN |
| GET | /api/consultas/mis-consultas | MEDICO, PACIENTE |
| PUT | /api/consultas/{id}/horario | PACIENTE (solo sus citas) |
