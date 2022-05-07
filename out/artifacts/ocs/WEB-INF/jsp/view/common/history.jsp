<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<c:if test="${courseSelected != null}">
    <c:redirect url="course?courseSelected=${courseSelected}" />
</c:if>


<head>
    <title>Online Course System | History</title>

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
                        <a class="nav-link" href="/ocs/comment">History</a>
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
            <div class="collapse navbar-collapse" id="">
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
                        <a class="nav-link" href="/ocs/comment">History</a>
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
</div>

<div class="container">

    <div class="card">
        <div class="card-body">
            <h4><strong>Message History</strong></h4>
            <hr />
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Message</th>
                    <th scope="col">Course ID / Poll ID</th>
                    <th scope="col">Sent Time</th>
                    <th scope="col">Page</th>
                    <th scope="col">Message Deleted?</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${comments.size() != 0}">
                        <c:forEach var="i" begin="0" end="${comments.size()-1}">
                            <tr>
                                <td>${comments.get(i).message}</td>
                                <td>${comments.get(i).courseID}</td>
                                <td>${comments.get(i).timestamp}</td>
                                <td>${comments.get(i).page}</td>
                                <td>${comments.get(i).deleted}</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="2">No Comment(s) Found</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>
    <br />
    <div class="card">
        <div class="card-body">
            <h4><strong>Poll History</strong></h4>
            <hr />
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Poll ID</th>
                    <th scope="col">Choice</th>
                    <th scope="col">Sent Time</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${polls.size() != 0}">
                        <c:forEach var="i" begin="0" end="${polls.size()-1}">
                            <tr>
                                <td>${polls.get(i).get(0)}</td>
                                <td>${polls.get(i).get(1)}</td>
                                <td>${polls.get(i).get(2)}</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="2">No Poll(s) Found</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
<script src="https://cdn.hypernology.com/bootstrap5.0/bootstrap.bundle.min.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/31.0.0/classic/ckeditor.js"></script>
</html>
