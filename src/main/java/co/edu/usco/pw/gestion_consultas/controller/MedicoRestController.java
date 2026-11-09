package co.edu.usco.pw.gestion_consultas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.pw.gestion_consultas.model.Medico;
import co.edu.usco.pw.gestion_consultas.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/medicos")
@Tag(name = "Medicos", description = "CRUD de medicos. Solo lo puede usar el rol ADMIN")
public class MedicoRestController {

	@Autowired
	private MedicoService medicoService;

	@Operation(summary = "Listar medicos", description = "Devuelve todos los medicos registrados con su usuario (sin la contraseña). Solo ADMIN.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK, lista de medicos"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no envio credenciales o son incorrectas", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMIN", content = @Content) })
	@GetMapping
	public List<Medico> listar() {
		return medicoService.listar();
	}

	@Operation(summary = "Obtener un medico", description = "Busca un medico por su ID. Solo ADMIN.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK, medico encontrado"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no envio credenciales o son incorrectas", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMIN", content = @Content),
			@ApiResponse(responseCode = "404", description = "No existe un medico con ese ID", content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<Medico> obtener(@Parameter(description = "ID del medico", example = "1") @PathVariable Long id) {
		Medico m = medicoService.buscarPorId(id);
		if (m == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(m);
	}

	@Operation(summary = "Crear medico", description = "Crea un medico y su usuario con rol MEDICO para que pueda iniciar sesion. "
			+ "Enviar nombre, especialidad y usuario { username, password }. La contraseña se guarda con BCrypt. Solo ADMIN.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Created, medico creado"),
			@ApiResponse(responseCode = "400", description = "Datos invalidos o el usuario ya existe", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no envio credenciales o son incorrectas", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMIN", content = @Content) })
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Medico medico) {
		String error = medicoService.validar(medico, null);
		if (error != null) {
			return ResponseEntity.badRequest().body(error);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(medicoService.crear(medico));
	}

	@Operation(summary = "Editar medico", description = "Actualiza nombre, especialidad y usuario del medico. "
			+ "Si el password viene vacio se deja la contraseña anterior. Solo ADMIN.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK, medico actualizado"),
			@ApiResponse(responseCode = "400", description = "Datos invalidos", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no envio credenciales o son incorrectas", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMIN", content = @Content),
			@ApiResponse(responseCode = "404", description = "No existe el medico", content = @Content) })
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@Parameter(description = "ID del medico a editar", example = "1") @PathVariable Long id,
			@RequestBody Medico datos) {
		if (medicoService.buscarPorId(id) == null) {
			return ResponseEntity.notFound().build();
		}
		String error = medicoService.validar(datos, id);
		if (error != null) {
			return ResponseEntity.badRequest().body(error);
		}
		return ResponseEntity.ok(medicoService.actualizar(id, datos));
	}

	@Operation(summary = "Eliminar medico", description = "Elimina el medico y su usuario. No se puede si tiene consultas asignadas. Solo ADMIN.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK, medico eliminado"),
			@ApiResponse(responseCode = "400", description = "El medico tiene consultas asignadas", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no envio credenciales o son incorrectas", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMIN", content = @Content),
			@ApiResponse(responseCode = "404", description = "No existe el medico", content = @Content) })
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminar(@Parameter(description = "ID del medico a eliminar", example = "1") @PathVariable Long id) {
		if (medicoService.buscarPorId(id) == null) {
			return ResponseEntity.notFound().build();
		}
		if (!medicoService.eliminar(id)) {
			return ResponseEntity.badRequest().body("No se puede eliminar, el medico tiene consultas asignadas");
		}
		return ResponseEntity.ok("Medico eliminado");
	}
}
