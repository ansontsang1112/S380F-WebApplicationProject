<!DOCTYPE html>
<html>
    <head>
        <title>Online Course System | Login</title>
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
        <h3>Online Course System > Login</h3>
        <br/>
        <c:if test="${param.error != null}">
            <div class="alert alert-danger" role="alert">
                <strong>Account or Password failed</strong>
            </div>

        </c:if>
        <c:if test="${param.logout != null}">
            <div class="alert alert-success" role="alert">
                You have logged out
            </div>
        </c:if>

        <form action="login" method="POST">
            <label for="username" class="form-label">Username:</label><br/>
            <input type="text" id="username" name="username" class="form-label" required /><br/><br/>

            <label for="password" class="form-label">Password:</label><br/>
            <input type="password" id="password" name="password" class="form-label" required /><br/><br/>

            <input type="checkbox" id="remember-me" name="remember-me" />
            <label for="remember-me">Remember me</label><br/><br/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <table>
                <tr>
                    <td><input type="submit" class="btn btn-outline-primary" value="Login to online course system"/></td>
                    <td><a href="register"><input type="button" class="btn btn-outline-primary" value="Don't have an account?"/></a></td>
                </tr>
            </table>
        </form>
    </div>
    </body>
    <script src="https://cdn.hypernology.com/bootstrap5.0/bootstrap.bundle.min.js"></script>
</html>
