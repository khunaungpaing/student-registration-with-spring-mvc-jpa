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
    
        <form class="row g-3 mt-3 ms-2" action="users" method="get">
            <div class="col-auto">
                <label for="staticEmail2" class="visually-hidden">User Id</label>
                <input type="text" class="form-control" id="staticEmail2" placeholder="User ID"
                value="${sessionScope.searchUserId}" name="userId" required>
            </div>
            <div class="col-auto">
                <label for="inputPassword2" class="visually-hidden">User Name</label>
                <input type="text" class="form-control" id="inputPassword2" placeholder="User Name" 
                value="${sessionScope.searchUserName}" name="username" required>
            </div>
    
            <div class="col-auto">
                <button type="submit" class="btn btn-primary mb-3 d-flex justify-content-center align-items-center"><i class='bx bx-search me-2'></i>Search</button>
            </div>
            <div class="col-auto">
                <button type="button" class="btn btn-success d-flex justify-content-center align-items-center" onclick="location.href = 'users/register';">
                    <i class='bx bx-plus me-2'></i>Add
                </button>
    
            </div>
            <div class="col-auto">
                <button type="reset" class="btn btn-danger mb-3 d-flex justify-content-center align-items-center"
                onclick="location.href='users'"><i class='bx bx-reset me-2' style='color:#ffffff' ></i>Reset</button>
            </div>
        </form>
    
        <table class="table table-striped" id="stduentTable">
            <thead>
                <tr>
                    
                    <th scope="col">User ID</th>
                    <th scope="col">User Name</th>
                    <th scope="col">User Email</th>
                    <th scope="col">Status</th>
                    <th scope="col">Actions</th>
                </tr>
            </thead>
            <tbody>
            	<c:if test="${not empty AllUserLists}">
            	<c:forEach var="user" items="${AllUserLists}" varStatus="loop">
			    <c:set var="statusText" value="${user.status == 1 ? 'Active' : 'Disable'}" />
			    <c:set var="statusBtn" value="${user.status != 1 ? 'Activate' : 'Disable'}" />
			    <c:set var="statusTitle" value="${user.status != 1 ? 'Activate' : 'Deactivate'}" />
			    <c:set var="statusTextColor" value="${user.status == 1 ? 'text-success' : 'text-danger'}" />
			    <c:set var="statusBtnColor" value="${user.status != 1 ? 'btn-success' : 'btn-danger'}" />
			
			    <tr class="align-middle">
			        <th scope="col">${user.id}</th>
			        <td>${user.name}</td>
			        <td>${user.email}</td>
			        <td class="${statusTextColor} fs-6 fw-bold">${statusText}</td>
			        <td>				
			            <button type="button" class="col-2 btn btn-primary btn-sm me-2" onclick="location.href='users/update?userId=${user.id}'">
			               Edit
			            </button>
			            <button type="button" class="col-4 btn ${statusBtnColor} btn-sm" data-bs-toggle="modal"
			                data-bs-target="#exampleModal${loop.index}">${statusBtn}</button>
			        </td>
			    </tr>
			
			    <div class="modal fade" id="exampleModal${loop.index}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			            <div class="modal-dialog modal-dialog-centered">
			                <div class="modal-content">
			                    <div class="modal-header">
			                        <h5 class="modal-title text-danger" id="exampleModalLabel">Are you sure to  ${statusTitle} ${user.id} ${user.name}</h5>
			                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			                    </div>
			                    <div class="modal-footer">
			                        <button type="button" class="btn btn-danger col-md-2" data-bs-dismiss="modal"
			                        onclick="location.href='users?userStatus=${user.status}&userId=${user.id}'">Ok</button>
			    
			                    </div>
			                </div>
			            </div>
        			</div>
	                </c:forEach>
                </c:if>
                <c:if test="${empty AllUserLists}">
                	<tr>
                		<th class="text-center fs-4 table-success" colspan="6">There is no user.</th>
                	</tr>
                </c:if>
    
            </tbody>
        </table>
    
        
    </div>
</div>
        
</body>

</html>

