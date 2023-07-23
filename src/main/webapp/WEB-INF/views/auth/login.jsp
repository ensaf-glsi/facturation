<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login</title>
	<script src="${pageContext.request.contextPath}/js/form.js"></script>
</head>
<body>
	<h1>Information de connexion</h1>
	<c:if test="${not empty param.error }">
		<div class="error">
			Nom d'utilisateur ou mot de passe incorrect !
		</div>
	</c:if>
	<form action="${pageContext.request.contextPath}/auth" method="post">
		<input type="hidden" name="action" value="login" />
		<table border="0">
			<tbody>
				<tr>
					<th>Nom d'utilisateur</th>
					<td>:</td>
					<td>
						<input type="text" name="username" value="${param.username}" required maxlength="100" />
					</td>
				</tr>
				<tr>
					<th>Mot de passe</th>
					<td>:</td>
					<td>
						<input type="password" name="password" required maxlength="30" />
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<button type="submit">Se connecter</button> 
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>