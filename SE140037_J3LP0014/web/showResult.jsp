<%-- 
    Document   : showResult
    Created on : Jan 28, 2021, 8:20:24 PM
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
        <title>Student Page - Show Result</title>
    </head>
    <body>
        <c:if test="${sessionScope.USER == null}">
            <c:redirect url="login" />
        </c:if>
        <c:if test="${sessionScope.ROLE ne 'Student'}">
            <c:redirect url="home" />
        </c:if>
        <div class="content ml-0 p-0">
            <nav class="navbar navbar-expand-lg navbar-light" style="background: #005086;">
                <a class="navbar-brand ml-5" href="home">
                    <img src="image/logo.png" class="img-fluid" style="width: 100px; height: 100px;">
                </a>
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item dropdown mr-5">
                        <a href="#" class="nav-link dropdown-toggle text-white" data-toggle="dropdown">Welcome, ${sessionScope.FULLNAME} <b class="caret pl-4"></b></a>
                        <ul class="dropdown-menu text-right">
                            <li><a class="dropdown-item text-white" href="history">History</a></li>
                            <li><a class="dropdown-item text-danger" href="logout">Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
            <div class="content-wrapper">
                <div class="container">
                    <h1 class="mt-3 mb-5">${sessionScope.FULLNAME}, You have done the quiz: ${requestScope.SUBJECT_NAME}!</h1>
                    <h3 class="mb-3">This is your result:</h3>
                    <div class="card bg-white border-primary">
                        <div class="card-body">
                            <p>
                                <b>Number Of Correct:</b> <span style="font-size: 20px;">${requestScope.NUMBER_OF_CORRECT} / ${requestScope.QUESTION_QUANTITY}</span>
                            </p>
                            <p>
                                <b>Grade:</b> <span style="font-size: 20px;">${requestScope.STUDENT_GRADE}</span>
                            </p>
                            <p>
                                <b>Submit Time:</b> <span style="font-size: 20px;">${requestScope.SUBMIT_TIME}</span>
                            </p>
                            <hr class="my-3">
                            <c:url var="subjectLink" value="view">
                                <c:param name="id" value="${requestScope.SUBJECT_ID}"/>
                            </c:url>
                            <a href="${subjectLink}" class="btn btn-primary">Continute</a>
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
