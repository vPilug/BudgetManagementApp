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
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js" integrity="sha384-zYPOMqeu1DAVkHiLqWBUTcbYfZ8osu1Nd6Z89ify25QV9guujx43ITvfi12/QExE" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.min.js" integrity="sha384-Y4oOpwW3duJdCWv5ly8SCFYWqFDsfob/3GkgExXKV4idmbt98QcxXYs9UoXAB7BZ" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<div class="dropdown">
    <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
        History
    </button>
    <ul class="dropdown-menu">
        <li><a class="dropdown-item" href="manage-incomes">Incomes</a></li>
        <li><a class="dropdown-item" href="manage-expenses">Expenses</a></li>
    </ul>
</div>
<div class="dropdown">
    <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
        Add
    </button>
    <ul class="dropdown-menu">
        <li><a class="dropdown-item" href="add-income">Incomes</a></li>
        <li><a class="dropdown-item" href="add-expense">Expenses</a></li>
    </ul>
</div>
<table class="table table-striped table-hover">
    <thead>
    <tr>
        <th scope="col">ID</th>
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
        <td>
        <a href="<c:out value="${context}"/>/manage-incomes?action=<c:out value="${requestScope.action_edit}"/>&incomeId=<c:out value="${income.id}"/>">
            <button type="button" class="btn btn-default btn-lg">
        <span class="glyphicon glyphicon-edit"></span> Edit
        </button>
        </a>
            <a href="<c:out value="${context}"/>/manage-incomes?action=<c:out value="${requestScope.action_delete}"/>&incomeId=<c:out value="${income.id}"/>">
                <i style="font-size:30px; color:red; padding-left: 20px" class="fa">&#xf014;</i>
            </a>
        </td>
    </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
