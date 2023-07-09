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
	<a href="${pageContext.request.contextPath}/customers/edit">Ajouter</a>
	<form id="filters" action="${pageContext.request.contextPath}/customers" >
		<table border="1">
			<thead>
				<tr>
					<th>#
						<div class="filter">
							<input name="id" value="${filter.id}"/>
						</div>
					</th>
					<th>Nom
						<div class="filter">
							<input name="name" value="${filter.name}" />
						</div>
					</th>
					<th>Email
						<div class="filter">
							<input name="email" value="${filter.email}" />
						</div>
					</th>
					<th>Tél.
						<div class="filter">
							<input name="phone" value="${filter.phone}" />
						</div>
					</th>
					<th>Adresse
						<div class="filter">
							<input name="address" value="${filter.address}" />
						</div>
					</th>
					<th>Actions
						<div class="filter">
							<button type="submit">OK</button> 
							<button type="button" onclick="clearForm('filters')">Clear</button>
						</div>
					
					</th>
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
							<a href="${pageContext.request.contextPath}/customers/edit?id=${item.id}">Modifier</a>
							 delete</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>

<!-- 
	<tags:dataTable items="${list}" columns="${columns}">
	    <jsp:attribute name="actions">
	        <a href="${controllerPath}/edit?id=${it.id}">Modifier</a>
	        <a onClick="confirmDelete(event)"
	           href="${controllerPath}?id=${it.id}&_method=DELETE">Supprimer</a>
	    </jsp:attribute>
	</tags:dataTable>
 -->
 
<script>
	//vider un fomulaire avec un id
	function clearForm(id) {
	    // Récupération du formulaire par son ID
	    const form = document.getElementById(id);
	    
	    // Récupération de tous les éléments d'entrée dans le formulaire
	    const inputs = form.getElementsByTagName('input');
	    
	    // Parcours de tous les inputs du formulaire
	    Array.from(inputs).forEach(input => {
	        // vider le champ
	        input.value = '';
	    });
	
	    // Soumission du formulaire pour executer la recherche
	    form.submit();
	}
</script>
</body>
</html>