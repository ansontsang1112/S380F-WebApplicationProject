<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Online Course System | Index</title>

    <link href="https://cdn.hypernology.com/bootstrap5.0/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<c:if test="${role == 'LECTURER'}">
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
</c:if>
<c:if test="${role == 'USER'}">
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
</c:if>
<c:if test="${role == null}">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="/ocs"><strong>NB Online Course System</strong></a>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="/ocs">Home</a>
                    </li>
                </ul>
                <form class="d-flex" action="register">
                    <button class="btn btn-outline-success" type="submit">Register</button>
                </form>
                &nbsp;
                <form class="d-flex" action="login">
                    <button class="btn btn-outline-success" type="submit">Login</button>
                </form>
            </div>
        </div>
    </nav>
</c:if>


    <div class="container">
        <h3><strong>Welcome to S380F NB Online Course System</strong></h3><hr/>
        <div class="row">
            <div class="col">
                <h5>Course List</h5>
                <hr>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Course ID</th>
                        <th>Course</th>
                    </tr>
                    <c:forEach var="i" begin="0" end="${courseObject.size()-1}">
                        <tr>
                            <td>${courseObject.get(i).courseID}</td>
                            <td>${courseObject.get(i).courseName}</td>
                        </tr>
                    </c:forEach>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
            <div class="col">
                <h5>Our wonderful lectures</h5>
                <p>We currently have <strong>${lecturersList.size()}</strong> wonderful lecture(s)!</p>
                <hr/>
                <ul>
                    <c:forEach var="i" begin="0" end="${lecturersList.size()-1}">
                        <li>${lecturersList.get(i)}</li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>

</body>
<script src="https://cdn.hypernology.com/bootstrap5.0/bootstrap.bundle.min.js"></script>
</html>
