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
                <li class="nav-item">
                    <a class="nav-link" href="/ocs/comment">Comment Page</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/ocs/poll">Polling Page</a>
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
    <hr>
    <div class="card">
        <div class="card-body">
            <h5 class="card-title">General Course Information</h5>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Course Name</th>
                    <th scope="col">Lecturer(s)</th>
                    <th scope="col">Student(s)</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="i" begin="0" end="${courseObject.size()-1}">
                    <tr>
                        <td>${courseObject.get(i).courseID}</td>
                        <td>${courseObject.get(i).courseName}</td>
                        <td>
                            <c:forEach var="j" begin="0" end="${courseObject.get(i).lecturesObject.size()-1}">
                                ${courseObject.get(i).lecturesObject.get(j).fullName}&nbsp;
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach var="j" begin="0" end="${courseObject.get(i).studentsObject.size()-1}">
                                ${courseObject.get(i).studentsObject.get(j).fullName}&nbsp;
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
<hr>
    <div class="card">
        <div class="card-body">
            <h5 class="card-title">General Student Information</h5>
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
        </div>
    </div>
</div>
</body>
<script src="https://cdn.hypernology.com/bootstrap5.0/bootstrap.bundle.min.js"></script>
</html>
