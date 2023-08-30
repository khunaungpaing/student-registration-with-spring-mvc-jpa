
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
			<c:url value="/students" var="studentList" />
			<form class="row g-3 mt-3 ms-2" action="${studentList}" method="get">
				<div class="col-auto">
					<label for="staticEmail2" class="visually-hidden">studentID</label>
					<input type="text" class="form-control" id="staticEmail2"
						name="studentId" placeholder="Student ID"
						value="${searchStudentId}" required>
				</div>
				<div class="col-auto">
					<label for="inputPassword2" class="visually-hidden">studentName</label>
					<input type="text" class="form-control" id="inputPassword2"
						name="studentName" placeholder="Student Name"
						value="${searchStudentname}" required>
				</div>

				<div class="col-auto ">
				
					<button type="submit" class="btn btn-primary mb-3 d-flex justify-content-center align-items-center"> <i class='bx bx-search me-2'></i> Search</button>
				</div>
				<div class="col-auto">
				
					<c:url value="/students/add" var="registrationUrl" />
					<button type="button" class="btn btn-success mb-3 d-flex justify-content-center align-items-center"
					onclick="location.href='${registrationUrl}'"> <i class='bx bx-plus me-2'></i> Add</button>
				</div>
				<div class="col-auto">
				

					<c:url value="/students" var="studentList" />
					<button type="button" class="btn btn-danger mb-3 d-flex justify-content-center align-items-center"
						onclick="location.href='${studentList}'"><i class='bx bx-reset me-2' style='color:#ffffff' ></i> Reset</button>
				</div>
			</form>
			<div>
				<table class="table table-striped" id="stduentTable">
					<thead>
						<tr>
							<th scope="col">Student ID</th>
							<th scope="col">Name</th>
							<th scope="col">Photo</th>
							<th scope="col">Details</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty sessionScope.AllStudentLists}">
							<c:forEach var="student" items="${sessionScope.AllStudentLists}">
								<tr class="align-middle">
									<th class="fs-5" scope="row">${student.id}</th>
									<td class="fs-5">${student.name}</td>
									<td><c:url value="${student.img_url}" var="studentPhoto" />
										<img src="${studentPhoto}" class="user-photo" alt="User Photo"
										width="100px" height="120px"></td>
									<td>
									<c:url value="/students/${student.id}" var="studentDetails" />
										<button type="button" class="btn btn-primary mb-2"
											onclick="location.href='${studentDetails}'">See
											More</button>
									</td>
								</tr>

							</c:forEach>
						</c:if>

						<c:if test="${empty sessionScope.AllStudentLists}">
                            <tr>
                                <th class="text-center fs-4 table-success" colspan="6">There
                                    is no student.
                                </th>
                            </tr>
                        </c:if>

					</tbody>
				</table>
			</div>
		</div>
	</div>

</body>

</html>



