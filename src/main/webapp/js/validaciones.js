// validaciones de los formularios

function validarLogin() {
	var usuario = document.getElementById("username").value.trim();
	var clave = document.getElementById("password").value;

	if (usuario == "") {
		alert("Ingrese el usuario");
		return false;
	}
	if (clave == "") {
		alert("Ingrese la contraseña");
		return false;
	}
	return true;
}

function validarMedico() {
	var nombre = document.getElementById("nombre").value.trim();
	var especialidad = document.getElementById("especialidad").value.trim();
	var usuario = document.getElementById("username").value.trim();
	var clave = document.getElementById("clave").value;
	var esNuevo = document.getElementById("esNuevo").value;

	if (nombre == "") {
		alert("El nombre es obligatorio");
		return false;
	}
	if (especialidad == "") {
		alert("La especialidad es obligatoria");
		return false;
	}
	if (usuario == "") {
		alert("El usuario es obligatorio");
		return false;
	}
	// la clave solo es obligatoria cuando es nuevo
	if (esNuevo == "true" && clave.length < 4) {
		alert("La contraseña debe tener minimo 4 caracteres");
		return false;
	}
	return true;
}

function validarConsulta() {
	var paciente = document.getElementById("nombrePaciente").value.trim();
	var motivo = document.getElementById("motivo").value.trim();
	var consultorio = document.getElementById("numeroConsultorio").value;
	var medico = document.getElementById("medico").value;

	if (paciente == "") {
		alert("El nombre del paciente es obligatorio");
		return false;
	}
	if (paciente.length > 40) {
		alert("El nombre del paciente no debe superar los 40 caracteres");
		return false;
	}
	// solo letras, numeros y espacios
	var expresion = /^[a-z0-9áéíóúñ ]+$/i;
	if (!expresion.test(paciente)) {
		alert("El nombre del paciente solo puede tener letras y numeros");
		return false;
	}
	if (motivo == "") {
		alert("El motivo de la consulta es obligatorio");
		return false;
	}
	if (motivo.length > 100) {
		alert("El motivo no debe superar los 100 caracteres");
		return false;
	}
	if (consultorio == "" || consultorio <= 0) {
		alert("Ingrese un numero de consultorio valido");
		return false;
	}
	if (!validarHorario()) {
		return false;
	}
	if (medico == "") {
		alert("Seleccione un medico");
		return false;
	}
	return true;
}

function validarHorario() {
	var inicio = document.getElementById("horaInicio").value;
	var fin = document.getElementById("horaFin").value;

	if (inicio == "" || fin == "") {
		alert("Ingrese la hora de inicio y la hora de finalizacion");
		return false;
	}
	// como son textos HH:mm se pueden comparar directo
	if (fin <= inicio) {
		alert("La hora de finalizacion debe ser mayor a la hora de inicio");
		return false;
	}
	return true;
}
