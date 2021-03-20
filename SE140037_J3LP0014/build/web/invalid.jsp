<%-- 
    Document   : error
    Created on : Jan 23, 2021, 4:19:15 PM
    Author     : TrongNS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <link href="assets/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="assets/css/myCSS.css" rel="stylesheet" type="text/css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:if test="${requestScope.INVALID_ROLE == null}">
            <c:if test="${sessionScope.USER != null}">
                <c:redirect url="home"/>
            </c:if>
        </c:if>
        <div class="content ml-0">
            <nav class="navbar navbar-expand-lg navbar-light" style="background: #005086;">
                <a class="navbar-brand ml-5" href="login">
                    <img src="image/logo.png" class="img-fluid" style="width: 100px; height: 100px;">
                </a>
                <ul class="navbar-nav ml-auto mr-5">
                    <li class="nav-item ml-2"><a class="nav-link btn btn-outline-primary bg-white" href="login">Login</a></li>
                    <li class="nav-item ml-2"><a class="nav-link btn btn-primary text-light" href="register">Sign Up</a></li>
                </ul>
            </nav>
            <div class="content-wrapper">
                <div class="card">
                    <div class="card-body d-flex flex-column justify-content-center align-items-center">
                        <h4>
                            <font color="red">
                            ${requestScope.INVALID_ROLE}
                            </font>
                        </h4>
                        <a href="login" class="btn btn-secondary mt-2">Back to Login</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
<!-- footer -->
<footer class="footer text-center text-lg-start">
    <!-- Copyright -->
    <div class="text-center p-3">
        © 2021 Copyright:
        <a class="text-info" href="home">Take Quiz Online</a>
    </div>
    <!-- Copyright -->
</footer>
