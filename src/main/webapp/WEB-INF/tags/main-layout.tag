<%@tag description="Main layout" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="pageTitle" type="java.lang.String" %>
<%@ attribute name="headerContent" fragment="true" %>
<%@ attribute name="footerContent" fragment="true" %>


<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle}</title>
    <script src="js/form.js"></script>
    <script src="js/ajax.js"></script>
</head>
<body>
    <header>
        <!-- header content -->
        <jsp:invoke fragment="headerContent" />
    </header>
    
    <main>
        <jsp:doBody />
    </main>
    
    <footer>
        <jsp:invoke fragment="footerContent" />    
    </footer>
</body>
</html>
