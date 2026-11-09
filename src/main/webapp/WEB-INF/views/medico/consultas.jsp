<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Mis consultas</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<div class="pagina">
		<%@ include file="/WEB-INF/views/encabezado.jsp"%>
		<div class="contenido">
			<div><h2>Mis consultas</h2></div>
			<p>Estas son las consultas que tiene asignadas.</p>

			<div class="table-responsive">
				<table class="tabla">
					<tr>
						<th>Paciente</th>
						<th>Motivo</th>
						<th>Consultorio</th>
						<th>Horario</th>
					</tr>
					<c:forEach var="c" items="${consultas}">
						<tr>
							<td>${c.nombrePaciente}</td>
							<td>${c.motivo}</td>
							<td class="consultorio">${c.numeroConsultorio}</td>
							<td>${c.horaInicio} a ${c.horaFin}</td>
						</tr>
					</c:forEach>
					<c:if test="${empty consultas}">
						<tr>
							<td colspan="4">No tiene consultas asignadas</td>
						</tr>
					</c:if>
				</table>
			</div>
			<%@ include file="/WEB-INF/views/pie.jsp"%>
		</div>
	</div>
</body>
</html>
