<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Mis citas</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<div class="pagina">
		<%@ include file="/WEB-INF/views/encabezado.jsp"%>
		<div class="contenido">
			<div><h2>Mis citas</h2></div>
			<p>Si necesita, puede cambiar el horario de sus citas.</p>

			<c:if test="${mensaje != null}">
				<div class="aviso">${mensaje}</div>
			</c:if>

			<div class="table-responsive">
				<table class="tabla">
					<tr>
						<th>Motivo</th>
						<th>Consultorio</th>
						<th>Médico</th>
						<th>Horario</th>
						<th></th>
					</tr>
					<c:forEach var="c" items="${consultas}">
						<tr>
							<td>${c.motivo}</td>
							<td class="consultorio">${c.numeroConsultorio}</td>
							<td>${c.medico.nombre}</td>
							<td>${c.horaInicio} a ${c.horaFin}</td>
							<td><a href="/paciente/consultas/horario/${c.id}" class="boton boton-amarillo boton-chico">Cambiar horario</a></td>
						</tr>
					</c:forEach>
					<c:if test="${empty consultas}">
						<tr>
							<td colspan="5">No tiene citas asignadas</td>
						</tr>
					</c:if>
				</table>
			</div>
			<%@ include file="/WEB-INF/views/pie.jsp"%>
		</div>
	</div>
</body>
</html>
