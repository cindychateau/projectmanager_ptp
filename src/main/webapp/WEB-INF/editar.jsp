<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar Proyecto</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<h1>Editar Proyecto</h1>
		<form:form action="/actualizar" method="post" modelAttribute="proyecto">
			<div>
				<form:label path="titulo">Título del Proyecto</form:label>
				<form:input path="titulo" class="form-control" />
				<form:errors path="titulo" class="text-danger" />
			</div>
			<div>
				<form:label path="descripcion">Descripción del Proyecto</form:label>
				<form:textarea path="descripcion" class="form-control" />
				<form:errors path="descripcion" class="text-danger" />
			</div>
			<div>
				<form:label path="fecha">Fecha de Entrega</form:label>
				<form:input type="date" path="fecha" class="form-control" />
				<form:errors path="fecha" class="text-danger" />
			</div>
			<form:hidden path="lider" value="${usuarioEnSesion.id}"/>
			<form:hidden path="id" value="${proyecto.id}" />
			
			<input type="hidden" value="put" name="_method" />
			
			<input type="submit" class="btn btn-success mt-3" value="Guardar" />
		</form:form>
	</div>
</body>
</html>