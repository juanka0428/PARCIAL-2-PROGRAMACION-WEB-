<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<nav class="navbar navbar-expand-lg navbar-dark barra mb-4">
	<div class="container">
		<span class="navbar-brand"><b>ECO<span class="text-white">SALUD</span></b></span>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menu">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="menu">
			<ul class="navbar-nav me-auto">
				<c:if test="${pageContext.request.isUserInRole('ADMIN')}">
					<li class="nav-item"><a class="nav-link" href="/admin/consultas">Consultas</a></li>
					<li class="nav-item"><a class="nav-link" href="/admin/medicos">Médicos</a></li>
				</c:if>
				<c:if test="${pageContext.request.isUserInRole('MEDICO')}">
					<li class="nav-item"><a class="nav-link" href="/medico/consultas">Mis consultas</a></li>
				</c:if>
				<c:if test="${pageContext.request.isUserInRole('PACIENTE')}">
					<li class="nav-item"><a class="nav-link" href="/paciente/consultas">Mis citas</a></li>
				</c:if>
				<li class="nav-item"><a class="nav-link" href="/swagger-ui/index.html" target="_blank">API</a></li>
			</ul>
			<span class="text-white me-3">${pageContext.request.userPrincipal.name}
				<c:if test="${pageContext.request.isUserInRole('ADMIN')}">(Administrador)</c:if>
				<c:if test="${pageContext.request.isUserInRole('MEDICO')}">(Médico)</c:if>
				<c:if test="${pageContext.request.isUserInRole('PACIENTE')}">(Paciente)</c:if>
			</span>
			<form action="/logout" method="post" class="d-inline">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				<button type="submit" class="btn btn-light btn-sm">Salir</button>
			</form>
		</div>
	</div>
</nav>
