<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Mis citas</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/encabezado.jsp"%>
	<div class="container">
		<div class="tarjeta">
			<h4 class="titulo">Mis citas</h4>
			<p class="text-muted">Puede cambiar el horario de sus citas.</p>

			<c:if test="${mensaje != null}">
				<div class="alert alert-success">${mensaje}</div>
			</c:if>

			<div class="table-responsive">
				<table class="table table-bordered table-hover tabla">
					<thead>
						<tr>
							<th>Motivo</th>
							<th>Consultorio</th>
							<th>Médico</th>
							<th>Horario</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="c" items="${consultas}">
							<tr>
								<td>${c.motivo}</td>
								<td><span class="consultorio">${c.numeroConsultorio}</span></td>
								<td>${c.medico.nombre}</td>
								<td>${c.horaInicio} - ${c.horaFin}</td>
								<td><a href="/paciente/consultas/horario/${c.id}" class="btn btn-verde btn-sm">Cambiar horario</a></td>
							</tr>
						</c:forEach>
						<c:if test="${empty consultas}">
							<tr>
								<td colspan="5" class="text-center text-muted">No tiene citas asignadas</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
