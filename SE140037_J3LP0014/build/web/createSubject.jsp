<%-- 
    Document   : createSubject
    Created on : Feb 16, 2021, 9:44:45 PM
    Author     : TrongNS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <link href="assets/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="assets/css/myCSS.css" rel="stylesheet" type="text/css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page - Create Subject</title>
    </head>
    <body>
        <c:if test="${sessionScope.USER == null}">
            <c:redirect url="login" />
        </c:if>
        <c:if test="${sessionScope.ROLE ne 'Admin'}">
            <c:redirect url="home" />
        </c:if>
        <style type="text/css">
            radio {
                cursor: pointer;
            }
        </style>
        <div class="wrapper">
            <nav id="sidebar">
                <div class="sidebar-header">
                    <div class="logo-image d-flex justify-content-center align-items-center w-100 mt-3">
                        <img src="image/logo.png" class="img-fluid">
                    </div>
                </div>
                <hr>
                <c:set var="subject" value="${requestScope.SUBJECT_INFO}"/>
                <ul class="list-unstyled components">
                    <c:set var="subjectList" value="${requestScope.SUBJECT_LIST}"/>
                    <li><a href="home">Home</a></li>
                    <li><a href="create-subject" style="background: #002b3d;">Create Subject</a></li>
                    <li><a href="create-question">Create Question</a></li>
                    <li> <a href="#submenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Subjects</a>
                        <ul class="collapse list-unstyled" id="submenu">
                            <c:forEach var="dto" items="${subjectList}">
                                <c:url var="viewQuestionLink" value="view-question">
                                    <c:param name="id" value="${dto.subjectId}"/>
                                </c:url>
                                <li>
                                    <div class="d-flex w-100 justify-content-between list-subject" style="background: #318fb5;">
                                        <a href="${viewQuestionLink}">${dto.subjectName}</a>
                                        <a href="#submenu${dto.subjectId}" data-toggle="collapse" id="link-quiz" aria-expanded="false" class="dropdown-toggle dropdown-toggle-split"></a>
                                    </div>
                                    <ul class="collapse list-unstyled" id="submenu${dto.subjectId}">
                                        <li>
                                            <c:url var="updateQuizLink" value="update-quiz">
                                                <c:param name="id" value="${dto.subjectId}"/>
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
                        <div class="d-flex w-100 justify-content-center mt-2 mb-4">
                            <h4 style="color: #318fb5;">Create new subject</h4>
                        </div>
                        <div class="my-2 mx-2">
                            <c:if test="${requestScope.CREATE_SUBJECT_FAIL != null}">
                                <font color="red">
                                ${requestScope.CREATE_SUBJECT_FAIL}
                                </font>
                            </c:if>
                            <c:if test="${requestScope.CREATE_SUBJECT_SUCCESS != null}">
                                <font color="green">
                                ${requestScope.CREATE_SUBJECT_SUCCESS}
                                </font>
                            </c:if>
                        </div>
                        <div class="QA">
                            <div class="Q mx-2">
                                <form action="create-subject" method="POST">
                                    <c:set var="error" value="${requestScope.CREATE_SUBJECT_ERROR}"/>
                                    <div class="form-group mb-3">
                                        <label for="txtSubjectId">Subject ID:<font color="red">*</font></label>
                                        <input type="text" maxlength="10" class="form-control" name="txtSubjectId" value="${param.txtSubjectId}" required="required"/>
                                        <div class="my-2">
                                            <c:if test="${not empty error}">
                                                <c:if test="${fn:contains(error, 'Duplicate')}">
                                                    <font color="red">
                                                    ${error}
                                                    </font>
                                                </c:if>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="form-group mb-3">
                                        <label for="txtSubjectName">Subject Name:<font color="red">*</font></label>
                                        <textarea class="form-control" name="txtSubjectName" required="required" style="height: 80px;">${param.txtSubjectName}</textarea>
                                        <div class="my-2">
                                            <c:if test="${not empty error}">
                                                <c:if test="${fn:contains(error, 'existed')}">
                                                    <font color="red">
                                                    ${error}
                                                    </font>
                                                </c:if>
                                            </c:if>
                                        </div>
                                    </div>
                                    <hr class="my-3">
                                    <div class="w-100 d-flex flex-row justify-content-center align-items-center mb-5">
                                        <input type="submit" value="Create Subject" class="btn btn-info"/>
                                        <input type="reset" class="btn btn-secondary ml-2" value="Reset" />
                                    </div>
                                </form>
                            </div>
                        </div>
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
