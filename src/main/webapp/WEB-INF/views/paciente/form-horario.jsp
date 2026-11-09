<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Cambiar horario</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<div class="pagina">
		<%@ include file="/WEB-INF/views/encabezado.jsp"%>
		<div class="contenido">
			<div><h2>Cambiar horario</h2></div>

			<c:if test="${error != null}">
				<div class="aviso-error">${error}</div>
			</c:if>

			<form action="/paciente/consultas/horario/${consulta.id}" method="post" onsubmit="return validarHorario()" style="max-width: 500px">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

				<fieldset>
					<legend>Cita</legend>
					<p>
						<b>Motivo:</b> ${consulta.motivo}<br>
						<b>Consultorio:</b> ${consulta.numeroConsultorio}<br>
						<b>Médico:</b> ${consulta.medico.nombre}
					</p>

					<div class="row">
						<div class="col">
							<label for="horaInicio">Hora de inicio</label>
							<input type="time" name="horaInicio" id="horaInicio" class="form-control" value="${consulta.horaInicio}">
						</div>
						<div class="col">
							<label for="horaFin">Hora de finalización</label>
							<input type="time" name="horaFin" id="horaFin" class="form-control" value="${consulta.horaFin}">
						</div>
					</div>
				</fieldset>

				<p style="margin-top: 15px">
					<button type="submit" class="boton">Guardar horario</button>
					<a href="/paciente/consultas" class="boton boton-gris">Cancelar</a>
				</p>
			</form>
			<%@ include file="/WEB-INF/views/pie.jsp"%>
		</div>
	</div>
	<script src="/js/validaciones.js"></script>
</body>
</html>
