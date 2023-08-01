<%--
  Created by IntelliJ IDEA.
  User: Vero
  Date: 5/16/2023
  Time: 5:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Budget management</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"
            integrity="sha384-zYPOMqeu1DAVkHiLqWBUTcbYfZ8osu1Nd6Z89ify25QV9guujx43ITvfi12/QExE"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.min.js"
            integrity="sha384-Y4oOpwW3duJdCWv5ly8SCFYWqFDsfob/3GkgExXKV4idmbt98QcxXYs9UoXAB7BZ"
            crossorigin="anonymous"></script>
    <%--    FONT LINKS --%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Great+Vibes&display=swap" rel="stylesheet">
</head>
<body>
<div style="padding: 20px 0; background-color: #D1FBED;"> <%-- START HEADER --%>
    <div style=" display: flex; width: 65%; margin: 0 auto;">
        <div style="width: 58%; display:flex; align-items: center"> <%-- Main Logo div --%>
            <svg xmlns="http://www.w3.org/2000/svg" width="65" height="65" fill="currentColor" class="bi bi-coin"
                 viewBox="0 0 16 16">
                <path d="M5.5 9.511c.076.954.83 1.697 2.182 1.785V12h.6v-.709c1.4-.098 2.218-.846 2.218-1.932 0-.987-.626-1.496-1.745-1.76l-.473-.112V5.57c.6.068.982.396 1.074.85h1.052c-.076-.919-.864-1.638-2.126-1.716V4h-.6v.719c-1.195.117-2.01.836-2.01 1.853 0 .9.606 1.472 1.613 1.707l.397.098v2.034c-.615-.093-1.022-.43-1.114-.9H5.5zm2.177-2.166c-.59-.137-.91-.416-.91-.836 0-.47.345-.822.915-.925v1.76h-.005zm.692 1.193c.717.166 1.048.435 1.048.91 0 .542-.412.914-1.135.982V8.518l.087.02z"/>
                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                <path d="M8 13.5a5.5 5.5 0 1 1 0-11 5.5 5.5 0 0 1 0 11zm0 .5A6 6 0 1 0 8 2a6 6 0 0 0 0 12z"/>
            </svg>
            <span style="font-family: 'Great Vibes'; font-size: 45px; font-weight: bold; padding-left: 20px;">Budget Management App</span>
        </div>
        <div style="width: 42%; display: flex; justify-content: space-between; align-items: flex-end;"> <%-- Main Buttons div --%>
            <a href="#">
                <button class="btn btn-lg" style="padding: 5px 12px; background-color: #486976;">
                    <svg xmlns="http://www.w3.org/2000/svg" width="35" height="35" fill="#ffffff"
                         class="bi bi-house-heart-fill" viewBox="0 0 16 16">
                        <path d="M7.293 1.5a1 1 0 0 1 1.414 0L11 3.793V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v3.293l2.354 2.353a.5.5 0 0 1-.708.707L8 2.207 1.354 8.853a.5.5 0 1 1-.708-.707L7.293 1.5Z"/>
                        <path d="m14 9.293-6-6-6 6V13.5A1.5 1.5 0 0 0 3.5 15h9a1.5 1.5 0 0 0 1.5-1.5V9.293Zm-6-.811c1.664-1.673 5.825 1.254 0 5.018-5.825-3.764-1.664-6.691 0-5.018Z"/>
                    </svg>
                </button>
            </a>
            <div class="dropdown">
                <button class="btn dropdown-toggle btn-lg" type="button" style="background-color: #486976; color: #fff;"
                        data-bs-toggle="dropdown"
                        aria-expanded="false">
                    Transactions history
                </button>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="manage-incomes">Incomes</a></li>
                    <li><a class="dropdown-item" href="manage-expenses">Expenses</a></li>
                </ul>
            </div>
            <div class="dropdown">
                <button class="btn dropdown-toggle btn-lg" style="background-color: #486976; color: #fff;" type="button"
                        data-bs-toggle="dropdown"
                        aria-expanded="false">
                    Add transaction
                </button>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="add-income">Incomes</a></li>
                    <li><a class="dropdown-item" href="add-expense">Expenses</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
<%-- END HEADER --%>
<div style="width: 65%; margin: 0 auto; padding: 50px 0;"> <%-- START CONTENT --%>
    <div style="float: right; padding-top: 80px">
        <img src="https://www.mintnotion.com/wp-content/uploads/2020/10/The-50_30_20-Budget-Rule.png" alt="Budget rule"
             width="600"
             height="500">
    </div>
    <div style="font-family: 'Open Sans'; font-size: 20px; padding: 205px 650px 0 60px;">
        <p>
            Budget Management App is an application that helps you manage your personal budget and track your income and
            expenses in a simple and efficient way. With its help, you can add incomes and expenses to later filter them
            based on desired time period or category. Additionally, you will find functions to delete or edit them.
        </p>
    </div>
</div>
<%-- END CONTENT --%>
<%-- START FOOTER --%>
<div style="background-color: #98B1B2; padding: 20px 0; width: 100%; position: absolute; bottom: 0; display: flex; justify-content: center; color: white;">
    <a href="https://www.linkedin.com/in/pilug/" target="_blank">
        <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-linkedin"
             viewBox="0 0 16 16">
            <path d="M0 1.146C0 .513.526 0 1.175 0h13.65C15.474 0 16 .513 16 1.146v13.708c0 .633-.526 1.146-1.175 1.146H1.175C.526 16 0 15.487 0 14.854V1.146zm4.943 12.248V6.169H2.542v7.225h2.401zm-1.2-8.212c.837 0 1.358-.554 1.358-1.248-.015-.709-.52-1.248-1.342-1.248-.822 0-1.359.54-1.359 1.248 0 .694.521 1.248 1.327 1.248h.016zm4.908 8.212V9.359c0-.216.016-.432.08-.586.173-.431.568-.878 1.232-.878.869 0 1.216.662 1.216 1.634v3.865h2.401V9.25c0-2.22-1.184-3.252-2.764-3.252-1.274 0-1.845.7-2.165 1.193v.025h-.016a5.54 5.54 0 0 1 .016-.025V6.169h-2.4c.03.678 0 7.225 0 7.225h2.4z"/>
        </svg>
    </a>
    <span style="padding-left: 20px"> Created by <a href="https://www.linkedin.com/in/pilug/"
                                                    target="_blank"
                                                    style="color: #486976;">Vero Pilug</a> © 2023 </span>
</div>
<%-- END FOOTER --%>
</body>
</html>
