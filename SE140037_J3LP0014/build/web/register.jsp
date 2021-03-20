<%-- 
    Document   : register
    Created on : Jan 22, 2021, 9:02:04 PM
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
        <title>Signup</title>
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
                <div class="container-fluid mt-4 d-flex flex-column align-items-center" style="position: relative; height: 100%; padding-bottom: 6rem;">
                    <div class="card shadow-lg">
                        <div class="card-body">
                            <div class="text-center text-primary my-3">
                                <h4>Create An Account</h4>
                            </div>
                            <c:if test="${requestScope.CREATE_ACCOUNT_FAIL != null}">
                                <font color="red">
                                ${requestScope.CREATE_ACCOUNT_FAIL}
                                </font>
                            </c:if>
                            <form action="create-an-account" method="POST">
                                <c:set var="error" value="${requestScope.CREATE_ACCOUNT_ERROR}"/>
                                <div class="form-group">
                                    <label for="txtEmail">
                                        Email:
                                    </label>
                                    <input type="text" maxlength="50" class="form-control" name="txtEmail" value="${param.txtEmail}" required="required"/><br/>
                                    <c:if test="${not empty error.invalidEmail}">
                                        <font color="red">
                                        ${error.invalidEmail}
                                        </font>
                                    </c:if>
                                    <c:if test="${not empty error.duplicateEmail}">
                                        <font color="red">
                                        ${error.duplicateEmail}
                                        </font>
                                    </c:if>
                                </div>
                                <div class="form-group">
                                    <label for="txtPassword">
                                        Password:
                                    </label>
                                    <input type="password" name="txtPassword" class="form-control" value="" required="required"/><br/>
                                </div>
                                <div class="form-group">
                                    <label for="txtConfirm">
                                        Confirm password:
                                    </label>
                                    <input type="password" name="txtConfirm" class="form-control" value="" required="required"/>
                                    <c:if test="${not empty error.confirmNotMatchPassword}">
                                        <font color="red">
                                        ${error.confirmNotMatchPassword}
                                        </font>
                                    </c:if>
                                </div>
                                <div class="form-group">
                                    <label for="txtFullname">
                                        Fullname:
                                    </label>
                                    <input type="text" minlength="2" maxlength="100" class="form-control" name="txtFullname" value="${param.txtFullname}" required="required"/><br/>
                                </div>
                                <div class="form-group">
                                    Role:
                                    <div class="d-flex align-items-center justify-content-center w-100">
                                        <input type="radio" class="mr-2" id="student" name="rdRole" value="Student" checked="checked" style="width: 20px; height: 20px;"/>
                                        <label for="student">Student</label>
                                        <input type="radio" class="ml-3 mr-2" id="admin" name="rdRole" value="Teacher" <c:if test="${param.rdRole eq 'Teacher'}">checked="checked"</c:if> style="width: 20px; height: 20px;"/>
                                        <label for="admin">Teacher</label>
                                    </div>
                                </div>
                                <hr class="my-4">
                                <div class="d-flex justify-content-center align-items-center w-100 mt-4">
                                    <input type="submit" class="btn btn-primary" value="Register" style="width: 100px; height: 50px;"/>
                                    <input type="reset" class="btn btn-secondary ml-2" value="Reset" style="width: 100px; height: 50px;"/>
                                </div>
                                <div class="d-flex justify-content-center align-items-center w-100 mt-2">
                                    <p>Already have an account? <a href="login">Signin</a></p>
                                </div>
                            </form>
                        </div>
                    </div>
                    <!-- footer -->
                    <footer class="footer text-center text-lg-start">
                        <!-- Copyright -->
                        <div class="text-center p-3">
                            © 2021 Copyright:
                            <a class="text-info" href="home">Take Quiz Online</a>
                        </div>
                        <!-- Copyright -->
                    </footer>
                </div>
            </div>
        </div>
    </body>
</html>
