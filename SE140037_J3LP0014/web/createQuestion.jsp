<%-- 
    Document   : createQuestion
    Created on : Jan 24, 2021, 10:10:50 PM
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
        <title>Admin Page - Create Question</title>
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
                    <li><a href="create-subject">Create Subject</a></li>
                    <li><a href="create-question" style="background: #002b3d;">Create Question</a></li>
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
                <div class="content mb-3">
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
                            <h4 style="color: #318fb5;">Create new question for subject ${subject.subjectId}</h4>
                        </div>
                        <div class="my-2 mx-2">
                            <c:if test="${requestScope.CREATE_QUEST_FAIL != null}">
                                <font color="red">
                                ${requestScope.CREATE_QUEST_FAIL}
                                </font>
                            </c:if>
                            <c:if test="${requestScope.CREATE_QUEST_SUCCESS != null}">
                                <font color="green">
                                ${requestScope.CREATE_QUEST_SUCCESS}
                                </font>
                            </c:if>
                        </div>
                        <div class="QA">
                            <div class="Q mx-2">
                                <form action="create-question" method="POST">
                                    <c:set var="error" value="${requestScope.CREATE_QUEST_ERROR}"/>
                                    <div class="form-group mb-3">
                                        <label for="txtQuestionId">Question ID:<font color="red">*</font></label>
                                        <input type="text" maxlength="10" class="form-control" name="txtQuestionId" value="${param.txtQuestionId}" required="required"/>
                                        <div class="my-2">
                                            <c:if test="${not empty error.duplicateQuestion}">
                                                <c:if test="${fn:contains(error.duplicateQuestion, 'Duplicate')}">
                                                    <font color="red">
                                                    ${error.duplicateQuestion}
                                                    </font>
                                                </c:if>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="form-group mb-3">
                                        <label for="txtQuestionContent">Question Content:<font color="red">*</font></label>
                                        <textarea class="form-control" name="txtQuestionContent" required="required" style="height: 80px;">${param.txtQuestionContent}</textarea>
                                        <div class="my-2">
                                            <c:if test="${not empty error.duplicateQuestion}">
                                                <c:if test="${fn:contains(error.duplicateQuestion, 'existed')}">
                                                    <font color="red">
                                                    ${error.duplicateQuestion}
                                                    </font>
                                                </c:if>
                                            </c:if>
                                        </div>
                                    </div>
                                    <c:if test="${not empty error.emptyCorrectAnswer}">
                                        <font color="red">
                                        ${error.emptyCorrectAnswer}
                                        </font>
                                    </c:if>   
                                    <p class="text-info">Click radio to set the answer to correct</p>
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">
                                                <p class="m-0 mr-4">Answer 1:<font color="red">*</font></p>
                                                <input type="radio" name="rdCorrectAnswer" value="answer1" style="cursor: pointer;" <c:if test="${param.rdCorrectAnswer eq 'answer1'}">checked="checked"</c:if>/>
                                                </div>
                                            </div>
                                            <textarea name="txtAnswer1" class="form-control" required="required" style="height: 80px;">${param.txtAnswer1}</textarea>
                                    </div>
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">
                                                <p class="m-0 mr-4">Answer 2:<font color="red">*</font></p>
                                                <input type="radio" name="rdCorrectAnswer" value="answer2" style="cursor: pointer;" <c:if test="${param.rdCorrectAnswer eq 'answer2'}">checked="checked"</c:if>/>
                                                </div>
                                            </div>
                                            <textarea name="txtAnswer2" class="form-control" required="required" style="height: 80px;">${param.txtAnswer2}</textarea>
                                    </div>
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">
                                                <p class="m-0 mr-4">Answer 3:<font color="red">*</font></p>
                                                <input type="radio" name="rdCorrectAnswer"  value="answer3" style="cursor: pointer;" <c:if test="${param.rdCorrectAnswer eq 'answer3'}">checked="checked"</c:if>/>
                                                </div>
                                            </div>
                                            <textarea name="txtAnswer3" class="form-control" required="required" style="height: 80px;">${param.txtAnswer3}</textarea>
                                    </div>
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text">
                                                <p class="m-0 mr-4">Answer 4:<font color="red">*</font></p>
                                                <input type="radio" name="rdCorrectAnswer" value="answer4" style="cursor: pointer;" <c:if test="${param.rdCorrectAnswer eq 'answer4'}">checked="checked"</c:if>/>
                                                </div>
                                            </div>
                                            <textarea name="txtAnswer4" class="form-control" required="required" style="height: 80px;">${param.txtAnswer4}</textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="#subject">
                                            <b>Subject:</b>
                                        </label>
                                        <select id="subject" name="cbSubject" class="form-control mb-3">
                                            <option value="">--Select subject--</option>
                                            <c:forEach items="${requestScope.SUBJECT_LIST}" var="dto">
                                                <option value="${dto.subjectId}" <c:if test="${param.cbSubject == dto.subjectId}">selected</c:if>>${dto.subjectId} - ${dto.subjectName}</option>
                                            </c:forEach>
                                        </select>
                                        <c:if test="${not empty error.emptySubject}">
                                            <font color="red">
                                            ${error.emptySubject}
                                            </font>
                                        </c:if>
                                    </div>
                                    <hr class="my-3">
                                    <div class="w-100 d-flex flex-row justify-content-center align-items-center mb-5">
                                        <input type="submit" value="Create Question" class="btn btn-info"/>
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
