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
        <h3>Online Course System > Access Denied</h3>
        <hr/>
        <div class="alert alert-danger" role="alert">
            <h4 class="alert-heading">Warning!</h4>
            <p>Our system detected you have accessed an unauthorized page. Please let us know if it is an error.</p>
            <p>Error: ${requestScope.error}</p>
            <hr>
            <p class="mb-0">You may try to <a href="login">login</a> or <a href="register">register</a> on our system in order to grant access to certain pages.</p>
        </div>
    </div>
    </body>
    <script src="https://cdn.hypernology.com/bootstrap5.0/bootstrap.bundle.min.js"></script>
</html>
