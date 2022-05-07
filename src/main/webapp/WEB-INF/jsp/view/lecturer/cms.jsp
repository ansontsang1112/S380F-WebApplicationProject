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

<div class="container">
    <hr>
    <h4>Lecturer Page | User : <security:authentication property="principal.username"/></h4>
    <h5>Course Management System</h5>
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
    <hr />

    <c:if test="${error != null}">
        <div class="panel">
            <div class="alert alert-danger" role="alert">
                    ${error}
            </div>
        </div>
    </c:if>
    <c:if test="${newCourseAdded != null}">
        <div class="panel">
            <div class="alert alert-success" role="alert">
                    ${newCourseAdded}
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
                        <h5 class="card-title">Add Course</h5>
                        <form:form method="post" action="/ocs/lecturer/cms">
                            <div class="mb-3">
                                <label for="courseID" class="form-label">Course ID</label>
                                <input type="text" class="form-control" name="courseID" id="courseID" placeholder="Course ID" aria-describedby="" required>
                            </div>
                            <div class="mb-3">
                                <label for="courseName" class="form-label">Course Name</label>
                                <input type="text" class="form-control" name="courseName" id="courseName" placeholder="Course Name" required>
                            </div>
                            <div class="mb-3">
                                <label for="courseName" class="form-label">Lecturer(s)</label>
                                <select class="form-select" name="lecturers" multiple aria-label="" required>
                                    <c:forEach var="i" begin="0" end="${lecturerList.size()-1}">
                                        <option value="${lecturerList.get(i).username}">${lecturerList.get(i).fullName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="courseName" class="form-label">Student(s)</label>
                                <select class="form-select" name="students" multiple aria-label="" required>
                                    <c:forEach var="i" begin="0" end="${studentList.size()-1}">
                                        <option value="${studentList.get(i).username}">${studentList.get(i).fullName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <button type="submit" name="action" value="add" class="btn btn-primary">Submit new course</button>
                        </form:form>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card" style="width: 100%;">
                        <div class="col">
                            <div class="card" style="width: 100%;">
                                <div class="card-body">
                                    <h5 class="card-title">Modify Course Information</h5>
                                    <form method="GET" action="cms">
                                        <div class="row g-3 align-items-center">
                                            <div class="col-auto">
                                                <label class="col-form-label">Courses List</label>
                                            </div>
                                            <div class="col-auto">
                                                <select name="cid" class="form-select" aria-label="">
                                                    <c:forEach var="i" begin="0" end="${courseObject.size()-1}">
                                                        <option value="${courseObject.get(i).id}">${courseObject.get(i).courseName} (${courseObject.get(i).courseID})</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-auto">
                                                <button type="submit" name="set" value="modify" class="btn btn-primary">Show Information</button>
                                            </div>
                                        </div>
                                    </form>
                                    <hr/>
                                    <c:if test="${param.set == 'modify' && param.cid != null}">
                                        <form:form method="POST" action="/ocs/lecturer/cms">
                                            <div class="alert alert-primary" role="alert">
                                                You are editing the course ${modifyCourseObject.courseID}'s information.
                                            </div>
                                            <div class="mb-3">
                                                <label for="courseName" class="form-label">Course Name</label>
                                                <input type="text" class="form-control" name="courseName" id="courseName_" value="${modifyCourseObject.courseName}">
                                            </div>
                                            <div class="mb-3">
                                                <label for="lecturers_org" class="form-label">Lecturers</label>
                                                <input type="text" class="form-control" id="lecturers_org" value="${modifyCourseObject.serializedLectures}" disabled>
                                                <select class="form-select" name="lecturers" multiple aria-label="">
                                                    <c:forEach var="i" begin="0" end="${lecturerList.size()-1}">
                                                        <option value="${lecturerList.get(i).username}">${lecturerList.get(i).fullName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="mb-3">
                                                <label for="students_org" class="form-label">Students</label>
                                                <input type="text" class="form-control" id="students_org" value="${modifyCourseObject.serializedStudents}" disabled>
                                                <select class="form-select" name="students" multiple aria-label="">
                                                    <c:forEach var="i" begin="0" end="${studentList.size()-1}">
                                                        <option value="${studentList.get(i).username}">${studentList.get(i).fullName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <input type="hidden" id="key" name="key" value="${modifyCourseObject.id}">
                                            <button type="submit" name="action" value="update" class="btn btn-outline-success">Submit</button>
                                        </form:form>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                </div>
            </div>
            <div class="col">
                <div class="card" style="width: 100%;">
                    <div class="card-body">
                        <h5 class="card-title">Remove Course</h5>
                        <form method="GET" action="cms">
                            <div class="row g-3 align-items-center">
                                <div class="col-auto">
                                    <label class="col-form-label">Courses List</label>
                                </div>
                                <div class="col-auto">
                                    <select name="cid" class="form-select" aria-label="">
                                        <c:forEach var="i" begin="0" end="${courseObject.size()-1}">
                                            <option value="${courseObject.get(i).id}">${courseObject.get(i).courseName} (${courseObject.get(i).courseID})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-auto">
                                    <button type="submit" name="set" value="delete" class="btn btn-warning">Delete the Course</button>
                                </div>
                            </div>
                        </form>
                        <hr/>
                        <c:if test="${param.set == 'delete'}">
                            <form:form method="POST" action="/ocs/lecturer/cms">
                                <div class="row g-3 align-items-center">
                                    <div class="col-auto">
                                        <div class="alert alert-warning" role="alert">
                                            You are deleting course ${deleteCourseObject.courseName}'s information.
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <button type="submit" name="action" value="delete" class="btn btn-outline-dark">Delete Information</button>
                                    </div>
                                    <input type="hidden" id="key" name="key" value="${deleteCourseObject.id}">
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
