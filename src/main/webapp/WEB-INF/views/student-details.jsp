<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
       <title>Student Registration</title>
        
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="main_contents">
		<div id="sub_content">
			<h2 class="col-md-6 offset-md-2 mb-5 mt-4 ">Student Details Info</h2>
			<div class="card col-md-6 offset-md-2 mb-5 mt-4"
				style="max-width: 700px;">
				<div class="card-body p-5">
				<c:url value="/students/${student.id}/update" var="studentUpdate" />
				<button type="button"
						class="btn btn-primary position-absolute te-20"
						onclick="location.href='${studentUpdate}'">Edit</button>
				
					
				<table class="table">
						<tr>
							<th>Photo</th>
							<td><c:url value="${sessionScope.student.img_url}" var="studentPhoto" />
										<img src="${studentPhoto}" class="user-photo" alt="User Photo"
										width="100px" height="120px">	
									
							</td>
								
						</tr>
						<tr>
							<th>Student Id</th>
							<td>${sessionScope.student.id}</td>
						</tr>
						<tr>
							<th>Student Name</th>
							<td>${sessionScope.student.name}</td>
						</tr>
						<tr>
							<th>Date of Birth</th>
							<td>${sessionScope.student.dob}</td>
						</tr>
						<tr>
							<th>Gender</th>
							<td>${sessionScope.student.gender==0?'Male':'Female'}</td>
						</tr>
						<tr>
							<th>Phone</th>
							<td>${sessionScope.student.phone}</td>
						</tr>
						<tr>
							<th>Education</th>
							<td>${sessionScope.student.education}</td>
						</tr>
						<tr>
							<th>Attend</th>
							
								<td>
									<c:forEach var="course" items="${sessionScope.student.enrollment}" varStatus="loop">
                                        ${course.name} ${not loop.last?", ":" "}
                                    </c:forEach>
								</td>
							
						</tr>
						
					</table>

				</div>
			</div>

		</div>
	</div>

</body>
</html>