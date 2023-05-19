<%--
  Created by IntelliJ IDEA.
  User: Vero
  Date: 5/16/2023
  Time: 8:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
    <title>Add income</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"
            integrity="sha384-zYPOMqeu1DAVkHiLqWBUTcbYfZ8osu1Nd6Z89ify25QV9guujx43ITvfi12/QExE"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.min.js"
            integrity="sha384-Y4oOpwW3duJdCWv5ly8SCFYWqFDsfob/3GkgExXKV4idmbt98QcxXYs9UoXAB7BZ"
            crossorigin="anonymous"></script>
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
<form method="post" action="<c:out value="${context}"/>/add-expense">
    <fieldset enable>
        <legend>Add expense</legend>
        <div class="mb-3">
            <label for="date" class="form-label">Date</label>
            <input type="date" id="date" class="form-control" name="date"
                   value="<c:out value="${requestScope.expense.date}"/>">
        </div>
        <div class="mb-3">
            <label for="amount" class="form-label">Amount</label>
            <input type="number" step="0.01" id="amount" class="form-control" placeholder="100.00" name="amount"
                   value="<c:out value="${requestScope.expense.amount}"/>">
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <input type="text" id="description" class="form-control" placeholder="Expense description"
                   name="description" value="<c:out value="${requestScope.expense.description}"/>">
        </div>
        <div class="mb-3">
            <label for="categoryId" class="form-label">Category</label>
            <select id="categoryId" class="form-select" name="categoryId">
                <c:forEach var="category" items="${requestScope.categoriesList}">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
        </div>
    </fieldset>
</form>
<button type="submit" class="btn btn-primary">Submit</button>
<a href="<c:out value="${context}"/>/add-category">
    <button type="add" class="btn btn-info">Add new category</button>
</a>
</body>
</html>
