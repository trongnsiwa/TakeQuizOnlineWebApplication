<%-- 
    Document   : login
    Created on : Jan 22, 2021, 11:14:51 PM
    Author     : TrongNS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <link href="assets/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="assets/css/myCSS.css" rel="stylesheet" type="text/css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Signin</title>
    </head>
    <body>
        <c:if test="${sessionScope.USER != null}">
            <c:redirect url="home" />
        </c:if>
        <div class="content ml-0 p-0">
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
                <div class="container d-flex flex-column align-items-center">
                    <div class="card bg-white border-primary shadow-lg w-50">
                        <div class="m-2 mt-3">
                            <h3 class="text-center p-2 mt-3 mb-0 text-primary">Log in</h3>
                            <c:if test="${requestScope.LOGIN_ERROR != null}">
                                <font color="red">
                                ${requestScope.LOGIN_ERROR}
                                </font>
                            </c:if>
                            <c:if test="${requestScope.CREATE_ACCOUNT_SUCCESS != null}">
                                <font color="green">
                                ${requestScope.CREATE_ACCOUNT_SUCCESS}
                                </font>
                            </c:if>
                        </div>
                        <div class="card-body">
                            <form action="login" method="POST">
                                <div class="form-group">
                                    <label for="txtEmail">
                                        Email:
                                    </label>
                                    <input type="text" maxlength="50" class="form-control" name="txtEmail" value="${param.txtEmail}" required="required"/>
                                </div>
                                <div class="form-group">
                                    <label for="txtPassword">
                                        Password:
                                    </label>
                                    <input type="password" class="form-control" name="txtPassword" value="${param.txtPassword}" required="required"/>
                                </div>
                                <div class="d-flex justify-content-center">
                                    <input type="submit" class="btn btn-primary mb-3 mr-1" value="Login" style="width: 100px; height: 50px;"/>
                                    <input type="reset" class="btn btn-secondary mb-3" value="Reset" style="width: 100px; height: 50px;"/>
                                </div>
                                <hr class="my-2">
                                <div class="d-flex justify-content-center align-items-center w-100 mt-2">
                                    <p>Don't have an account? <a href="register">Sign up here</a></p>
                                </div>
                            </form>
                        </div>
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
