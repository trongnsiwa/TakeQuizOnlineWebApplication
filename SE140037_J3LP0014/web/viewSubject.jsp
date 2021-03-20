<%-- 
    Document   : viewSubject
    Created on : Jan 27, 2021, 12:09:48 AM
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
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-3">
                            <c:set var="subjectList" value="${requestScope.SUBJECT_LIST}"/>
                            <div class="card border-primary">
                                <div class="card-body">
                                    <p class="font-weight-bold">List Of Subjects:</p>
                                    <c:forEach var="dto" items="${subjectList}">
                                        <div class="card my-2 border-primary">
                                            <div class="card-body">
                                                <c:url var="subjectLink" value="view">
                                                    <c:param name="id" value="${dto.subjectId}"/>
                                                </c:url>
                                                <h5 class="card-title"><a href="${subjectLink}">${dto.subjectName}</a></h5>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-9">
                            <c:set var="subject" value="${requestScope.SUBJECT_INFO}"/>
                            <c:if test="${subject != null}">
                                <c:if test="${subject.quizQuestionQuantity > 0}">
                                    <h3 class="m-3" style="color: #005086;">${subject.subjectId} - ${subject.subjectName}</h3>
                                    <hr class="my-2 mb-3">

                                    <ul>
                                        <div class="my-2">
                                            <c:if test="${not empty requestScope.START_QUIZ_ERROR}">
                                                <font color="red">
                                                ${requestScope.START_QUIZ_ERROR}
                                                </font>
                                            </c:if>
                                        </div>
                                        <li class="list-unstyled bg-white p-3 border border-primary" style="border-radius: 10px;">
                                            <div class="d-flex flex-row justify-content-between align-items-center w-100">
                                                <div>
                                                    <p><b>Time limit:</b> ${subject.timeLimit} mins</p>
                                                    <p><b>Question quantity:</b> ${subject.quizQuestionQuantity}</p>
                                                </div>
                                                <div class="ml-5">
                                                    <p><b>Grade:</b> ${subject.grade}</p>
                                                    <input type="hidden" class="date" value="${subject.startDate}"/>
                                                    <input type="hidden" class="date" value="${subject.endDate}"/>
                                                    <p><b>Start Date:</b> <span class="format-date">${subject.startDate}</span> - <b>End Date:</b> <span class="format-date">${subject.endDate}</span></p> 
                                                </div>
                                                <div class="justify-content-end align-items-end">
                                                    <c:if test="${not subject.attempting && sessionScope.QUESTION_LIST == null}">
                                                        <c:url var="quizLink" value="attempt-quiz">
                                                            <c:param name="id" value="${subject.subjectId}"/>
                                                        </c:url>
                                                        <a href="${quizLink}" class="btn btn-primary">Attempt Quiz</a>
                                                    </c:if>
                                                    <c:if test="${subject.attempting || sessionScope.QUESTION_LIST != null}">
                                                        <c:url var="reattemptLink" value="attempt-quiz">
                                                            <c:param name="attempting" value="${quiz.attempting}"/>
                                                            <c:param name="id" value="${subject.subjectId}"/>
                                                        </c:url>
                                                        <a href="${reattemptLink}" class="btn btn-primary">Reattempt Quiz</a>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </c:if>
                                <c:if test="${subject.quizQuestionQuantity == 0}">
                                    <div class="d-flex w-100 justify-content-center">
                                        <h3>
                                            <font color="red">
                                            The subject is being updated, please come back later.
                                            </font>
                                        </h3>
                                    </div>
                                    <div class="d-flex w-100 justify-content-center mt-5">
                                        <a href="home" class="text-muted" style="font-size: 20px;">Click here to go back home</a>
                                    </div>
                                </c:if>
                            </c:if>
                            <c:if test="${subject == null}">
                                <div class="d-flex flex-row w-100 justify-content-center">
                                    <h3>
                                        <font color="red">
                                        Subject is not existed
                                        </font>
                                    </h3>
                                </div>
                                <div class="d-flex w-100 justify-content-center mt-5">
                                    <a href="home" class="text-muted" style="font-size: 20px;">Click here to go back home</a>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://momentjs.com/downloads/moment.js"></script>
        <script type="text/javascript">
            const format1 = "dddd, MMM Do YYYY";
            var sdate = document.getElementsByClassName("date");
            var formatDate = document.getElementsByClassName("format-date");
            for (var i = 0; i < sdate.length; i++) {
                var ssdate = sdate[i].value;
                ssdate = ssdate.substring(0, ssdate.lastIndexOf('.'));
                var datetime1 = moment(ssdate).format(format1);

                formatDate[i].innerHTML = datetime1;
            }
        </script>
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
