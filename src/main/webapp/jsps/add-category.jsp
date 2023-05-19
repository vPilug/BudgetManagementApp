<%--
  Created by IntelliJ IDEA.
  User: Vero
  Date: 5/18/2023
  Time: 7:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <title>Add category</title>
</head>
<body>
<form method="post" action="<c:out value="${context}"/>/add-category">
    <fieldset enable>
        <legend>Add a new category</legend>
        <div class="mb-3">
            <input type="text" id="name" class="form-control" name="name" placeholder="new category"
                   value="<c:out value="${requestScope.categoriesList.name}"/>">
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </fieldset>
</form>
</body>
</html>
