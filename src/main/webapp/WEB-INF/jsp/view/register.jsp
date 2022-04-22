<!DOCTYPE html>
<html>
<head>
    <title>Online Course System | Student Register</title>
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

<div class="container">
    <h3>Online Course System > Register</h3>

    <c:if test = "${param.get('error') != null}">
        <div class="alert alert-danger" role="alert">
            <p>Some unexpected error happens. Your input missed something. (Missed: ${param.get('missing').toString()})</p>
        </div>
    </c:if>
<br/>
<form:form method="POST" action="/ocs/register" modelAttribute="registerModel">
    <table>
        <tr>
            <td colspan="2"><h5>Login Information</h5></td>
        </tr>
        <tr>
            <td><form:label path="username">Username</form:label></td>
            <td><form:input type="text" path="username"/></td>
        </tr>
        <tr>
            <td><form:label path="password">Password</form:label></td>
            <td><form:input type="password" path="password" /></td>
        </tr>

        <tr>
            <td colspan="2"><h5>Student Information</h5></td>
        </tr>
        <tr>
            <td><form:label path="fullName">Full Name</form:label></td>
            <td><form:input type="text" path="fullName" /></td>
        </tr>
        <tr>
            <td><form:label path="phoneNumber">Phone Number</form:label></td>
            <td><form:input type="text" path="phoneNumber" /></td>
        </tr>
        <tr>
            <td><form:label path="address">Address</form:label></td>
            <td><form:input type="text" path="address" /></td>
        </tr>
    </table>
    <hr>
    <table>
        <tr>
            <td><input type="submit" class="btn btn-outline-success" value="Submit Registration"/></td>
            <td><a href="login"><input type="button" class="btn btn-outline-primary" value="Already have an account?"/></a></td>
        </tr>
    </table>

</form:form>
</div>
</body>
<script src="https://cdn.hypernology.com/bootstrap5.0/bootstrap.bundle.min.js"></script>
</html>
