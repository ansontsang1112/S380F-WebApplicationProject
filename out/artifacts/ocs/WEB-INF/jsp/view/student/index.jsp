<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Online Course System | Student Page</title>
    <link href="https://cdn.hypernology.com/bootstrap5.0/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/ocs"><strong>NB Online Course System</strong></a>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/ocs">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/ocs/student">User Page</a>
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
    <h4>Student Page | Current Session: <security:authentication property="principal.username"/></h4>
    <hr/>
    <div class="container">
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <h5>Personal Information</h5>
                        <table class="table">
                            <tbody>
                            <tr>
                                <th scope="row">Full Name</th>
                                <td>${userObject.fullName}</td>
                            </tr>
                            <tr>
                                <th scope="row">Phone Number</th>
                                <td>${userObject.phoneNumber}</td>
                            </tr>
                            <tr>
                                <th scope="row">Address</th>
                                <td>${userObject.address}</td>
                            </tr>
                            <tr>
                                <th scope="row">Password</th>
                                <td>${userObject.password}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="col">
                <c:if test="${error != null}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </c:if>
                <c:if test="${OK != null}">
                    <div class="alert alert-success" role="alert">
                        ${OK}
                    </div>
                </c:if>
                    <div class="accordion" id="edit">
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingOne">
                                <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-controls="collapseOne">
                                    Modify your personal information
                                </button>
                            </h2>
                            <div id="collapseOne" class="accordion-collapse collapse" aria-labelledby="headingOne" data-bs-parent="#edit">
                                <div class="accordion-body">
                                    <form:form method="post" action="/ocs/student/">
                                        <div class="mb-3">
                                            <label class="form-label">Full Name</label>
                                            <input type="text" class="form-control" name="fullName" value="${userObject.fullName}">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Phone Number</label>
                                            <input type="text" class="form-control" name="phoneNumber" value="${userObject.phoneNumber}">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Address</label>
                                            <input type="text" class="form-control" name="address" value="${userObject.address}">
                                        </div>
                                        <input type="hidden" id="key" name="key" value="${userObject.uid}">
                                        <button type="submit" name="action" value="updateInfo" class="btn btn-primary">Update Personal Info</button>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingThree">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                    Change your password
                                </button>
                            </h2>
                            <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree" data-bs-parent="#edit">
                                <div class="accordion-body">
                                    <form:form method="post" action="/ocs/student/">
                                        <div class="mb-3">
                                            <label class="form-label">Original Password</label>
                                            <input type="password" class="form-control" name="oPassword">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">New Password</label>
                                            <input type="password" class="form-control" name="nPassword">
                                        </div>
                                        <input type="hidden" id="key" name="key" value="${userObject.uid}">
                                        <button type="submit" name="action" value="updatePassword" class="btn btn-primary">Update Personal Info</button>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                    </div>
            </div>
        </div>
        <hr/>
        <div class="card">
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Course Name</th>
                        <th scope="col">Lecturer(s)</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${courseObjectByUser != null}">
                        <c:forEach var="i" begin="0" end="${courseObjectByUser.size()-1}">
                            <tr>
                                <td><h5>
                                    <a href="/ocs/course"><span class="badge bg-success">${courseObjectByUser.get(i).courseID}</span></a>
                                </h5></td>
                                <td>${courseObjectByUser.get(i).courseName}</td>
                                <td>${courseObjectByUser.get(i).serializedLectures}</td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${courseObjectByUser == null}">
                        <tr>
                            <td colspan="4">No course registered.</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


</body>
<script src="https://cdn.hypernology.com/bootstrap5.0/bootstrap.bundle.min.js"></script>
</html>
