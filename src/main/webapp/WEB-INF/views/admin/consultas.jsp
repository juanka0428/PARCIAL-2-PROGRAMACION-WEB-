<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Consultas</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<div class="pagina">
		<%@ include file="/WEB-INF/views/encabezado.jsp"%>
		<div class="contenido">
			<div><h2>Consultas registradas</h2></div>

			<c:if test="${mensaje != null}">
				<div class="aviso">${mensaje}</div>
			</c:if>
			<c:if test="${error != null}">
				<div class="aviso-error">${error}</div>
			</c:if>

			<p><a href="/admin/consultas/nueva" class="boton">+ Registrar consultorio</a></p>

			<div class="table-responsive">
				<table class="tabla">
					<tr>
						<th>#</th>
						<th>Paciente</th>
						<th>Motivo</th>
						<th>Consultorio</th>
						<th>Horario</th>
						<th>Médico</th>
						<th></th>
					</tr>
					<c:forEach var="c" items="${consultas}">
						<tr>
							<td>${c.id}</td>
							<td>${c.nombrePaciente}</td>
							<td>${c.motivo}</td>
							<td class="consultorio">${c.numeroConsultorio}</td>
							<td>${c.horaInicio} a ${c.horaFin}</td>
							<td>${c.medico.nombre}</td>
							<td style="white-space: nowrap">
								<a href="/admin/consultas/editar/${c.id}" class="boton boton-amarillo boton-chico">Editar</a>
								<form action="/admin/consultas/eliminar/${c.id}" method="post" style="display: inline"
									onsubmit="return confirm('¿Eliminar esta consulta?')">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
									<button type="submit" class="boton boton-rojo boton-chico">Eliminar</button>
								</form>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty consultas}">
						<tr>
							<td colspan="7">No hay consultas registradas</td>
						</tr>
					</c:if>
				</table>
			</div>
			<%@ include file="/WEB-INF/views/pie.jsp"%>
		</div>
	</div>
</body>
</html>
