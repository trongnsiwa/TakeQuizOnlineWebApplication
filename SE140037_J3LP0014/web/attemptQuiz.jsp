<%-- 
    Document   : startQuiz
    Created on : Jan 27, 2021, 1:04:13 PM
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
        <title>Student Page - Attempt Quiz</title>
        <style>
            div#question-list {
                overflow-x: hidden; 
                overflow-y: scroll; 
                height: 780px;
                padding: 7px;
                border: 2px solid #005086;
                border-radius: 10px;
            }

            button{
                z-index: 2;
            }
        </style>
    </head>
    <body onload="quizTimer()">
        <c:if test="${sessionScope.USER == null}">
            <c:redirect url="login" />
        </c:if>
        <c:if test="${sessionScope.ROLE ne 'Student'}">
            <c:redirect url="home" />
        </c:if>
        <c:set var="subject" value="${requestScope.SUBJECT_INFO}"/>
        <c:url var="subjecLink" value="view">
            <c:param name="id" value="${subject.subjectId}"/>
        </c:url>
        <c:if test="${requestScope.QUESTION_LIST == null}">
            <c:redirect url="${subjectLink}"/>
        </c:if>
        <div class="content ml-0 p-0">
            <input type="hidden" id="subject-id" value="${subject.subjectId}"/>
            <input type="hidden" id="user-id" value="${sessionScope.USER}"/>
            <input type="hidden" id="time-limit" value="${subject.timeLimit}"/>

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
            <div class="content-wrapper mt-0">
                <c:set var="questionQuantity" value="${subject.quizQuestionQuantity}"/>
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-3">
                            <div class="card border-primary">
                                <div class="card-body">
                                    <p><b>Subject:</b> ${subject.subjectId} - ${subject.subjectName}</p>
                                    <input type="hidden" class="date" value="${subject.startDate}"/>
                                    <p><b>Start Date:</b> <span class="format-date">${subject.startDate}</span></p>
                                    <input type="hidden" class="date" value="${subject.endDate}"/>
                                    <p><b>End Date:</b> <span class="format-date">${subject.endDate}</span></p>
                                    <hr class="my-2">
                                    <p class="font-weight-bold">List Of Questions:</p>
                                    <div class="card my-2 border-primary">
                                        <div class="card-body">
                                            <table class="table table-borderless">
                                                <tbody>
                                                    <c:set var="end" value="${questionQuantity - 1}"/>
                                                    <c:set var="step" value="${1}"/>
                                                    <c:if test="${questionQuantity > 4}">
                                                        <c:set var="end" value="${questionQuantity}"/>
                                                        <c:set var="step" value="${4}"/>
                                                    </c:if>
                                                    <c:forEach var="index" begin="0" end="${end}" step="${step}">
                                                        <tr>
                                                            <c:if test="${questionQuantity > 4}">
                                                                <c:forEach var="col" begin="1" end="4">
                                                                    <td class="border border-primary text-center p-0 m-0">
                                                                        <c:if test="${(index + col) <= questionQuantity}">
                                                                            <a id="${index + col}" style="font-size: 19px;" href="#Q${index + col}">${index + col}</a>
                                                                        </c:if>
                                                                    </td>
                                                                </c:forEach>
                                                            </c:if>
                                                            <c:if test="${questionQuantity <= 4}">
                                                                <td class="border border-primary text-center p-0 m-0">
                                                                    <a id="${index + 1}" style="font-size: 19px;" href="#Q${index + 1}">${index + 1}</a>
                                                                </td>
                                                            </c:if>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <p>Time Remaining: <br/><span id="show-time" style="font-size: 25px; color: #007bff;"></span></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-9" id="question-list">
                            <c:set var="questionList" value="${requestScope.QUESTION_LIST}"/>
                            <form action="submit" method="POST" name="quizForm">
                                <c:forEach var="question" items="${questionList}" varStatus="counter">
                                    <c:set var="answers" value="${question.answerList}"/>
                                    <div class="QA mt-0 bg-white border-primary">
                                        <div class="Q">
                                            <div class="d-flex flex-column p-2">
                                                <div class="form-group mb-3">
                                                    <input type="hidden" name="txtQuestionId" value="${question.questionId}" />
                                                    <p id="Q${counter.count}"><b>Q${counter.count}. ${question.content}</b></p>
                                                    <c:set var="indexQuest" value="${counter.count}"/>
                                                </div>
                                            </div>
                                            <p class="text-info mx-2">Select one:</p>
                                            <div class="d-flex flex-column p-2">
                                                <c:forEach var="ans" items="${answers}" varStatus="counter">
                                                    <div class="d-flex flex-row">
                                                        <input type="radio" name="rdAnswerChoice_${question.questionId}" value="${ans.answerId}_${ans.correct}" style="cursor: pointer;" onchange="colorLink(${indexQuest})"/>
                                                        <p class="m-0 m-2"><span>&#${counter.index + 65};) </span>${ans.content}</p>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                                <div class="d-flex justify-content-end mb-5" id="finsh-quiz-submit">
                                    <input type="hidden" name="txtSubjectId" value="${subject.subjectId}"/>
                                    <input type="hidden" name="txtSubjectName" value="${subject.subjectName}"/>
                                    <input type="hidden" name="txtGrade" value="${subject.grade}"/>
                                    <input type="hidden" name="txtQuestionQuantity" value="${questionQuantity}"/>
                                    <button type="button" class="btn btn-primary" data-toggle="modal" id="btn-submit" data-targer="#confirm-submit">Finish Quiz</button>
                                    <input type="hidden" name="minute"/>
                                    <input type="hidden" name="second"/>
                                </div>
                            </form>
                            <div class="modal fade" id="confirm-submit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title w-100 text-center text-info">Confirmation</h5>
                                        </div>
                                        <div class="modal-body text-center">
                                            Once you submit, you will no longer be able to change your answers for this attempt
                                        </div>
                                        <div class="modal-footer d-flex flex-column justify-content-center">
                                            <a id="btn-ok" class="btn btn-info">Submit all and finish</a>
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://momentjs.com/downloads/moment.js"></script>
        <script type="text/javascript">

                                                            var subjectId = document.getElementById("subject-id").value;
                                                            var userId = document.getElementById("user-id").value;
                                                            var timeLimit = document.getElementById("time-limit").value;

                                                            var isChange = false;
                                                            var submitted = false;

                                                            $("input[type=radio]").change(function () {
                                                                isChange = true;
                                                            });

                                                            var timeout;

                                                            function saveChanges() {
                                                                updateTimer(userId + subjectId, document.quizForm.minute.value, document.quizForm.second.value);
                                                                window.onbeforeunload = null;
                                                            }

                                                            $(window).on("beforeunload", function () {
                                                                setTimeout(saveChanges, 0);
                                                                if (submitted) {
                                                                    return underfined;
                                                                }
                                                                if (isChange && !submitted) {
                                                                    timeout = setTimeout(function () {

                                                                    }, 1000);
                                                                    return "You have not saved your changes.";
                                                                }
                                                            });

                                                            function noTimeout() {
                                                                if (isChange && !submitted) {
                                                                    clearTimeout(timeout);
                                                                }
                                                            }

                                                            window.unload = noTimeout;

                                                            const format1 = "dddd, MMM Do YYYY";
                                                            var sdate = document.getElementsByClassName("date");
                                                            var formatDate = document.getElementsByClassName("format-date");
                                                            for (var i = 0; i < sdate.length; i++) {
                                                                var ssdate = sdate[i].value;
                                                                ssdate = ssdate.substring(0, ssdate.lastIndexOf('.'));
                                                                var datetime1 = moment(ssdate).format(format1);

                                                                formatDate[i].innerHTML = datetime1;
                                                            }

                                                            function setTimerWithExpiry(key, min, sec, ttl) {
                                                                var now = new Date().getTime();
                                                                var expiry = now + ttl;
                                                                var item = {
                                                                    min: min,
                                                                    sec: sec,
                                                                    expiry: expiry
                                                                };
                                                                var itemJson = JSON.stringify(item);
                                                                localStorage.setItem('' + key, itemJson);
                                                            }

                                                            function updateTimer(key, min, sec) {
                                                                var itemStr = localStorage.getItem('' + key);
                                                                if (itemStr !== null) {
                                                                    var item = JSON.parse(itemStr);
                                                                    item.min = min;
                                                                    item.sec = sec;
                                                                    var itemJson = JSON.stringify(item);
                                                                    localStorage.setItem('' + key, itemJson);
                                                                }
                                                            }

                                                            function getTimerWithExpiry(key) {
                                                                var itemStr = localStorage.getItem('' + key);

                                                                var item = JSON.parse(itemStr);
                                                                var now = new Date().getTime();
                                                                var remainTime = item.expiry - now;

                                                                if (remainTime < 0) {
                                                                    item.min = 0;
                                                                } else {
                                                                    var remainInMin = Math.floor(remainTime / (60 * 1000));
                                                                    var remainInSec = ((remainTime % (60 * 1000)) / 1000).toFixed(0);

                                                                    item.min = remainInMin;
                                                                    item.sec = remainInSec;
                                                                }

                                                                return item;
                                                            }

                                                            var tim;

                                                            var min = '${requestScope.min}';
                                                            var sec = '${requestScope.sec}';

                                                            if (min === '' && sec === '') {
                                                                var item = getTimerWithExpiry(userId + subjectId);
                                                                min = item.min;
                                                                sec = item.sec;
                                                            } else {
                                                                if (localStorage.getItem('' + userId + subjectId) === null) {
                                                                    setTimerWithExpiry(userId + subjectId, min, sec, parseInt(timeLimit) * 60 * 1000);
                                                                }
                                                            }

                                                            function quizTimer() {
                                                                if (parseInt(sec) > 0) {
                                                                    var isFirst = true;
                                                                    if (parseInt(sec) < 10) {
                                                                        sec = '0' + sec;
                                                                    }
                                                                    if (parseInt(min) < 10 && isFirst === true) {
                                                                        min = '0' + min;
                                                                        isFirst = false;
                                                                    }
                                                                    if (parseInt(min) > 0) {
                                                                        document.getElementById("show-time").innerHTML = min + " : " + sec;
                                                                    } else {
                                                                        document.getElementById("show-time").innerHTML = "<font color='red'>" + min + " : " + sec + "</font>";
                                                                    }
                                                                    sec = parseInt(sec) - 1;
                                                                    min = parseInt(min);
                                                                    document.quizForm.minute.value = min;
                                                                    document.quizForm.second.value = sec;
                                                                    tim = setTimeout("quizTimer()", 1000);
                                                                } else {
                                                                    if (parseInt(min) === 0 && parseInt(sec) === 0) {
                                                                        submitted = true;
                                                                        document.getElementById("show-time").innerHTML = "<font color='red'>" + "0" + min + " : " + "0" + sec + "</font>";
                                                                        localStorage.removeItem('' + userId + subjectId);
                                                                        document.quizForm.minute.value = 0;
                                                                        document.quizForm.second.value = 0;
                                                                        document.quizForm.submit();
                                                                    }

                                                                    if (parseInt(sec) === 0) {
                                                                        if (parseInt(min) < 10) {
                                                                            min = '0' + min;
                                                                        }
                                                                        sec = '0' + sec;
                                                                        document.getElementById("show-time").innerHTML = min + " : " + sec;
                                                                        min = parseInt(min) - 1;
                                                                        sec = 59;
                                                                        document.quizForm.minute.value = min;
                                                                        document.quizForm.second.value = sec;
                                                                        tim = setTimeout("quizTimer()", 1000);
                                                                    }
                                                                }
                                                            }

                                                            function colorLink(val) {
                                                                document.getElementById(val).style.color = "#228B22";
                                                            }

                                                            $(document).ready(function () {
                                                                $("#finsh-quiz-submit").on('click', '#btn-submit', function () {
                                                                    $('#confirm-submit').modal("show");
                                                                });

                                                                $('#btn-ok').click(function () {
                                                                    submitted = true;
                                                                    localStorage.removeItem('' + userId + subjectId);
                                                                    document.quizForm.minute.value = 0;
                                                                    document.quizForm.second.value = 0;
                                                                    document.quizForm.submit();
                                                                });

                                                            });
        </script>
    </body>
</html>
<!-- footer -->
<footer class="footer text-center text-lg-start">
    <!-- Copyright -->
    <div class="text-left p-3">
        © 2021 Copyright:<br>
        <a class="text-info" href="home">Take Quiz Online</a>
    </div>
    <!-- Copyright -->
</footer>

