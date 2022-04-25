<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Online Course System | Lecturer Page</title>
    <link href="https://cdn.hypernology.com/bootstrap5.0/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/ocs"><strong>NB Online Course System</strong></a>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="/ocs">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/ocs/lecturer">User Page</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/ocs/lecturer/cms">Course Management</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/ocs/lecturer/sms">Student Management</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/ocs/course">Course Page</a>
                </li>
            </ul>
            &nbsp;
            <form class="d-flex" action="${pageContext.request.contextPath}/logout">
                <button class="btn btn-outline-success" type="submit">Logout</button>
            </form>
        </div>
    </div>
</nav>

<div class="container">
    <hr>
    <h4>Lecturer Page | User : <security:authentication property="principal.username"/></h4>
    <h5>Student Management System</h5>
    <hr>
    <div class="card">
        <div class="card-body">
            <h5 class="card-title">General Student Information</h5>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Course Name</th>
                    <th scope="col">Lecturer(s)</th>
                    <th scope="col">Student(s)</th>
                </tr>
                </thead>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Username</th>
                        <th scope="col">Full Name</th>
                        <th scope="col">Phone Number</th>
                        <th scope="col">Address</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="i" begin="0" end="${studentObject.size()-1}">
                        <tr>
                            <td>${studentObject.get(i).username}</td>
                            <td>${studentObject.get(i).fullName}</td>
                            <td>${studentObject.get(i).phoneNumber}</td>
                            <td>${studentObject.get(i).address}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </table>
        </div>
    </div>
    <hr />
    <c:if test="${error != null}">
        <div class="panel">
            <div class="alert alert-danger" role="alert">
                    ${error}
            </div>
        </div>
    </c:if>
    <c:if test="${OK != null}">
        <div class="panel">
            <div class="alert alert-success" role="alert">
                    ${OK}
            </div>
        </div>
    </c:if>
    <div class="card">
        <div class="card-body">
    <div class="container">
        <div class="row">
            <div class="col">
                <div class="card" style="width: 100%;">
                    <div class="card-body">
                        <h5 class="card-title">Modify User Information</h5>
                        <form method="GET" action="sms">
                        <div class="row g-3 align-items-center">
                            <div class="col-auto">
                                <label class="col-form-label">User List</label>
                            </div>
                            <div class="col-auto">
                                <select name="sid" class="form-select" aria-label="">
                                    <c:forEach var="i" begin="0" end="${studentObject.size()-1}">
                                        <option value="${studentObject.get(i).uid}">${studentObject.get(i).fullName} (${studentObject.get(i).username})</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-auto">
                                <button type="submit" name="set" value="modify" class="btn btn-primary">Show Information</button>
                            </div>
                        </div>
                        </form>
                        <hr/>
                        <c:if test="${param.set == 'modify'}">
                            <form:form method="POST" action="/ocs/lecturer/sms">
                                <div class="alert alert-primary" role="alert">
                                    You are editing user ${modifyStudentObject.fullName}'s information.
                                </div>
                                <div class="mb-3">
                                    <label for="fullName" class="form-label">Full Name</label>
                                    <input type="text" class="form-control" name="fullName" id="fullName" value="${modifyStudentObject.fullName}">
                                </div>
                                <div class="mb-3">
                                    <label for="phoneNumber" class="form-label">Phone Number</label>
                                    <input type="text" class="form-control" name="phoneNumber" id="phoneNumber" value="${modifyStudentObject.phoneNumber}">
                                </div>
                                <div class="mb-3">
                                    <label for="address" class="form-label">Address</label>
                                    <input type="text" class="form-control" name="address" id="address" value="${modifyStudentObject.address}">
                                </div>
                                <input type="hidden" id="key" name="key" value="${modifyStudentObject.uid}">
                                <button type="submit" name="action" value="update" class="btn btn-outline-success">Submit</button>
                            </form:form>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card" style="width: 100%;">
                    <div class="card-body">
                        <h5 class="card-title">Remove User Information</h5>
                        <form method="GET" action="sms">
                            <div class="row g-3 align-items-center">
                                <div class="col-auto">
                                    <label class="col-form-label">User List</label>
                                </div>
                                <div class="col-auto">
                                    <select name="sid" class="form-select" aria-label="">
                                        <c:forEach var="i" begin="0" end="${studentObject.size()-1}">
                                            <option value="${studentObject.get(i).uid}">${studentObject.get(i).fullName} (${studentObject.get(i).username})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-auto">
                                    <button type="submit" name="set" value="delete" class="btn btn-primary">Show Information</button>
                                </div>
                            </div>
                        </form>
                        <hr/>
                        <c:if test="${param.set == 'delete'}">
                            <form:form method="POST" action="/ocs/lecturer/sms">
                                <div class="row g-3 align-items-center">
                                    <div class="col-auto">
                                        <div class="alert alert-warning" role="alert">
                                            You are deleting user ${deleteStudentObject.fullName}'s information.
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <button type="submit" name="action" value="delete" class="btn btn-outline-dark">Delete Information</button>
                                    </div>
                                    <input type="hidden" id="key" name="key" value="${deleteStudentObject.uid}">
                                </div>
                            </form:form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    </div>
</div>
</body>
<script src="https://cdn.hypernology.com/bootstrap5.0/bootstrap.bundle.min.js"></script>
</html>
