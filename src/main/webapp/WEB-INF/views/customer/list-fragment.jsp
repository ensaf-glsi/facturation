<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:forEach items="${list}" var="item">
	<tr>
		<td>${item.id}</td>
		<td>${item.name}</td>
		<td>${item.email}</td>
		<td>${item.phone}</td>
		<td>${item.address}</td>
		<td>
			<a href="${pageContext.request.contextPath}/customers/edit?id=${item.id}">Modifier</a> 
			<a href="${pageContext.request.contextPath}/customers/delete?id=${item.id}"
				onclick="return confirm('Etes vous sur de vouloir supprimer ce client ?')">Supprimer</a>
		</td>
	</tr>
</c:forEach>