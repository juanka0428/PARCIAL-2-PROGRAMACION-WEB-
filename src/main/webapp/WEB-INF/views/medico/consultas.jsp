<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Mis consultas</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/encabezado.jsp"%>
	<div class="container">
		<div class="tarjeta">
			<h4 class="titulo">Mis consultas asignadas</h4>
			<p class="text-muted">Aquí puede ver la información de sus consultas.</p>

			<div class="table-responsive">
				<table class="table table-bordered table-hover tabla">
					<thead>
						<tr>
							<th>Paciente</th>
							<th>Motivo</th>
							<th>Consultorio</th>
							<th>Horario</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="c" items="${consultas}">
							<tr>
								<td>${c.nombrePaciente}</td>
								<td>${c.motivo}</td>
								<td><span class="consultorio">${c.numeroConsultorio}</span></td>
								<td>${c.horaInicio} - ${c.horaFin}</td>
							</tr>
						</c:forEach>
						<c:if test="${empty consultas}">
							<tr>
								<td colspan="4" class="text-center text-muted">No tiene consultas asignadas</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
