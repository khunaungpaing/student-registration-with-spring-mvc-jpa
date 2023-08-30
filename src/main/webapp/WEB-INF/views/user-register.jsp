<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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

                <form id="user-form" action="register" method="post">

                    <h2 class="col-md-6 offset-md-2 mb-5 mt-4 fw-bold">CREATE NEW
                        USER</h2>
                    <div class="row mb-4">
                        <div class="col-md-2"></div>
                        <label for="userid" class="col-md-2 col-form-label">User
                            ID</label>
                        <div class="col-md-4">
                            <input type="text" class="form-control" id="userid" name="userId"
                                   value="${sessionScope.userId}" placeholder="UserId" disabled
								required>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="email" class="col-md-2 col-form-label">Email</label>
						<div class="col-md-4">
							<input type="email" class="form-control" id="email" name="email"
								placeholder="Email" required>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="name" class="col-md-2 col-form-label">Username</label>
						<div class="col-md-4">
							<input type="text" class="form-control" id="name" name="name"
								placeholder="Username" required>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="password" class="col-md-2 col-form-label">Passowrd</label>
						<div class="col-md-4">
							<input type="password" class="form-control" id="password"
								name="pass" placeholder="Password" required>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="confirmPassword" class="col-md-2 col-form-label">Confirm
							Passowrd</label>
						<div class="col-md-4">
							<input type="password" class="form-control" id="confirm-password"
								name="comfirm" placeholder="Confirm password" required>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-4"></div>

						<div class="d-flex justify-content-end col-md-4">

							<!-- Reset Button -->
							<button type="reset" class="btn btn-secondary col-md-4 me-2">
								Reset</button>

							<!-- Button trigger modal -->
							<button type="button" class="btn btn-success col-md-4"
								data-bs-toggle="modal" data-bs-target="#exampleModal">
								Create</button>

							<!-- Modal -->
							<div class="modal fade" id="exampleModal" tabindex="-1"
								aria-labelledby="exampleModalLabel" aria-hidden="true">
								<div class="modal-dialog modal-dialog-centered">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title" id="exampleModalLabel">Verify
												you are ADMIN</h5>
											<button type="button" class="btn-close"
												data-bs-dismiss="modal" aria-label="Close"></button>
										</div>

										<div class="modal-body">
											<input type="password" class="form-control" id="adminPass"
												name="adminPass" placeholder="Enter admin password" required>

										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-secondary"
												data-bs-dismiss="modal">Close</button>
											<button type="submit" class="btn btn-success">
												Create New User</button>
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
        
        //check password
        const form = document.getElementById('user-form');
        const passwordInput = form.querySelector('#password');
        const confirmPasswordInput = form.querySelector('#confirm-password');

        form.addEventListener('submit', function(event) {
            if (passwordInput.value !== confirmPasswordInput.value) {
                alert("Passwords do not match!");
                event.preventDefault();
            } else if (passwordInput.value.length < 8) {
                alert("Password must be at least 8 characters long!");
                event.preventDefault(); 
            }
        });
        
        
        
		//toast
		if(${sessionScope.isCreated}){
			var toast = new bootstrap.Toast(document.querySelector('#successToast'));
			toast.show();


		}
		if(${sessionScope.notCreated}){
			var toast = new bootstrap.Toast(document.querySelector('#failToast'));
			toast.show();

		}
        


    </script>
</body>

</html>