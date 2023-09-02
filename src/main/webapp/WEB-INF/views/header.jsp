<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Registration</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

    <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


    <style type="text/css">
        body {
            margin: 0px;
            font-family: "Roboto", sans-serif;
            font-size: 11pt;
            float: left;
            text-align: justify;
            color: #537cbd;
            background: #f2f9ff;
            margin-left: auto;
            margin-right: auto;
            width: 1366px;
            height: 100%;
            overflow: hidden;
        }

        .user-photo {
            width: 100px;
            height: 120px;
            object-fit: cover;
            border: 1px solid linear-gradient(45deg, #dde6e5, #d6f5e3);
            border-radius: 5px;
        }

        .user-photo1 {
            width: 40px;
            height: 40px;
            object-fit: cover;
            border: 1px solid linear-gradient(45deg, #dde6e5, #d6f5e3);
            border-radius: 50%;
            margin-right: 15px;
        }

        .te-20 {
            top: 8%;
            right: 8%;
        }

        #testheader {
            background: #f2f2f2;
            width: 100%;
            height: 90px;
            float: top;
            padding: 20px 20px 0px 0px;
        }

        #testheader a {
            /* text-decoration: none; */
            color: rgb(114, 112, 112);
            font-family: "Roboto", sans-serif;
        }

        #testsidebar {
            background: rgb(255, 217, 171);
            float: left;
            width: 220px;
            min-height: 1000px;
            height: auto !important;
        }

        #testfooter {
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
            z-index: 1;
        }

        #container {
            overflow: hidden;
            flex: 1;
        }

        #main_contents {
            background: #ffffff;
            overflow: hidden;
            min-height: 1000px;
            height: auto !important;
            padding-top: 15px;
        }

        #sub_content {
            padding: 10px;
            margin-left: auto;
            padding-left: 10px;
            overflow: auto;
            position: absolute;
            width: 100%;
            height: auto;
            top: 90px;
            bottom: 20px;
            left: 220px;
        }

        #contents h3 {
            color: rgb(82, 179, 69);
            padding: 20px 20px 20px 20px;
            margin-left: 250px;
            font-size: 20px;
        }

        #stduentTable {
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
            font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande',
            'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
        }

        .login-page .form .login {
            margin-top: -31px;
            margin-bottom: 26px;
        }

        .w-m-full {
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
            background-image: linear-gradient(45deg, #328f8a, #08ac4b);
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

        .bottom-10 {
            bottom: 10%;
        }

        .login-page-body {
            background-color: #328f8a;
            background-image: linear-gradient(45deg, #328f8a, #08ac4b);
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
            height: 100vh;
            width: 200px;
            position: absolute;
            z-index: 0;
            left: 0;
            font-family: "Roboto", sans-serif;
            /* background-color: rgb(154, 255, 133); */
            overflow-x: hidden;
            padding-top: 100px;
            float: left;
            background: #5294ff;
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
            background: #bae9ff;
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
            .sidenav {
                padding-top: 15px;
            }

            .sidenav a {
                font-size: 18px;
            }
        }
    </style>

</head>
<body>

<nav
        class="navbar navbar-expand-lg bg-white px-5 py-3 border-bottom w-m-full fixed-top">
    <div class="container-fluid w-m-full ">
        <a class="navbar-brand fw-bold fs-5" href="student-register">
            <c:url value="/images/logo.png" var="logo"/>
            <img class="me-2" src="${logo}" alt="Logo" width="45" height="45"/>
            STUDENT REGISTRATION
        </a>

        <div class="d-flex align-items-center">
            <c:url value="${sessionScope.userImg}" var="userPhoto"/>
            <img src="${userPhoto}" class="user-photo1" alt="User Photo">
            <span class="me-3 fw-bold">${sessionScope.username}</span> <span
                class="me-3">|</span>
            <div id="clock" class="me-3"></div>

            <c:url value="/logout" var="logouturl"/>
            <button class="btn btn-outline-danger d-flex justify-content-center align-items-center" type="button"
                    onclick="location.href='${logouturl}'"><i class='bx bx-log-out me-2'></i>Log out
            </button>
        </div>
    </div>
</nav>

<div class="sidenav">

    <c:url value="/students" var="studentList"/>
    <a
            href="${studentList}" class="d-flex  align-items-center"><i class='bx bxs-id-card me-2'></i>Manage
        student</a>

    <c:if test="${sessionScope.isAdmin}">
        <div class="text-white">
            <hr class="mt-0 mb-0 ms-3 me-3">
        </div>

        <c:url var="addCourses" value="/courses"/>
        <a href='${addCourses}' class="d-flex  align-items-center"><i class='bx bxs-category-alt me-2'></i>Manage
            courses</a>


    </c:if>

    <c:url var="users" value="/users"/>
    <c:if test="${sessionScope.isAdmin}">
        <div class="text-white">
            <hr class="mt-0 mb-0 ms-3 me-3 ">
        </div>
        <a href="${users}" class="d-flex  align-items-center"><i class='bx bxs-user-pin me-2'></i>Manage user</a>
    </c:if>
    <c:url var="profile" value="/user/profile"/>

    <div class="text-white">
        <hr class="mt-0 mb-0 ms-3 me-3 ">
    </div>
    <a href='${profile}' class="d-flex  align-items-center">
        <i class='bx bxs-user-circle me-2'></i>Profile</a>


</div>


<div id="testfooter">
    <span>Copyright &#169; Ko Khun 2023</span>


</div>

<script>


    function updateDateTime() {
        const now = new Date();
        const clockElement = document.getElementById('clock');
        const formattedDateTime = now.toLocaleString();
        clockElement.innerHTML = formattedDateTime;
    }

    // Update the date and time every second
    setInterval(updateDateTime, 1000);

    // Initial call to display the date and time immediately
    updateDateTime();


</script>

</body>
</html>