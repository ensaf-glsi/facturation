<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
<meta charset="UTF-8">
<title>Clients</title>
</head>
<body>
	<h1>Liste des clients :</h1>
	<a href="new">Ajouter</a>

	<table border="1">
		<thead>
			<tr>
				<th>#</th>
				<th>Nom</th>
				<th>Email</th>
				<th>Tél.</th>
				<th>Adresse</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
		    <c:forEach items="${list}" var="item">
				<tr>
					<td>${item.id}</td>
					<td>${item.name}</td>
					<td>${item.email}</td>
					<td>${item.phone}</td>
					<td>${item.address}</td>
					<td>
						<a href="${pageContext.request.contextPath}/clients/edit?id=${item.id}">Modifier</a>
						 delete</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<tags:dataTable items="${list}" columns="${columns}">
	    <jsp:attribute name="actions">
	        <a href="${controllerPath}/edit?id=${it.id}">Modifier</a>
	        <a onClick="confirmDelete(event)"
	           href="${controllerPath}?id=${it.id}&_method=DELETE">Supprimer</a>
	    </jsp:attribute>
	</tags:dataTable>

</body>
</html>