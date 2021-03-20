<%-- 
    Document   : student
    Created on : Jan 26, 2021, 11:17:40 PM
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
        <title>Student Page - Home</title>
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
                            <li><a class="dropdown-item" href="history">History</a></li>
                            <li><a class="dropdown-item text-danger" href="logout">Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
            <div class="content-wrapper">
                <div class="container">
                    <c:set var="subjectList" value="${requestScope.SUBJECT_LIST}"/>
                    <h3 class="mb-5" style="color: #005086;">Welcome, ${sessionScope.FULLNAME}</h3>
                    <c:if test="${not empty subjectList}">
                        <div class="card border-primary">
                            <div class="card-body">
                                <p class="font-weight-bold">Choose subject to do quiz:</p>
                                <c:forEach var="subject" items="${subjectList}">
                                    <div class="card my-2 border-primary">
                                        <div class="card-body">
                                            <c:url var="subjectLink" value="view">
                                                <c:param name="id" value="${subject.subjectId}"/>
                                            </c:url>
                                            <h5 class="card-title"><a href="${subjectLink}">${subject.subjectName}</a></h5>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${empty subjectList}">
                        <div class="d-flex w-100 justify-content-center">
                            <h3>
                                <font color="red">
                                We're updating the data, please come back later <i class="fa fa-wrench"></i>
                                </font>
                            </h3>
                        </div>
                    </c:if>
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
