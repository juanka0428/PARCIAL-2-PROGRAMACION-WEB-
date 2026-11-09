package co.edu.usco.pw.gestion_consultas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
				.authorizeHttpRequests(auth -> auth
						// para que deje mostrar las jsp
						.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
						.requestMatchers("/login", "/css/**", "/js/**").permitAll()
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

						// paginas
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/medico/**").hasRole("MEDICO")
						.requestMatchers("/paciente/**").hasRole("PACIENTE")

						// servicios web
						.requestMatchers(HttpMethod.GET, "/api/consultas/mis-consultas").hasAnyRole("MEDICO", "PACIENTE")
						.requestMatchers(HttpMethod.PUT, "/api/consultas/*/horario").hasRole("PACIENTE")
						.requestMatchers("/api/medicos/**").hasRole("ADMIN")
						.requestMatchers("/api/consultas/**").hasRole("ADMIN")
						.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/inicio", true).permitAll())
				// autenticacion basica para probar los servicios en swagger
				.httpBasic(Customizer.withDefaults())
				.logout(logout -> logout.logoutSuccessUrl("/login?salir").permitAll())
				// cuando no tiene permiso: la api responde 403 y las paginas van a la vista personalizada
				.exceptionHandling(ex -> ex.accessDeniedHandler((request, response, e) -> {
					if (request.getRequestURI().startsWith("/api")) {
						response.sendError(403);
					} else {
						response.sendRedirect("/acceso-denegado");
					}
				}));
		return http.build();
	}
}
