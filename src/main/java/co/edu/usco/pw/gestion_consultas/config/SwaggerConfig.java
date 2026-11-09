package co.edu.usco.pw.gestion_consultas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		// esquema de autenticacion basica para el boton Authorize de swagger
		SecurityScheme basico = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")
				.description("Ingrese usuario y contraseña. Ej: administrador/admin2026, juanmayor/juan2026, sofia/paciente2026");

		return new OpenAPI()
				.info(new Info().title("ECOSALUD - API de Consultas Medicas").version("1.0.0")
						.description("Servicios web para gestionar medicos y consultas medicas. "
								+ "Roles: ADMIN (crea, edita y elimina medicos y consultas), "
								+ "MEDICO (solo ve sus consultas) y PACIENTE (ve sus citas y cambia el horario). "
								+ "Primero use el boton Authorize con un usuario. Sin credenciales la respuesta es 401 "
								+ "y con un rol que no tiene permiso la respuesta es 403.")
						.contact(new Contact().name("Juan Mayor").email("juankagamer8@gmail.com")))
				.components(new Components().addSecuritySchemes("basicAuth", basico))
				.addSecurityItem(new SecurityRequirement().addList("basicAuth"));
	}
}
