<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<c:if test="${courseSelected != null}">
    <c:redirect url="course?courseSelected=${courseSelected}" />
</c:if>


<head>
    <c:choose>
        <c:when test="${courseSelected}">
            <title>Online Course System | Course, ${courseSelected}</title>
        </c:when>
        <c:otherwise>
            <title>Online Course System | Course Page</title>
        </c:otherwise>
    </c:choose>

    <style>
        .scroll {
            max-height: 200px;
            overflow-y: auto;
        }
    </style>

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

    <div class="container">
        <h4><strong>Course Index</strong></h4><hr/>
            <ul class="nav nav-tabs">
                <c:forEach var="i" begin="0" end="${courseObjectByUser.size()-1}">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="?courseSelected=${courseObjectByUser.get(i).courseID}">${courseObjectByUser.get(i).courseID}</a>
                    </li>
                </c:forEach>
            </ul>
    </div>
<br/>
</div>

<div class="container">
    <c:choose>
        <c:when test="${courseRequestedByUser == null}">
            <div class="alert alert-info" role="alert">
                Hi, ${userObject.fullName}. Please select the course you want to access by clicking the tag(s).
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info" role="alert">
                Hi, ${userObject.fullName}. Welcome to <span style="color: dodgerblue">${courseRequestedByUser.courseName}</span>.
            </div>
        </c:otherwise>
    </c:choose>
    <c:if test="${error}">
        <div class="alert alert-danger" role="alert">
            Error Occur: ${error}
        </div>
    </c:if>
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <c:choose>
                        <c:when test="${courseRequestedByUser != null}">
                            <p>Course Material(s)</p>
                            <hr/>
                        </c:when>
                        <c:otherwise>
                            <p style="color:dodgerblue"><strong>No Course Selected</strong></p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <c:choose>
                        <c:when test="${courseRequestedByUser != null}">
                            <p>Course Comment / Messages</p>
                            <hr/>
                        <div class="card">
                            <div class="card-body scroll">
                            <c:choose>
                                <c:when test="${courseComments.size() != 0}">
                                    <c:forEach var="i" begin="0" end="${courseComments.size()-1}">
                                        <div class="card text-center">
                                            <div class="card-body">
                                                ${courseComments.get(i).message}
                                            </div>
                                            <div class="card-footer text-muted">
                                                <small>By ${courseComments.get(i).userID}, at ${courseComments.get(i).timestamp}</small>
                                            </div>
                                        </div>
                                        <br>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <p>Leave a comment on this course!!</p>
                                </c:otherwise>
                            </c:choose>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p style="color:dodgerblue"><strong>No Course Selected</strong></p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    </div>

    <hr/>

    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${courseRequestedByUser != null}">
                                <p>You are speaking as "${userObject.username}" course ${param.courseSelected}</p>
                                <hr/>
                                <form:form action="/ocs/course" method="post">
                                    <div class="mb-3">
                                        <label for="editor" class="form-label">Comment / Message</label>
                                        <textarea id="editor" name="message" class="form-control"></textarea>
                                    </div>
                                    <input type="hidden" name="courseID" value="${param.courseSelected}"/>
                                    <button type="submit" name="action" value="add" class="btn btn-primary">Submit</button>
                                </form:form>
                            </c:when>
                            <c:otherwise>
                                <p style="color:dodgerblue"><strong>No Course Selected</strong></p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script src="https://cdn.hypernology.com/bootstrap5.0/bootstrap.bundle.min.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/31.0.0/classic/ckeditor.js"></script>

<script>
    ClassicEditor
        .create(document.querySelector('#editor'))
        .catch(error => {
            console.error(error);
        });
</script>
</html>
