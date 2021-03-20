<%-- 
    Document   : showHistory
    Created on : Jan 29, 2021, 2:03:59 PM
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
        <title>JSP Page</title>
    </head>
    <body>
        <c:if test="${sessionScope.USER == null}">
            <c:redirect url="login" />
        </c:if>
        <c:if test="${sessionScope.ROLE ne 'Student'}">
            <c:redirect url="home" />
        </c:if>
        <style>
            .table-submit {
                line-height: 1.2;
            }

            .card {
                min-width: 0;
                word-wrap: break-word;
                background-clip: border-box;
            }
        </style>
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
                    <div class="row align-items-center">
                        <div class="col-xl-12" style="position: relative; height: 100%; padding-bottom: 6rem;">
                            <div class="mb-2">
                                <a href="home" class="btn btn-secondary">Back to Home</a>
                            </div>
                            <div class="card position-relative d-flex flex-column">
                                <div class="table-responsive">
                                    <table class="table table-borderless table-submit" style="background: #005086; border-left: 2px solid #005086; border-right: 2px solid #005086;">
                                        <thead class="text-muted">
                                            <tr>
                                                <th colspan="2" scope="col">
                                                    <div class="text-center pb-5">
                                                        <form action="search-history" class="d-flex flex-row">
                                                            <div class="w-100 mx-auto">
                                                                <div class="form-group mb-1">
                                                                    <i class="fa fa-search position-absolute" style="padding: 10px; min-width: 40px; "></i>
                                                                    <input type="text" name="txtSearchSubmit" value="${param.txtSearchSubmit}" class="form-control w-100 align-content-center" style="padding-left: 35px;" placeholder="Search">
                                                                </div>
                                                            </div>
                                                            <div>
                                                                <input type="hidden" name="pageNo" value="${requestScope.PAGE_NO}" />
                                                                <input type="hidden" name="pageSize" value="${requestScope.PAGE_SIZE}"/>
                                                                <button type="submit" class="btn btn-success mr-1" style="width: 100px;">Search</button>
                                                                <input type="reset" class="btn btn-secondary mt-1" style="width: 100px;" value="Reset" />
                                                            </div>
                                                        </form>
                                                    </div>
                                                </th>
                                            </tr>
                                            <tr>
                                                <th colspan="2" scope="col">
                                                    <div class="d-flex justify-content-end">
                                                        <form action="search-history">
                                                            <c:set var="pageSize" value="${requestScope.PAGE_SIZE}"/>
                                                            <select name="pageSize">
                                                                <option value="all">Always show all</option>
                                                                <c:forEach var="size" begin="1" end="20" step="1">
                                                                    <option value="${size}" class="form-control" <c:if test="${pageSize == size}">selected="selected"</c:if>>${size}</option>
                                                                </c:forEach>
                                                            </select>
                                                            <input type="hidden" name="txtSearchSubmit" value="${param.txtSearchSubmit}"/>
                                                            <input type="submit" class="btn btn-primary ml-2" name="action" value="Change size">
                                                        </form>
                                                    </div>
                                                </th>
                                            </tr>
                                        </thead>

                                        <c:set var="submits" value="${requestScope.SUBMIT_QUIZ_LIST}"/>
                                        <c:if test="${not empty submits}">
                                            <c:forEach var="submit" items="${submits}">
                                                <tbody class="mt-3 bg-white w-100" style="border-bottom: 2px solid #005086;">
                                                    <tr>
                                                        <td class="small">
                                                            <p>Submit Date: ${submit.submitDate}</p>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <div class="d-flex flex-row">
                                                                <div>
                                                                    <p><b>Subject Name: </b> </p>
                                                                    <p><b>Number Of Correct: </b></p>
                                                                    <p><b>Grade: </b></p>
                                                                </div>
                                                                <div class="ml-5" style="font-size: 18px;">
                                                                    <p>${submit.subjectName}</p>
                                                                    <p>${submit.numberOfCorrect}</p>
                                                                    <p>${submit.grade}</p>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </c:forEach>
                                        </table>
                                        <div class="d-flex w-100 justify-content-center mt-2 mb-4">
                                            <c:if test="${not empty requestScope.PAGE_NO}">
                                                <c:set var="pageNo" value="${requestScope.PAGE_NO}" />
                                                <c:if test="${pageNo != 1}">
                                                    <c:url var="prevLink" value="search-history">
                                                        <c:param name="txtSearchSubmit" value="${param.txtSearchSubmit}"/>
                                                        <c:param name="pageNo" value="${pageNo}"/>
                                                        <c:param name="pageSize" value="${requestScope.PAGE_SIZE}"/>
                                                        <c:param name="action" value="Prev" />
                                                    </c:url>
                                                    <a href="${prevLink}" class="btn btn-primary">Prev</a>
                                                </c:if>
                                                <input type="number" class="form-control m-0 mx-2 text-center" min="1" value="${pageNo}" style="width: 50px;" readonly="readonly"/>
                                                <c:if test="${empty requestScope.LAST_LIST}">
                                                    <c:url var="nextLink" value="search-history">
                                                        <c:param name="txtSearchSubmit" value="${param.txtSearchSubmit}"/>
                                                        <c:param name="pageNo" value="${pageNo}"/>
                                                        <c:param name="pageSize" value="${requestScope.PAGE_SIZE}"/>
                                                        <c:param name="action" value="Next" />
                                                    </c:url>
                                                    <a href="${nextLink}" class="btn btn-primary">Next</a>
                                                </c:if>
                                            </c:if>
                                        </div>
                                    </c:if>
                                    <c:if test="${empty submits}">
                                        <tbody class="bg-white" style="border-bottom: 2px solid #005086;">
                                            <tr>
                                                <td class="text-center py-5" colspan="2">
                                                    <font color="red">
                                                    No result was found
                                                    </font>
                                                </td>
                                            </tr>
                                        </tbody>
                                        </table>
                                    </c:if>
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
            </div>
        </div>
    </body>
</html>
