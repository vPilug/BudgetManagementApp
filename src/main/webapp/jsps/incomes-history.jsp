<%--
  Created by IntelliJ IDEA.
  User: Vero
  Date: 5/16/2023
  Time: 7:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored = "false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Incomes</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th scope="col">Id</th>
        <th scope="col">Date</th>
        <th scope="col">Amount</th>
        <th scope="col">Source</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${requestScope.incomesList}" var="income">
    <tr>
        <td><c:out value="${income.id}"/></td>
        <td><c:out value="${income.date}"/></td>
        <td><c:out value="${income.amount}"/></td>
        <td><c:out value="${income.source}"/></td>
    </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
