<%-- 
    Document   : subject
    Created on : Jan 23, 2021, 10:21:23 PM
    Author     : TrongNS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <link href="assets/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="assets/css/myCSS.css" rel="stylesheet" type="text/css">
        <title>Admin Page - View Subject</title>
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
                <c:set var="subject" value="${requestScope.SUBJECT_INFO}"/>
                <ul class="list-unstyled components">
                    <c:set var="subjectList" value="${requestScope.SUBJECT_LIST}"/>
                    <li><a href="home">Home</a></li>
                    <li><a href="create-subject">Create Subject</a></li>
                    <li><a href="create-question">Create Question</a></li>
                    <li> <a href="#submenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Subjects</a>
                        <ul class="list-unstyled" id="submenu">
                            <c:forEach var="dto" items="${subjectList}">
                                <c:url var="viewQuestionLink" value="view-question">
                                    <c:param name="id" value="${dto.subjectId}"/>
                                </c:url>
                                <li>
                                    <div class="d-flex w-100 justify-content-between list-subject" 
                                         <c:if test="${not empty subject}">
                                             <c:if test="${subject.subjectId == dto.subjectId}">
                                                 style="background: #002b3d;"
                                             </c:if>
                                             <c:if test="${subject.subjectId != dto.subjectId}">
                                                 style="background: #318fb5;"
                                             </c:if>
                                         </c:if>
                                         <c:if test="${empty subject}">
                                             style="background: #318fb5;"
                                         </c:if>
                                         >
                                        <a href="${viewQuestionLink}" 
                                           <c:if test="${not empty subject}">
                                               <c:if test="${subject.subjectId == dto.subjectId}">
                                                   style="background: #002b3d;"
                                               </c:if>
                                           </c:if>
                                           >${dto.subjectName}</a>
                                        <a href="#submenu${dto.subjectId}" data-toggle="collapse" id="link-quiz" aria-expanded="false" class="dropdown-toggle dropdown-toggle-split" 
                                           <c:if test="${not empty subject}">
                                               <c:if test="${subject.subjectId == dto.subjectId}">
                                                   style="background: #002b3d;"
                                               </c:if>
                                           </c:if>></a>
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
            <div class="container-fluid" style="padding-bottom: 6rem;">
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
                        <c:if test="${not empty subject}">
                            <div class="container">
                                <h3 style="color: #318fb5;">${subject.subjectId} - ${subject.subjectName}</h3>
                                <form action="search" class="d-flex flex-row w-100 justify-content-between text-center">
                                    <div class="d-flex flex-row w-100 justify-content-center">
                                        <div class="form-group mb-1 w-100">
                                            <i class="fa fa-search position-absolute" style="padding: 10px; min-width: 40px; "></i>
                                            <input type="text" name="txtSearchQuestion" value="${param.txtSearchQuestion}" class="form-control w-100 align-content-center" style="padding-left: 35px;"placeholder="Search Question. . .">
                                        </div>
                                        <div class="form-group ml-2 w-25">
                                            <select name="cbStatus" class="form-control">
                                                <option value="">--Select Status--</option>
                                                <option value="Activate" <c:if test="${param.cbStatus eq 'Activate'}">selected</c:if>>Activate</option>
                                                <option value="Deactivate" <c:if test="${param.cbStatus eq 'Deactivate'}">selected</c:if>>Deactivate</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="ml-0 pl-0">
                                            <input type="hidden" name="pageNo" value="${requestScope.PAGE_NO}" />
                                        <input type="hidden" name="id" value="${subject.subjectId}" />
                                        <input type="hidden" name="pageSize" value="${requestScope.PAGE_SIZE}"/>
                                        <input type="submit" class="btn btn-success mr-1" value="Search" style="width: 100px;"/>
                                        <input type="reset" class="btn btn-secondary mt-1" value="Reset" style="width: 100px;"/>
                                    </div>
                                </form>
                            </div>
                            <div class="my-2">
                                <c:if test="${not empty requestScope.UPDATE_QUEST_SUCCESS}">
                                    <font color="green">
                                    ${requestScope.UPDATE_QUEST_SUCCESS}
                                    </font>
                                </c:if>
                                <c:if test="${not empty requestScope.UPDATE_QUEST_FAIL}">
                                    <font color="red">
                                    ${requestScope.UPDATE_QUEST_FAIL}
                                    </font>
                                </c:if>
                                <c:if test="${not empty requestScope.UPDATE_QUEST_ERROR}">
                                    <font color="red">
                                    Error:<br>
                                    ${requestScope.UPDATE_QUEST_ERROR}
                                    </font>
                                </c:if>
                                <c:if test="${not empty requestScope.DELETE_QUEST_SUCCESS}">
                                    <font color="green">
                                    ${requestScope.DELETE_QUEST_SUCCESS}
                                    </font>
                                </c:if>
                                <c:if test="${not empty requestScope.DELETE_QUEST_FAIL}">
                                    <font color="red">
                                    ${requestScope.DELETE_QUEST_FAIL}
                                    </font>
                                </c:if>
                            </div>
                            <div class="d-flex justify-content-end">
                                <form action="search">
                                    <c:set var="pageSize" value="${requestScope.PAGE_SIZE}"/>
                                    <select name="pageSize">
                                        <option value="all">Always show all</option>
                                        <c:forEach var="size" begin="1" end="20" step="1">
                                            <option value="${size}" class="form-control" <c:if test="${pageSize == size}">selected="selected"</c:if>>${size}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" name="txtSearchQuestion" value="${param.txtSearchQuestion}"/>
                                    <input type="hidden" name="cbStatus" value="${param.cbStatus}"/>
                                    <input type="hidden" name="id" value="${subject.subjectId}" />
                                    <input type="submit" class="btn btn-outline-primary ml-2" name="action" value="Change size">
                                </form>
                            </div>
                            <c:set var="questionList" value="${requestScope.QUESTION_LIST}"/>
                            <c:if test="${not empty questionList}">
                                <c:forEach var="question" items="${questionList}" varStatus="counter">
                                    <c:set var="answers" value="${question.answerList}"/>
                                    <div class="QA" style="
                                         <c:if test="${question.status eq 'Deactivate'}">border: 1px solid red;</c:if>
                                         <c:if test="${question.status eq 'Activate'}">border: 1px solid green;</c:if>">
                                             <div class="Q">
                                                 <form action="update" method="POST">
                                                     <p class="text-info mx-2" style="font-size: 20px;">Q${counter.count}-${question.questionId}.</p>
                                                 <div class="w-100 d-flex flex-column p-2">
                                                     <div class="form-group mb-3">
                                                         <input type="hidden" name="txtQuestionId" value="${question.questionId}" />
                                                         <label for="txtQuestionContent_${question.questionId}">Question Content:<font color="red">*</font></label>
                                                             <c:set var="paramContent" value="txtQuestionContent_${question.questionId}"/>
                                                         <textarea class="form-control" name="txtQuestionContent_${question.questionId}" required="required" style="height: 80px;"><c:if test="${not empty question.content}">${question.content}</c:if><c:if test="${empty question.content}">${param[paramContent]}</c:if></textarea>
                                                         </div>
                                                     </div>
                                                     <p class="text-info mx-2">Click radio to set the answer to correct</p>
                                                     <div class="w-100 d-flex flex-column align-items-center p-2">
                                                     <c:if test="${not empty answers}">
                                                         <c:forEach var="ans" items="${answers}" varStatus="counter">
                                                             <div class="input-group mb-3">
                                                                 <div class="input-group-prepend">
                                                                     <div class="input-group-text">
                                                                         <p class="m-0 mr-4">Answer ${counter.count}:<font color="red">*</font></p>
                                                                         <input type="radio" name="rdCorrectAnswer_${question.questionId}" value="answer${counter.count}" <c:if test="${ans.correct == true}">checked="checked"</c:if> style="cursor: pointer;" />
                                                                         </div>
                                                                     </div>
                                                                     <textarea class="form-control" name="txtAnswer${counter.count}_${question.questionId}" required="required" style="height: 80px;">${ans.content}</textarea>
                                                             </div>
                                                         </c:forEach>
                                                     </c:if>
                                                     <c:if test="${empty answers}">
                                                         <div class="input-group mb-3">
                                                             <div class="input-group-prepend">
                                                                 <div class="input-group-text">
                                                                     <p class="m-0 mr-4">Answer 1:<font color="red">*</font></p>
                                                                     <input type="radio" name="rdCorrectAnswer_${question.questionId}" value="answer1" style="cursor: pointer;" />
                                                                 </div>
                                                             </div>
                                                             <c:set var="paramAnswer1" value="txtAnswer1_${question.questionId}"/>
                                                             <textarea class="form-control" name="txtAnswer1_${question.questionId}" required="required" style="height: 80px;">${param[paramAnswer1]}</textarea>
                                                         </div>
                                                         <div class="input-group mb-3">
                                                             <div class="input-group-prepend">
                                                                 <div class="input-group-text">
                                                                     <p class="m-0 mr-4">Answer 2:<font color="red">*</font></p>
                                                                     <input type="radio" name="rdCorrectAnswer_${question.questionId}" value="answer2" style="cursor: pointer;" />
                                                                 </div>
                                                             </div>
                                                             <c:set var="paramAnswer2" value="txtAnswer2_${question.questionId}"/>
                                                             <textarea class="form-control" name="txtAnswer2_${question.questionId}" required="required" style="height: 80px;">${param[paramAnswer2]}</textarea>
                                                         </div>
                                                         <div class="input-group mb-3">
                                                             <div class="input-group-prepend">
                                                                 <div class="input-group-text">
                                                                     <p class="m-0 mr-4">Answer 3:<font color="red">*</font></p>
                                                                     <input type="radio" name="rdCorrectAnswer_${question.questionId}" value="answer3" style="cursor: pointer;" />
                                                                 </div>
                                                             </div>
                                                             <c:set var="paramAnswer3" value="txtAnswer3_${question.questionId}"/>
                                                             <textarea class="form-control" name="txtAnswer3_${question.questionId}" required="required" style="height: 80px;">${param[paramAnswer3]}</textarea>
                                                         </div>
                                                         <div class="input-group mb-3">
                                                             <div class="input-group-prepend">
                                                                 <div class="input-group-text">
                                                                     <p class="m-0 mr-4">Answer 4:<font color="red">*</font></p>
                                                                     <input type="radio" name="rdCorrectAnswer_${question.questionId}" value="answer4" style="cursor: pointer;" />
                                                                 </div>
                                                             </div>
                                                             <c:set var="paramAnswer4" value="txtAnswer4_${question.questionId}"/>
                                                             <textarea class="form-control" name="txtAnswer4_${question.questionId}" required="required" style="height: 80px;">${param[paramAnswer4]}</textarea>
                                                         </div>
                                                     </c:if>
                                                 </div>
                                                 <div>
                                                     <p class="text-info mx-2">Check the status to make question activate</p>
                                                     <span class="mx-2"><b>Status:</b></span> <input type="checkbox" name="chkStatus" class="chk-status" value="${question.status}" <c:if test="${question.status eq 'Activate'}">checked="checked"</c:if> />
                                                     </div>
                                                     <div class="d-flex justify-content-end">
                                                         <input type="hidden" name="txtSearchQuestion" value="${param.txtSearchQuestion}" />
                                                     <input type="hidden" name="pageNo" value="${requestScope.PAGE_NO}" />
                                                     <input type="hidden" name="pageSize" value="${requestScope.PAGE_SIZE}" />
                                                     <input type="hidden" name="id" value="${subject.subjectId}" />
                                                     <input type="hidden" name="cbStatus" value="${param.cbStatus}" />
                                                     <input type="submit" class="btn btn-primary mb-3 mr-1" name="action" value="Update"/>

                                                     <c:url var="deleteLink" value="delete">
                                                         <c:param name="txtSearchQuestion" value="${param.txtSearchQuestion}" />
                                                         <c:param name="pageNo" value="${requestScope.PAGE_NO}" />
                                                         <c:param name="pageSize" value="${requestScope.PAGE_SIZE}" />
                                                         <c:param name="id" value="${subject.subjectId}" />
                                                         <c:param name="cbStatus" value="${param.cbStatus}" />
                                                         <c:param name="txtQuestionId" value="${question.questionId}"/>
                                                         <c:param name="action" value="Delete"/>
                                                     </c:url>
                                                     <a href="#" class="btn btn-danger mb-3 mr-1" data-abc="true" data-href="${deleteLink}" data-toggle="modal" data-target="#confirm-delete" <c:if test="${question.status ne 'Activate'}">style="pointer-events: none;"</c:if>>Delete</a>
                                                         <!-- Confirm delete product modal -->
                                                         <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                                             <div class="modal-dialog">
                                                                 <div class="modal-content">
                                                                     <div class="modal-header">
                                                                         <h5 class="modal-title w-100 text-center text-danger">Confirm delete question</h5>
                                                                     </div>
                                                                     <div class="modal-body text-center">
                                                                         Are you sure want to delete this question?
                                                                     </div>
                                                                     <div class="modal-footer d-flex justify-content-center">
                                                                         <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                                         <a class="btn btn-danger btn-ok">Delete</a>
                                                                     </div>
                                                                 </div>
                                                             </div>
                                                         </div>
                                                     </div>
                                                 </form>
                                             </div>
                                         </div>
                                </c:forEach>

                                <div class="d-flex w-100 justify-content-center mt-2 mb-4">
                                    <c:if test="${not empty requestScope.PAGE_NO}">
                                        <c:set var="pageNo" value="${requestScope.PAGE_NO}" />
                                        <c:if test="${pageNo != 1}">
                                            <c:url var="prevLink" value="search">
                                                <c:param name="txtSearchQuestion" value="${param.txtSearchQuestion}"/>
                                                <c:param name="pageNo" value="${pageNo}"/>
                                                <c:param name="pageSize" value="${requestScope.PAGE_SIZE}"/>
                                                <c:param name="cbStatus" value="${param.cbStatus}"/>
                                                <c:param name="id" value="${subject.subjectId}" />
                                                <c:param name="action" value="Prev" />
                                            </c:url>
                                            <a href="${prevLink}" class="btn btn-primary">Prev</a>
                                        </c:if>
                                        <input type="number" class="form-control m-0 mx-2 text-center" min="1" value="${pageNo}" style="width: 50px;" readonly="readonly"/>
                                        <c:if test="${empty requestScope.LAST_LIST}">
                                            <c:url var="nextLink" value="search">
                                                <c:param name="txtSearchQuestion" value="${param.txtSearchQuestion}"/>
                                                <c:param name="pageNo" value="${pageNo}"/>
                                                <c:param name="pageSize" value="${requestScope.PAGE_SIZE}"/>
                                                <c:param name="cbStatus" value="${param.cbStatus}"/>
                                                <c:param name="id" value="${subject.subjectId}" />
                                                <c:param name="action" value="Next" />
                                            </c:url>
                                            <a href="${nextLink}" class="btn btn-primary">Next</a>
                                        </c:if>
                                    </c:if>
                                </div>
                            </c:if>
                            <c:if test="${empty questionList}">
                                <div class="d-flex flex-row w-100 justify-content-center mt-3">
                                    <font color="red">
                                    No Result was found.
                                    </font>
                                </div>
                            </c:if>
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
            </c:if>
            <c:if test="${empty subject}">
                <div class="d-flex flex-row w-100 justify-content-center">
                    <h3>
                        <font color="red">
                        This subject doesn't have any questions, please create at least one
                        </font>
                    </h3>
                </div>
                <div class="d-flex w-100 justify-content-center mt-5">
                    <a href="create-question" class="text-muted" style="font-size: 20px;">Click here to create question</a>
                </div>
            </c:if>
        </div>

        <script type="text/javascript">
            $(document).ready(function () {
                $('#confirm-delete').on('show.bs.modal', function (e) {
                    $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
                });
            });
        </script>
    </body>
</html>
