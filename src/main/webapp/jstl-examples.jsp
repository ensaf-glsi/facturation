<%@ page import="java.util.Vector" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

    <h1>Tag Plugin Examples - &lt;c:if></h1>
    <h3>Set the test result to a variable</h3>
    <c:if test="${1==1}" var="theTruth" scope="page"/>
    The result of testing for (1==1) is: ${theTruth}

    <h3>Conditionally execute the body</h3>
    <c:if test="${2>0}">
        <p>It's true that (2>0)! Working.</p>
    </c:if>
    <c:if test="${0>2}">
        <p>It's not true that (0>2)! Failed.</p>
    </c:if>
    
    

    <h1>Tag Plugin Examples - &lt;c:forEach></h1>
    <h3>Iterating over a range</h3>
    <c:forEach var="item" begin="1" end="10">
        ${item}
    </c:forEach>

    <% Vector<String> v = new Vector<>();
        v.add("One"); v.add("Two"); v.add("Three"); v.add("Four");

        pageContext.setAttribute("vector", v);
    %>

    <h3>Iterating over a Vector</h3>

    <c:forEach items="${vector}" var="item" >
        ${item}
    </c:forEach>
    
    
    
    
    <h1>Tag Plugin Examples - &lt;c:choose></h1>
    <c:forEach var="index" begin="0" end="4">
      # ${index}:
      <c:choose>
        <c:when test="${index == 1}">
          One!<br/>
        </c:when>
        <c:when test="${index == 4}">
          Four!<br/>
        </c:when>
        <c:when test="${index == 3}">
          Three!<br/>
        </c:when>
        <c:otherwise>
          Huh?<br/>
        </c:otherwise>
      </c:choose>
    </c:forEach>

</body>
</html>