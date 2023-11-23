<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<title>Student Registration</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	
<link rel="stylesheet"
  href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">

<style type="text/css">

body{
    margin:0px;
    font-family: "Roboto", sans-serif;
	font-size:11pt;
	float:left;
	text-align:justify;
  color: rgb(0, 128, 128);  
  background-image: linear-gradient(45deg,#dde6e5,#d6f5e3);
    margin-left: auto;
    margin-right:auto;
    width: 1366px;
    height: 100%;
    overflow:hidden;
}
.user-photo {
  width: 100px;
  height: 120px;
  object-fit: cover;
  border: 1px solid linear-gradient(45deg,#dde6e5,#d6f5e3);
  border-radius: 5px;
}

.te-20{
	top: 8%;
	right: 8%;
}
#testheader{
  background: #f2f2f2;
    width: 100%;
    height: 90px;    
    float: top;
    padding:20px 20px 0px 0px;
    }

#testheader a{
  /* text-decoration: none; */
  color: rgb(114, 112, 112);
  font-family: "Roboto", sans-serif;
}

#testsidebar{
    background: rgb(255, 217, 171);
    float: left;
    width:220px;
	min-height:1000px;
	height:auto !important;
}

#testfooter{
  background: #f2f2f2;
    height: 60px;  
    text-align: center;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 20px 20px 0px 0px;
    position: fixed;
    width: 100%;
    float: bottom;
}

#container{
    overflow: hidden;
    flex: 1;
}

#main_contents{
    background: #ffffff;
    overflow: hidden;
    min-height: 1000px;
    height: auto !important;
    padding-top: 15px;
}

#sub_content{
    padding: 10px;
    margin-left: auto;
    padding-left: 10px;
    overflow :auto ;
    position: absolute ;
    width: 100%;
    height:auto;
    top: 90px;
    bottom: 20px;
    left: 220px;   

}

#contents h3{
    color: rgb(82, 179, 69);
    padding: 20px 20px 20px 20px;
    margin-left: 250px;
    font-size: 20px;
  }

  #stduentTable{
    width: 80%;
  }


/* Login Form css */


.login-page {
  width: 360px;
  padding: 8% 0 0;
  margin: auto;
}
.login-page p {
    color: firebrick;
    font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
}
.login-page .form .login{
  margin-top: -31px;
margin-bottom: 26px;
}

.w-m-full{
	 width: 100vw; 
}
.form {
  position: relative;
  z-index: 1;
  background: #FFFFFF;
  max-width: 360px;
  margin: 0 auto 100px;
  padding: 45px;
  text-align: center;
  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
}
.form input {
  font-family: "Roboto", sans-serif;
  outline: 0;
  background: #f2f2f2;
  width: 100%;
  border: 0;
  margin: 0 0 15px;
  padding: 15px;
  box-sizing: border-box;
  font-size: 14px;
}
.form button {
  font-family: "Roboto", sans-serif;
  text-transform: uppercase;
  outline: 0;
  background-color: #328f8a;
  background-image: linear-gradient(45deg,#0881c2,#00a6ff);
  width: 100%;
  border: 0;
  padding: 15px;
  color: #FFFFFF;
  font-size: 14px;
  -webkit-transition: all 0.3 ease;
  transition: all 0.3 ease;
  cursor: pointer;
}
.form .message {
  margin: 15px 0 0;
  color: #b3b3b3;
  font-size: 12px;
}
.form .message a {
  color: #4CAF50;
  text-decoration: none;
}

.bottom-10{
	bottom: 10%;
}

.login-page-body {
  background-color: #328f8a;
  background-image: linear-gradient(45deg,#d9f5ff,#00a6ff);
  font-family: "Roboto", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 100%;
    height: 100%;
    overflow: hidden;
    color: rgb(0, 0, 0);
}


/* Fixed sidenav, full height */
.sidenav {
    height: 100%;
    width: 200px;
    position: absolute;
    z-index: 0;
    left: 0;
    font-family: "Roboto", sans-serif;
    /* background-color: rgb(154, 255, 133); */
    background-image: linear-gradient(45deg,#328f8a,#08ac4b);
    overflow-x: hidden;
    padding-top: 20px;
    float: left;
  }
  .side-bg{
 	
    background-image: linear-gradient(45deg,#328f8a,#08ac4b);
  }
  
  /* Style the sidenav links and the dropdown button */
  .sidenav a, .dropdown-btn {
    padding: 6px 8px 6px 16px;
    text-decoration: none;
    font-size: 16px;
    font-family: "Roboto", sans-serif;
    color: #ffffff;
    display: block;
    border: none;
    background: none;
    width: 100%;
    text-align: left;
    cursor: pointer;
    outline: none;
  }
  
  /* On mouse-over */
  .sidenav a:hover, .dropdown-btn:hover {
    color: #000000;
  }
  
  /* Main content */
  .main {
    margin-left: 200px; /* Same as the width of the sidenav */
    font-size: 20px; /* Increased text to enable scrolling */
    padding: 0px 10px;
  }
  
  /* Add an active class to the active dropdown button */
  .active {
    background-color: rgb(145, 145, 145);
    color: white;
  }
  
  /* Dropdown container (hidden by default). Optional: add a lighter background color and some left padding to change the design of the dropdown content */
  .dropdown-container {
    display: none;
    background-color: rgb(145, 145, 145);
    padding-left: 8px;
  }
  
  /* Optional: Style the caret down icon */
  .fa-caret-down {
    float: right;
    padding-right: 8px;
  }
  
  /* Some media queries for responsiveness */
  @media screen and (max-height: 450px) {
    .sidenav {padding-top: 15px;}
    .sidenav a {font-size: 18px;}
  }
  
  .h-full{
  	height: 100%;
  margin-block: 11px;
  }

</style>

</head>
<body class="login-page-body">
		<div class="login-page">
		<div class="form">
			<div class="login">
				<div class="login-header">
					<c:url value="/images/logo.png" var="logo" />
					<img class="me-2" src="${logo}" alt="Logo" width="100" height="100"/>
					<h1>Welcome!</h1>
					<p id="error">${accountDisableException}</p>
				</div>
			</div>
			<c:url value="/login" var="login" />
			<form class="login-form" action="${login}" method="post">
			
				<div class="input-group mb-3">
				  <div class="input-group-prepend">
				    <span class="input-group-text" id="basic-addon1"><i class='bx bx-envelope h-full' ></i></span>
				  </div>
				  <input type="email" class="form-control" placeholder="Email" aria-label="email" aria-describedby="basic-addon1" name="email" value="${email}" required>
					
				</div>
				<p id="emailError">${accountNotFoundException}</p>
				
				<div class="input-group mb-3">
				  <div class="input-group-prepend">
				    <span class="input-group-text" id="basic-addon1"><i class='bx bx-lock-alt h-full' ></i></span>
				  </div>
				  <input type="password" class="form-control" placeholder="Password" aria-label="pass" aria-describedby="basic-addon1" name="pass" value="${password}" required>
					
				</div>
				<p id="passwordError">${passwordNotMatchException}</p>
				
				<button type="submit" class="btn fw-bold fs-6">login</button>
			</form>
		</div>
	</div>
</body>
<script>

const passwordInput = document.querySelector('input[name="pass"]');
const passwordError = document.getElementById('passwordError');

// Add an event listener to the password input field
passwordInput.addEventListener('input', function() {
    // Check if the password input field is empty
    if (passwordInput.value === '') {
        // Hide the password error message
        passwordError.style.display = 'none';
    }
});

const emailInput = document.querySelector('input[name="email"]');
const emailError = document.getElementById('emailError');
const error = document.getElementById('error');

// Add an event listener to the password input field
emailInput.addEventListener('input', function() {
    // Check if the password input field is empty
    if (emailInput.value === '') {
    	
        // Hide the password error message
        error.style.display ='none';
        emailError.style.display = 'none';
    }
});

</script>
</html>