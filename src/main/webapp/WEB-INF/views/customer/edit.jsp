<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
<meta charset="UTF-8">
	<title>Clients :: Ajouter/Modifier un client</title>
</head>
<body>
	<h1>
		<c:choose>
			<c:when test="${empty item}">
				Ajouter un client
			</c:when>
			<c:otherwise>
				Modifier client (${item.id})
			</c:otherwise>
		</c:choose>
	</h1>
	
	<a href="javascript:history.go(-1)">Retour</a>
	
	
	<!-- <form action="" method="post"> -->
	
	<form action="${pageContext.request.contextPath}/customers" method="post">
		<input type="hidden" name="id" value="${item.id}" />
		<input type="hidden" name="action" value='<%= request.getAttribute("item") == null ? "create" : "update" %>' />
		<table border="0">
			<tbody>
				<tr>
					<th>Nom</th>
					<td>:</td>
					<td>
						<input type="text" name="name" value="${item.name}" required maxlength="100" />
					</td>
				</tr>
				<tr>
					<th>Email</th>
					<td>:</td>
					<td>
						<input type="email" name="email" value="${item.email}" required maxlength="100" />
					</td>
				</tr>
				<tr>
					<th>Tél.</th>
					<td>:</td>
					<td>
						<input type="tel" name="phone" value="${item.phone}" maxlength="30" />
					</td>
				</tr>
				<tr>
					<th>Adresse</th>
					<td>:</td>
					<td>
						<input name="address" value="${item.address}" maxlength="250" />
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<button type="submit">Valider</button> 
						<button type="reset">Réinitialiser</button> 
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>