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
			<c:url value="add" var="registrationUrl" />
				<form id="myForm" action="${registrationUrl}" method="post"
					enctype="multipart/form-data">

					<h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student
						Registration</h2>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="studentID" class="col-md-2 col-form-label">Student
							ID</label>
						<div class="col-md-4">
							<input type="text" class="form-control"
								value="${sessionScope.studentId}" id="studentID"
								name="studentId" disabled>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="name" class="col-md-2 col-form-label">Name</label>
						<div class="col-md-4">
							<input type="text" class="form-control" id="name" name="name" placeholder="Enter student name" required>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="dob" class="col-md-2 col-form-label">DOB</label>
						 <div class="col-md-4">
					        <input type="date" class="form-control" id="dob" name="dob" required onchange="validateAge()">
					    </div>
					</div>
					<fieldset class="row mb-4">
						<div class="col-md-2"></div>
						<legend class="col-form-label col-md-2 pt-0">Gender</legend>
						<div class="col-md-4">
							<div class="form-check-inline">
								<input class="form-check-input" type="radio" name="gender"
									id="gridRadios1" value="0"> <label
									class="form-check-label" for="gridRadios1"> Male </label>
							</div>
							<div class="form-check-inline">
								<input class="form-check-input" type="radio" name="gender"
									id="gridRadios2" value="1"> <label
									class="form-check-label" for="gridRadios2"> Female </label>
							</div>

						</div>
					</fieldset>

					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="phone" class="col-md-2 col-form-label">Phone</label>
						<div class="col-md-4">
					        <input type="tel" id="phone" name="phone"  class="form-control" placeholder="e.g., 09-123456789" required
					               oninput="validatePhoneNumber(this);">
					        <br>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="education" class="col-md-2 col-form-label">Education</label>
						<div class="col-md-4">
							<select class="form-select" aria-label="Education" id="education"
								name="education">
								<option selected>Bachelor of Information Technology</option>
								<option value="Diploma in IT">Diploma in IT</option>
								<option value="Bachelor of Computer Science">Bachelor of Computer Science</option>

							</select>
						</div>
					</div>
					<fieldset class="row mb-4">
						<div class="col-md-2"></div>
						<legend class="col-form-label col-md-2 pt-0">Attend</legend>

						<div class="col-md-4">
							<c:forEach var="course" items="${sessionScope.courseList}">
								<div class="form-check-inline col-md-auto">
									<input class="form-check-input" type="checkbox" name="courses"
										id="${course.id}" value="${course.id}" > <label
										class="form-check-label" for="${course.id}">
										${course.name} </label>
								</div>
							</c:forEach>
							<c:if test="${empty courseList}">
								<p>There is no course for attend.</p>
							</c:if>
						</div>
					</fieldset>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="photo" class="col-md-2 col-form-label">Photo</label>
						<div class="col-md-4">
							<input type="file" accept=".jpg, .jpeg, .png" name="imageFile"
								class="form-control" id="photo" required>
						</div>
					</div>

					<div class="row mb-4">
						<div class="col-md-4"></div>
						<div class="d-flex justify-content-end col-md-4">
							<button type="button" class="btn btn-danger col-md-2 me-2">Reset</button>
							<button type="button" class="btn btn-success col-md-4"
								data-bs-toggle="modal" data-bs-target="#exampleModal">
								Add New Student</button>
								
							<!-- Modal -->
							<div class="modal fade" id="exampleModal" tabindex="-1"
								aria-labelledby="exampleModalLabel" aria-hidden="true">
								<div class="modal-dialog modal-dialog-centered">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title" id="exampleModalLabel">Verify
												you are Authenticate Person</h5>
											<button type="button" class="btn-close"
												data-bs-dismiss="modal" aria-label="Close"></button>
										</div>

										<div class="modal-body">
											<input type="password" class="form-control" id="adminPass"
												name="adminPass" placeholder="Enter your password" required>

										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-secondary"
												data-bs-dismiss="modal">Close</button>
											<button type="submit" class="btn btn-success">
												Add new student</button>
										</div>

									</div>
								
							</div>
						</div>
					</div>
					</div>
				</form>
			</div>
		</div>
		
		<!-- success toast  -->
		<div class="position-fixed end-0 p-3" style="z-index: 11">
			<div id="successToast" class="toast hide" role="alert"
				aria-live="assertive" aria-atomic="true">
				<div class="toast-header">
					<div class="me-2">
						<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
							viewBox="0 0 24 24" style="fill: green; transform:; msFilter:;">
							<path
								d="M12 2C6.486 2 2 6.486 2 12s4.486 10 10 10 10-4.486 10-10S17.514 2 12 2zm-1.999 14.413-3.713-3.705L7.7 11.292l2.299 2.295 5.294-5.294 1.414 1.414-6.706 6.706z"></path></svg>
					</div>
					<strong class="me-auto">Created</strong> <small>11 mins ago</small>
					<button type="button" class="btn-close" data-bs-dismiss="toast"
						aria-label="Close"></button>
				</div>
				<div class="toast-body">Successfully created user</div>
			</div>
		</div>

		<!-- fail toast  -->

		<div class="toast-container position-fixed end-0 p-3 z-1">
			<div id="failToast" class="toast" role="alert" aria-live="assertive"
				aria-atomic="true">
				<div class="toast-header">
					<div class="me-2">
						<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
							viewBox="0 0 24 24" style="fill: red; transform:; msFilter:;">
					<path
								d="M12 2C6.486 2 2 6.486 2 12s4.486 10 10 10 10-4.486 10-10S17.514 2 12 2zm4.207 12.793-1.414 1.414L12 13.414l-2.793 2.793-1.414-1.414L10.586 12 7.793 9.207l1.414-1.414L12 10.586l2.793-2.793 1.414 1.414L13.414 12l2.793 2.793z"></path></svg>
					</div>
					<strong class="me-auto text-danger">Failed to create user</strong>
					<small>11 mins ago</small>
					<button type="button" class="btn-close" data-bs-dismiss="toast"
						aria-label="Close"></button>
				</div>
				<div class="toast-body text-danger">${sessionScope.error}</div>
			</div>
		</div>
	
	<script>
                       
          //toast
    		if(${sessionScope.isCreatedStudent}){
    			var toast = new bootstrap.Toast(document.querySelector('#successToast'));
    			toast.show();


    		}
    		if(${sessionScope.notCreatedStudent}){
    			var toast = new bootstrap.Toast(document.querySelector('#failToast'));
    			toast.show();

    		}
    		
    		//check attend
    		document.getElementById("myForm").addEventListener("submit", function(event) {
    		    var checkboxes = document.querySelectorAll('input[name="courses"]');
    		    var checkedCountA = 0;
    		    
    		    var radioButtons = document.querySelectorAll('input[name="gender"]');
    		    var checkedCountB = 0;
    		    
    		    for (var i = 0; i < checkboxes.length; i++) {
    		        if (checkboxes[i].checked) {
    		            checkedCountA++;
    		        }
    		    }

    		    if (checkedCount < 1) {
    		        alert("Please select at least 1 course.");
    		        event.preventDefault();
    		    }

    		    for (var i = 0; i < radioButtons.length; i++) {
    		        if (radioButtons[i].checked) {
    		            checkedCountB++;
    		        }
    		    }

    		    if (checkedCount === 0) {
    		        alert("Please select Gender.");
    		        event.preventDefault();
    		    }
    		});
    		
    		// Validate and format Myanmar phone number input
            function validatePhoneNumber(input) {
                var phoneNumber = input.value;
                var validFormat = /^[0-9]{2}-[0-9]{8,9}$/.test(phoneNumber);
                
                if (validFormat) {
                    input.setCustomValidity("");
                    input.value = formatPhoneNumber(phoneNumber);
                } else {
                    input.setCustomValidity("Enter a valid Myanmar phone number (e.g., 09-123456789).");
                }
            }

            // Format Myanmar phone number as user types
            function formatPhoneNumber(phoneNumber) {
                return phoneNumber.replace(/(\d{2})(\d{8,9})/, "$1-$2");
            }
            
            function validateAge() {
                var dobInput = document.getElementById('dob');
                var selectedDate = new Date(dobInput.value);
                var currentDate = new Date();
                var minAgeDate = new Date(currentDate.getFullYear() - 14, currentDate.getMonth(), currentDate.getDate());

                if (selectedDate > minAgeDate) {
                    alert("Age must be above 14 years.");
                    dobInput.value = ''; // Clear the input field
                }
            }
      </script>
</body>

</html>

