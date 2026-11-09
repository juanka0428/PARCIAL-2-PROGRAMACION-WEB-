<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<div class="menu">
	<div class="nombre">ECO<span>SALUD</span></div>

	<div class="usuario">
		<b>${pageContext.request.userPrincipal.name}</b>
		<c:if test="${pageContext.request.isUserInRole('ADMIN')}">Administrador</c:if>
		<c:if test="${pageContext.request.isUserInRole('MEDICO')}">Médico</c:if>
		<c:if test="${pageContext.request.isUserInRole('PACIENTE')}">Paciente</c:if>
	</div>

	<ul>
		<c:if test="${pageContext.request.isUserInRole('ADMIN')}">
			<li><a href="/admin/consultas">Consultas</a></li>
			<li><a href="/admin/consultas/nueva">Registrar consultorio</a></li>
			<li><a href="/admin/medicos">Médicos</a></li>
		</c:if>
		<c:if test="${pageContext.request.isUserInRole('MEDICO')}">
			<li><a href="/medico/consultas">Mis consultas</a></li>
		</c:if>
		<c:if test="${pageContext.request.isUserInRole('PACIENTE')}">
			<li><a href="/paciente/consultas">Mis citas</a></li>
		</c:if>
		<li><a href="/swagger-ui/index.html" target="_blank">Documentación API</a></li>
	</ul>

	<form action="/logout" method="post" class="salir">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		<button type="submit">Cerrar sesión</button>
	</form>
</div>
