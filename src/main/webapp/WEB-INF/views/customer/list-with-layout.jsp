<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:main-layout pageTitle="Clients">
    <jsp:body>
		<h1>Liste des clients :</h1>
		<a href="${pageContext.request.contextPath}/customers/edit">Ajouter</a>
		<form onSubmit="handleSearch(event)" id="filters" action="${pageContext.request.contextPath}/customers" >
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
					<%@ include file="list-fragment.jsp" %>
					<%-- 
					<jsp:include page="list-fragment.jsp">
						<jsp:param name="list" value="${list}"/>
	    			</jsp:include>
					--%>
				</tbody>
			</table>
		</form>
	
		<script>
			function handleSearch(event) {
				event.preventDefault();
				const form = event.target;
				console.log('handle search', event);
			    const params = new FormData(form);
			
			    xhrLoad('#filters tbody', form.action, params);
			}
		</script>

    </jsp:body>
</tags:main-layout>
