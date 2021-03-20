<%-- 
    Document   : viewQuiz
    Created on : Jan 26, 2021, 10:53:41 PM
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
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css" rel="stylesheet" type="text/css">
        <link href="assets/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="assets/css/myCSS.css" rel="stylesheet" type="text/css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page - View Quiz</title>
    </head>
    <body>
        <c:if test="${sessionScope.USER == null}">
            <c:redirect url="login" />
        </c:if>
        <c:if test="${sessionScope.ROLE ne 'Admin'}">
            <c:redirect url="home" />
        </c:if>
        <style>
            .table-responsive {
                display: table;
            }

            .table td, th {
                text-align: center;
                vertical-align: middle;
            }

            #datepicker1, #datepicker2{
                width:180px;
            }
            #datepicker1 > span:hover, 
            #datepicker2 > span:hover{
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
                <ul class="list-unstyled components">
                    <c:set var="dto" value="${requestScope.SUBJECT_INFO}"/>
                    <c:set var="subjectList" value="${requestScope.SUBJECT_LIST}"/>
                    <li><a href="home">Home</a></li>
                    <li><a href="create-subject">Create Subject</a></li>
                    <li><a href="create-question">Create Question</a></li>
                    <li> <a href="#submenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Subjects</a>
                        <ul class="list-unstyled" id="submenu">
                            <c:forEach var="subject" items="${subjectList}">
                                <c:url var="viewQuestionLink" value="view-question">
                                    <c:param name="id" value="${subject.subjectId}"/>
                                </c:url>
                                <li>
                                    <div class="d-flex w-100 justify-content-between list-subject" style="background: #318fb5;">
                                        <a href="${viewQuestionLink}">${subject.subjectName}</a>
                                        <a href="#submenu${subject.subjectId}" data-toggle="collapse" id="link-quiz" aria-expanded="false" class="dropdown-toggle dropdown-toggle-split"></a>
                                    </div>
                                    <ul class="<c:if test="${empty dto}">
                                        collapse
                                        </c:if>
                                        <c:if test="${not empty dto}">
                                            <c:if test="${subject.subjectId != dto.subjectId}">
                                                collapse
                                            </c:if>
                                        </c:if> list-unstyled" id="submenu${subject.subjectId}">
                                        <li>
                                            <c:url var="updateQuizLink" value="update-quiz">
                                                <c:param name="id" value="${subject.subjectId}"/>
                                            </c:url>
                                            <a href="${updateQuizLink}" style="
                                               <c:if test="${not empty dto}">
                                                   <c:if test="${subject.subjectId == dto.subjectId}">
                                                       background: #002b3d;
                                                   </c:if>
                                               </c:if>">Update Quiz</a>
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
                        <c:if test="${not empty dto}">
                            <h4 class="my-3 mb-5" style="color: #318fb5;">Update quiz for ${dto.subjectName}</h4>
                            <table class="table" border="1">
                                <thead style="background: #318fb5; color: #fff;">
                                    <tr><th>Subject ID</th>
                                        <th>Subjects Name</th>
                                        <th>Total Questions</th>
                                        <th>Time Limit</th>
                                        <th>Quiz Question Quantity</th>
                                        <th>Grade</th>
                                        <th>Start Date</th>
                                        <th>End Date</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <form action="update-quiz" method="POST">
                                    <tr>
                                        <td>
                                            ${dto.subjectId}
                                            <input type="hidden" name="txtSubjectId" value="${dto.subjectId}"/>
                                        </td>
                                        <td>
                                            <input type="text" name="txtSubjectName" value="${dto.subjectName}" />
                                        </td>
                                        <td>
                                            <input class="form-control" type="text" min="1" pattern="\d+" name="txtTotalQuestion" 
                                                   value="${dto.totalQuestion}" 
                                                   required="required" oninvalid="setCustomValidity('Please enter only positive number value')" oninput="setCustomValidity('')"/>
                                        </td>
                                        <td>
                                            <input class="form-control" type="text" min="1" placeholder="In minutes. . ." pattern="\d+" name="txtTimeLimit" 
                                                   value="${dto.timeLimit}" 
                                                   required="required" oninvalid="setCustomValidity('Please enter only positive number value')" oninput="setCustomValidity('')"/>
                                        </td>
                                        <td>
                                            <input class="form-control" type="text" min="1" pattern="\d+" name="txtQuizQuestionQuantity" 
                                                   value="${dto.quizQuestionQuantity}" 
                                                   required="required" oninvalid="setCustomValidity('Please enter only positive number value')" oninput="setCustomValidity('')"/>
                                        </td>
                                        <td>
                                            <input class="form-control" type="text" pattern="^[0-9]*[.]?[0-9]*$" name="txtGrade" 
                                                   value="${dto.grade}" 
                                                   required="required" oninvalid="setCustomValidity('Please enter only positive number value')" oninput="setCustomValidity('')"/>
                                        </td>
                                        <td>
                                            <input type="hidden" id="start-date" value="${dto.startDate}"/>
                                            <div class="input-append date d-flex align-items-center" id="datepicker1" data-date-format="dd-mm-yyyy">
                                                <input name="txtStartDate" class="form-control" 
                                                       value="${dto.startDate}" 
                                                       type="text" readonly="">
                                                <span class="add-on ml-2"><i class="fa fa-calendar"></i></span>
                                            </div>
                                        </td>
                                        <td>
                                            <input type="hidden" id="end-date" value="${dto.endDate}"/>
                                            <div class="input-append date d-flex align-items-center" id="datepicker2" data-date-format="dd-mm-yyyy">
                                                <input name="txtEndDate" class="form-control" 
                                                       value="${dto.endDate}" 
                                                       type="text" readonly="">
                                                <span class="add-on ml-2"><i class="fa fa-calendar"></i></span>
                                            </div>
                                        </td>
                                        <td>
                                            <input type="submit" value="Update Quiz" class="btn btn-info"/>
                                        </td>
                                    </tr>
                                </form>
                            </c:if>
                            <c:if test="${empty dto}">
                                <div class="d-flex w-100 justify-content-center">
                                    <h3>
                                        <font color="red">
                                        Please create at least one question to update quiz for this subject
                                        </font>
                                    </h3>
                                </div>
                                <div class="d-flex w-100 justify-content-center mt-5">
                                    <a href="create-question" class="text-muted" style="font-size: 20px;">Click here to create question</a>
                                </div>
                            </c:if>
                            </tbody>
                        </table>
                        <div class="mt-5">
                            <div class="my-2">
                                <c:if test="${requestScope.UPDATE_QUIZ_FAIL != null}">
                                    <font color="red">
                                    ${requestScope.UPDATE_QUIZ_FAIL}
                                    </font>
                                </c:if>
                            </div>
                            <div class="my-2">
                                <c:if test="${requestScope.UPDATE_QUIZ_SUCCESS != null}">
                                    <font color="green">
                                    ${requestScope.UPDATE_QUIZ_SUCCESS}
                                    </font>
                                </c:if>
                            </div>
                            <c:set var="error" value="${requestScope.UPDATE_QUIZ_ERROR}"/>
                            <div class="my-2">
                                <c:if test="${error != null}">
                                    <font color="red">
                                    Error:
                                    </font>
                                </c:if>
                            </div>
                            <div class="my-2">
                                <c:if test="${not empty error.zeroQuestionQuantity}">
                                    <font color="red">
                                    ${error.zeroQuestionQuantity}
                                    </font>
                                </c:if>
                            </div>
                            <div class="my-2">
                                <c:if test="${not empty error.zeroTotalQuestion}">
                                    <font color="red">
                                    ${error.zeroTotalQuestion}
                                    </font>
                                </c:if>
                            </div>
                            <div class="my-2">
                                <c:if test="${not empty error.zeroMinute}">
                                    <font color="red">
                                    ${error.zeroMinute}
                                    </font>
                                </c:if>
                            </div>
                            <div class="my-2">
                                <c:if test="${not empty error.startDateAfterEndDate}">
                                    <font color="red">
                                    ${error.startDateAfterEndDate}
                                    </font>
                                </c:if>
                            </div>
                            <div class="my-2">
                                <c:if test="${not empty error.emptyStartDate}">
                                    <font color="red">
                                    ${error.emptyStartDate}
                                    </font>
                                </c:if>
                            </div>
                            <div class="my-2">
                                <c:if test="${not empty error.emptyEndDate}">
                                    <font color="red">
                                    ${error.emptyEndDate}
                                    </font>
                                </c:if>
                            </div>
                            <div class="my-2">
                                <c:if test="${not empty error.notEnoughQuestion}">
                                    <font color="red">
                                    ${error.notEnoughQuestion}
                                    </font>
                                </c:if>
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
        <script type="text/javascript">
            $(function () {
                var startDate = document.getElementById("start-date").value;
                var endDate = document.getElementById("end-date").value;
                startDate = startDate.toString().substring(0, startDate.toString().indexOf(" "));
                endDate = endDate.toString().substring(0, endDate.toString().indexOf(" "));
                var sDate = new Date(startDate);
                var eDate = new Date(endDate);

                $("#datepicker1").datepicker({
                    autoclose: true,
                    todayHighlight: true
                }).datepicker('update', sDate);

                $("#datepicker2").datepicker({
                    autoclose: true,
                    todayHighlight: true
                }).datepicker('update', eDate);
            });
        </script>
    </body>
</html>
