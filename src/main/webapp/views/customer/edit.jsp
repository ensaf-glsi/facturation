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
		Ajouter un client :
	</h1>
	
	<!-- <form action="" method="post"> -->
	
	<form action="${pageContext.request.contextPath}/customers" method="post">
		<input type="hidden" name="id" value="${item.id}">
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