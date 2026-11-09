<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Consultas</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/encabezado.jsp"%>
	<div class="container">
		<div class="tarjeta">
			<div class="d-flex justify-content-between align-items-center mb-3">
				<h4 class="titulo mb-0">Consultas registradas</h4>
				<a href="/admin/consultas/nueva" class="btn btn-verde">Registrar consultorio</a>
			</div>

			<c:if test="${mensaje != null}">
				<div class="alert alert-success">${mensaje}</div>
			</c:if>
			<c:if test="${error != null}">
				<div class="alert alert-danger">${error}</div>
			</c:if>

			<div class="table-responsive">
				<table class="table table-bordered table-hover tabla">
					<thead>
						<tr>
							<th>#</th>
							<th>Paciente</th>
							<th>Motivo</th>
							<th>Consultorio</th>
							<th>Horario</th>
							<th>Médico</th>
							<th>Acciones</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="c" items="${consultas}">
							<tr>
								<td>${c.id}</td>
								<td>${c.nombrePaciente}</td>
								<td>${c.motivo}</td>
								<td><span class="consultorio">${c.numeroConsultorio}</span></td>
								<td>${c.horaInicio} - ${c.horaFin}</td>
								<td>${c.medico.nombre}</td>
								<td>
									<a href="/admin/consultas/editar/${c.id}" class="btn btn-warning btn-sm">Editar</a>
									<form action="/admin/consultas/eliminar/${c.id}" method="post" class="d-inline"
										onsubmit="return confirm('¿Eliminar esta consulta?')">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
										<button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
									</form>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty consultas}">
							<tr>
								<td colspan="7" class="text-center text-muted">No hay consultas</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
