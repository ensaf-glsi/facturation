<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="items" required="true" type="java.util.List" %>
<%@ attribute name="columns" required="true" type="java.util.Map" %>
<%@ attribute name="actions" fragment="true" %>

<table>
    <thead>
        <tr>
            <c:forEach var="entry" items="${columns}">
                <th>${entry.value}</th>
            </c:forEach>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${items}">
            <tr>
                <c:forEach var="entry" items="${columns}">
                    <td>${item[entry.key]}</td>
                </c:forEach>
                <td>
                	<c:set var="it" value="${item}"/>
                    <jsp:invoke fragment="actions" />
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
