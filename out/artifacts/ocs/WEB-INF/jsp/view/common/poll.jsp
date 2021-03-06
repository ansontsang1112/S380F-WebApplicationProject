<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<c:if test="${pollSelected != null}">
    <c:redirect url="poll?id=${pollSelected}" />
</c:if>


<head>
    <title>Online Course System | Polling Page</title>

    <link href="https://cdn.hypernology.com/bootstrap5.0/bootstrap.min.css" rel="stylesheet">
    <script src="https://i-cdn.hypernology.com/memberPortal/plugins/chart.js/Chart.min.js"></script>

    <style>
        .scroll {
            max-height: 250px;
            overflow-y: auto;
        }
    </style>
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

<div class="container">
    <h4><strong>Course Index</strong></h4><hr/>
    <ul class="nav nav-tabs">
        <c:if test="${pollList.size() != 0}">
            <c:forEach var="i" begin="0" end="${pollList.size()-1}">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="?id=${pollList.get(i).uid}">${pollList.get(i).pollID}</a>
                </li>
            </c:forEach>
        </c:if>
    </ul>
</div>
<br/>

<div class="container">
    <c:choose>
        <c:when test="${id == null}">
            <div class="alert alert-info" role="alert">
                Hi, ${userObject.fullName}. Please select the poll you want to vote by clicking the tag(s).
            </div>
        </c:when>
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
                        <c:when test="${id != null}">
                            <p>Voting States</p>
                            <hr/>
                            <canvas id="voteChart" width="64px" height="15%"></canvas>
                            <script>
                                const labels = [
                                    '${requestedPoll.choice1}',
                                    '${requestedPoll.choice2}',
                                    '${requestedPoll.choice3}',
                                    '${requestedPoll.choice4}'
                                ];

                                const data = {
                                    labels: labels,
                                    datasets: [{
                                        label: '${requestedPoll.question}',
                                        backgroundColor: 'rgb(51, 255, 255)',
                                        borderColor: 'rgb(5, 249, 255)',
                                        data: [
                                            ${qrc.get("choice1")},
                                            ${qrc.get("choice2")},
                                            ${qrc.get("choice3")},
                                            ${qrc.get("choice4")}
                                        ],
                                    }]
                                };

                                const config = {
                                    type: 'bar',
                                    data: data,
                                    options: {
                                        responsive: true
                                    },
                                };

                                const myChart = new Chart(
                                    document.getElementById('voteChart'),
                                    config
                                );
                            </script>
                        </c:when>
                        <c:otherwise>
                            <p style="color:dodgerblue"><strong>No Poll Question Selected</strong></p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <div class="col md-12">
            <div class="card">
                <div class="card-body">
                    <c:choose>
                    <c:when test="${id != null}">
                    <p>Q: <strong>${requestedPoll.question}</strong></p>
                    <hr/>
                    <div class="card">
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${isVoteBefore == 'Y'}">
                                    <div class="mb-3">
                                        <form:form action="/ocs/poll" method="post">
                                            <c:forEach var="i" begin="0" end="${pollChoices.size()-1}">
                                                <div class="form-check">
                                                    <c:choose>
                                                        <c:when test="${pollChoices.get(i) == pollChooseBefore}">
                                                            <input class="form-check-input" type="radio" name="choice" value="${i+1}" id="${i+1}" checked>
                                                            <label class="form-check-label" for="${i+1}">
                                                                    ${pollChoices.get(i)}
                                                            </label>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="form-check-input" type="radio" name="choice" value="${i+1}" id="${i+1}">
                                                            <label class="form-check-label" for="${i+1}">
                                                                    ${pollChoices.get(i)}
                                                            </label>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </c:forEach>
                                            <input type="hidden" id="id" name="id" value="${param.id}">
                                            <input type="hidden" id="update" name="update" value="true">
                                            <input type="hidden" id="polledUID" name="polledUID" value="${polledUID}">
                                            <button type="submit" class="btn btn-primary">Update the poll</button>
                                        </form:form>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <form:form action="/ocs/poll" method="post">
                                        <div class="mb-3">
                                            <c:forEach var="i" begin="0" end="${pollChoices.size()-1}">
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="choice" value="${i+1}" id="${i+1}">
                                                    <label class="form-check-label" for="${i+1}">
                                                            ${pollChoices.get(i)}
                                                    </label>
                                                </div>
                                            </c:forEach>
                                            <input type="hidden" id="id" name="id" value="${param.id}">
                                            <input type="hidden" id="update" name="update" value="false">
                                        </div>
                                        <button type="submit" class="btn btn-primary">Submit the poll</button>
                                    </form:form>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        </c:when>
                        <c:otherwise>
                            <p style="color:dodgerblue"><strong>No Poll Question Selected</strong></p>
                        </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
        <br/>
        <hr />
    </div>
    <div class="container">
        <div class="row">
            <div class="col">
                <div class="container">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-body">
                                    <c:choose>
                                        <c:when test="${id != null}">
                                            <p>You are speaking as "${userObject.username}" at ${requestedPoll.pollID}</p>
                                            <hr/>
                                            <form:form action="/ocs/poll" method="post">
                                                <div class="mb-3">
                                                    <label for="editor" class="form-label">Comments</label>
                                                    <textarea id="editor" name="message" class="form-control"></textarea>
                                                </div>
                                                <input type="hidden" name="pollID" value="${requestedPoll.uid}"/>
                                                <input type="hidden" name="id" value="${requestedPoll.uid}">
                                                <button type="submit" name="action" value="add" class="btn btn-primary">Submit</button>
                                            </form:form>
                                        </c:when>
                                        <c:otherwise>
                                            <p style="color:dodgerblue"><strong>No Poll Selected</strong></p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <hr/>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body scroll">
                        <c:choose>
                            <c:when test="${id != null && comments.size() > 0}">
                                <c:forEach var="i" begin="0" end="${comments.size()-1}">
                                    <div class="card">
                                        <div class="card-body">
                                            <div class="card text-center">
                                                <div class="card-body">
                                                        ${comments.get(i).message}
                                                </div>
                                                <div class="card-footer text-muted">
                                                    <small>By ${comments.get(i).userID}, at ${comments.get(i).timestamp}</small><br>
                                                    <c:if test="${role != 'USER'}">
                                                        <form:form action="/ocs/course">
                                                            <input type="hidden" name="pollID" value="${comments.get(i).courseID}">
                                                            <input type="hidden" name="messageId" value="${comments.get(i).message_id}">
                                                            <button type="submit" name="action" value="delete" class="btn btn-outline-danger">Remove this comment</button>
                                                        </form:form>
                                                    </c:if>
                                                </div>
                                            </div>
                                            <br>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p style="color:dodgerblue"><strong>No Comments</strong></p>
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
