<%--
  Created by IntelliJ IDEA.
  User: Vero
  Date: 5/16/2023
  Time: 5:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored = "false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Budget management</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js" integrity="sha384-zYPOMqeu1DAVkHiLqWBUTcbYfZ8osu1Nd6Z89ify25QV9guujx43ITvfi12/QExE" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.min.js" integrity="sha384-Y4oOpwW3duJdCWv5ly8SCFYWqFDsfob/3GkgExXKV4idmbt98QcxXYs9UoXAB7BZ" crossorigin="anonymous"></script>
</head>
<body style="background-color: beige;">
<h1 align="center" style="font-family:'Lucida Calligraphy';"> Budget management</h1>
<div class="dropdown">
    <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
        History
    </button>
    <ul class="dropdown-menu">
        <li><a class="dropdown-item" href="incomes">Incomes</a></li>
        <li><a class="dropdown-item" href="expenses">Expenses</a></li>
    </ul>
</div>
<div class="dropdown">
    <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
        Add
    </button>
    <ul class="dropdown-menu">
        <li><a class="dropdown-item" href="incomes">Incomes</a></li>
        <li><a class="dropdown-item" href="expenses">Expenses</a></li>
    </ul>
</div>
<img src="https://rochester.kidsoutandabout.com/sites/default/files/Money%20Management%20for%20Teens-photo.jpg" alt="image" class="center">

</body>
</html>
