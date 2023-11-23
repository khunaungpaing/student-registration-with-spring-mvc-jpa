<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Student Registration</title>
    <style>
        @import url('https://fonts.googleapis.com/css?family=Montserrat');

        * {
            box-sizing: border-box;
        }

        body {
            padding-left: 200px;
            background-color: #c2d7ff;
            font-family: Montserrat, sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            min-height: 100vh;
            margin: 0;
        }

        h3 {
            margin: 10px 0;
        }

        h6 {
            margin: 5px 0;
            text-transform: uppercase;
        }

        p {
            font-size: 14px;
            line-height: 21px;
        }

        .card-container {
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0px 10px 20px -10px rgba(40, 33, 33, 0.75);
            color: #B3B8CD;
            position: relative;
            width: 380px;
            min-height: 100%;
            max-width: 100%;
            text-align: center;
            padding-top: 50px;
        }

        .card-container .pro {
            color: #231E39;
            background-color: #FEBB0B;
            border-radius: 3px;
            font-size: 14px;
            font-weight: bold;
            padding: 3px 7px;
            position: absolute;
            top: 30px;
            left: 30px;
        }

        .card-container .round {
            border: 1px solid #03BFCB;
            border-radius: 50%;
            padding: 7px;
        }

        button.primary {
            background-color: #5294ff;
            border: 1px solid #5294ff;
            border-radius: 3px;
            color: #ffffff;
            font-family: Roboto, sans-serif;
            font-weight: 500;
            padding: 10px 25px;
            cursor: pointer;
        }

        button.primary.ghost {
            background-color: transparent;
            color: #5294ff;
        }

        button.primary:hover {
            background: #3577ff;
            color: white;
        }

        .skills {
            background-color: #5294ff;
            padding-inline: 20px;
            padding-block: 15px;
            margin-top: 30px;
            border-radius: 0 0 10px 10px;
            text-align: center;
            color: #ffffff;
        }

        .skills ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
        }

        .skills ul li {
            border: 1px solid #2D2747;
            border-radius: 2px;
            display: inline-block;
            font-size: 12px;
            margin: 0 7px 7px 0;
            padding: 7px;
        }

        .text-lblue {
            margin-top: 10px;
            color: #66a0ff;
        }

        .profile-pic {
            width: 150px;
            height: 150px;
            object-fit: cover;
        }

    </style>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<div class="card-container">
    <c:url value="${sessionScope.userImg}" var="userPhoto"/>
    <img class="round profile-pic" src="${userPhoto}" alt="user"/>
    <h3 class="text-lblue">${sessionScope.username}</h3>
    <h6>Role : ${sessionScope.isAdmin? "ADMIN":"USER"}</h6>
    <div class="mt-4">
        <button type="button" class="primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
            Reset Password
        </button>
        <button type="button" class="primary">
            change profile
        </button>
    </div>
    <div class="skills">
        <h6>Authorize</h6>
        <c:if test="${sessionScope.isAdmin}">
            <p>Admin can manage user, course and student.</p>
        </c:if>
        <c:if test="${not sessionScope.isAdmin}">
            <p>User can only manage student.</p>
        </c:if>

    </div>
</div>

<!-- Modal -->

<form action="reset-password" id="password-form" method="post">
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Reset Password</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row mb-4">
                        <div class="col-md-1"></div>
                        <label for="oldpassword" class="col-md-4 col-form-label">Current Password</label>
                        <div class="col-md-6">
                            <input type="password" class="form-control" id="oldpassword"
                                   name="oldpass" placeholder="Current Password" required>
                        </div>
                    </div>
                    <div class="row mb-4">
                        <div class="col-md-1"></div>
                        <label for="apassword" class="col-md-4 col-form-label">Passowrd</label>
                        <div class="col-md-6">
                            <input type="password" class="form-control" id="apassword"
                                   name="pass" placeholder="Password" required>
                        </div>
                    </div>
                    <div class="row mb-4">
                        <div class="col-md-1"></div>
                        <label for="aconfirm-password" class="col-md-4 col-form-label">Confirm
                            Passowrd</label>
                        <div class="col-md-6">
                            <input type="password" class="form-control" id="aconfirm-password"
                                   name="comfirm" placeholder="Confirm password" required>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
<script>


    //check password
    const form = document.getElementById('password-form');
    const passwordInput = form.querySelector('#apassword');
    const confirmPasswordInput = form.querySelector('#aconfirm-password');

    form.addEventListener('submit', function (event) {
        if (passwordInput.value !== confirmPasswordInput.value) {
            alert("Passwords do not match!");
            event.preventDefault();
        } else if (passwordInput.value.length < 8) {
            alert("Password must be at least 8 characters long!");
            event.preventDefault();
        }
    });
</script>
</html>