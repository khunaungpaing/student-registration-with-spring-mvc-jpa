<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Student Registration</title>
</head>

<body>
<jsp:include page="header.jsp"></jsp:include>

<div class="main_contents">
    <div id="sub_content">

        <form class="row g-3 mt-3 ms-2" action="courses" method="get">
            <div class="col-auto">
                <label for="staticEmail2" class="visually-hidden">Course Id</label>
                <input type="text" class="form-control" id="staticEmail2" placeholder="Course ID"
                       value="${sessionScope.searchCourseId}" name="courseId" required>
            </div>
            <div class="col-auto">
                <label for="inputPassword2" class="visually-hidden">Course Name</label>
                <input type="text" class="form-control" id="inputPassword2" placeholder="Course Name"
                       value="${sessionScope.searchCourseName}" name="courseName" required>
            </div>

            <div class="col-auto">
                <button type="submit" class="btn btn-primary mb-3 d-flex justify-content-center align-items-center"><i
                        class='bx bx-search me-2'></i>Search
                </button>
            </div>
            <div class="col-auto">
                <button type="button" class="btn btn-success d-flex justify-content-center align-items-center"
                        onclick="location.href = 'courses/add';">
                    <i class='bx bx-plus me-2'></i>Add
                </button>

            </div>
            <div class="col-auto">
                <button type="reset" class="btn btn-danger mb-3 d-flex justify-content-center align-items-center"
                        onclick="location.href='users'"><i class='bx bx-reset me-2' style='color:#ffffff'></i>Reset
                </button>
            </div>
        </form>

        <table class="table table-striped" id="stduentTable">
            <thead>
            <tr>

                <th scope="col" >Course ID</th>
                <th scope="col">Course Name</th>
                <th scope="col" class="text-center" >Attendees Count</th>
                <th scope="col" >Created At</th>
                <th scope="col">Status</th>
                <th scope="col" width="350">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${not empty sessionScope.courseList}">
                <c:forEach var="course" items="${sessionScope.courseList}" varStatus="loop">
                    <c:set var="statusText" value="${course.active ? 'Active' : 'Disable'}"/>
                    <c:set var="statusBtn" value="${!course.active ? 'Activate' : 'Disable'}"/>
                    <c:set var="statusTitle" value="${!course.active? 'Activate' : 'Deactivate'}"/>
                    <c:set var="statusTextColor" value="${course.active ? 'text-success' : 'text-danger'}"/>
                    <c:set var="statusBtnColor" value="${!course.active ? 'btn-success' : 'btn-danger'}"/>

                    <tr class="align-middle">
                        <th scope="col">${course.id}</th>
                        <td>${course.name}</td>
                        <td class="text-center">${course.students.size()}</td>
                        <fmt:formatDate value="${course.createdAt}" pattern="hh:mm a (dd-MMM-yyyy)" var="formattedDate" />
                        <td> ${formattedDate}</td>
                        <td  class="${statusTextColor} fs-6 fw-bold">${statusText}</td>
                        <td>
                            <button type="button" class="col-2 btn btn-primary btn-sm me-2" onclick="location.href='courses/update?courseId=${course.id}'">
                                Edit
                            </button>
                            <button type="button" class="col-4 btn ${statusBtnColor} btn-sm" data-bs-toggle="modal"
                                    data-bs-target="#exampleModal${loop.index}">${statusBtn}</button>
                        </td>
                    </tr>

                    <div class="modal fade" id="exampleModal${loop.index}" tabindex="-1"
                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title text-danger" id="exampleModalLabel">Are you sure
                                        to ${statusTitle} ${course.id} ${course.name}</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger col-md-2" data-bs-dismiss="modal"
                                            onclick="location.href='courses?active=${!course.active}&courseId=${course.id}'">
                                        Ok
                                    </button>

                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty sessionScope.courseList}">
                <tr>
                    <th class="text-center fs-4 table-success" colspan="6">There is no course.</th>
                </tr>
            </c:if>

            </tbody>
        </table>


    </div>
</div>

</body>

</html>

