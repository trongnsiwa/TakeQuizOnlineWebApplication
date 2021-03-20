<%-- 
    Document   : admin
    Created on : Jan 23, 2021, 8:02:38 PM
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
        <title>Admin Page - Home</title>
    </head>
    <body>
        <c:if test="${sessionScope.USER == null}">
            <c:redirect url="login" />
        </c:if>
        <c:if test="${sessionScope.ROLE ne 'Admin'}">
            <c:redirect url="home" />
        </c:if>
        <div class="wrapper">
            <nav id="sidebar">
                <div class="sidebar-header">
                    <div class="logo-image d-flex justify-content-center align-items-center w-100 mt-3">
                        <img src="image/logo.png" class="img-fluid">
                    </div>
                </div>
                <hr>
                <ul class="list-unstyled components">
                    <c:set var="subjectList" value="${requestScope.SUBJECT_LIST}"/>
                    <li><a href="home" style="background: #002b3d;">Home</a></li>
                    <li><a href="create-subject">Create Subject</a></li>
                    <li><a href="create-question">Create Question</a></li>
                    <li> <a href="#submenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Subjects</a>
                        <ul class="collapse list-unstyled" id="submenu">
                            <c:forEach var="subject" items="${subjectList}">
                                <c:url var="viewQuestionLink" value="view-question">
                                    <c:param name="id" value="${subject.subjectId}"/>
                                </c:url>
                                <li>
                                    <div class="d-flex w-100 justify-content-between list-subject" style="background: #318fb5;">
                                        <a href="${viewQuestionLink}">${subject.subjectName}</a>
                                        <a href="#submenu${subject.subjectId}" data-toggle="collapse" id="link-quiz" aria-expanded="false" class="dropdown-toggle dropdown-toggle-split"></a>
                                    </div>
                                    <ul class="collapse list-unstyled" id="submenu${subject.subjectId}">
                                        <li>
                                            <c:url var="updateQuizLink" value="update-quiz">
                                                <c:param name="id" value="${subject.subjectId}"/>
                                            </c:url>
                                            <a href="${updateQuizLink}">Update Quiz</a>
                                        </li>
                                    </ul>
                                </li>
                            </c:forEach>
                        </ul>
                    </li>
                </ul>
            </nav>
            <div class="container-fluid">
                <div class="content">
                    <nav class="navbar navbar-expand-lg navbar-light bg-light">
                        <ul class="navbar-nav ml-auto">
                            <li class="nav-item dropdown">
                                <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" style="background: #fafafa;">Welcome, Admin ${sessionScope.FULLNAME} <b class="caret pl-4"></b></a>
                                <ul class="dropdown-menu text-right w-100">
                                    <li><a class="dropdown-item text-danger" href="logout">Logout</a></li>
                                </ul>
                            </li>
                        </ul>
                    </nav>
                    <div class="content-wrapper">
                        <h4>Welcome, Admin ${sessionScope.FULLNAME}</h4>
                        <h5 class="mt-5 mb-3">List of subject:</h5>
                        <c:forEach var="subject" items="${subjectList}">
                            <div class="card my-2 border-primary">
                                <div class="card-body">
                                    <c:url var="viewQuestionLink" value="view-question">
                                        <c:param name="id" value="${subject.subjectId}"/>
                                    </c:url>
                                    <h5 class="card-title"><a href="${viewQuestionLink}">${subject.subjectName}</a></h5>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <!-- footer -->
                <footer class="footer text-center text-lg-start">
                    <!-- Copyright -->
                    <div class="text-right p-3">
                        © 2021 Copyright:
                        <a class="text-info" href="home">Take Quiz Online</a>
                    </div>
                    <!-- Copyright -->
                </footer>
            </div>
        </div>
    </body>
</html>
