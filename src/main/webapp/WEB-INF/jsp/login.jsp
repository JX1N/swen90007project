<%
    String path = request.getContextPath();
    String scheme;
    if (request.getHeader("x-forwarded-proto") != null) {
        scheme = request.getHeader("x-forwarded-proto");
    } else {
        scheme = request.getScheme();
    }

    String basePath;
    if (request.getServerPort() != 80) {
        basePath = scheme + "://" + request.getServerName() + ":"+request.getServerPort()+path + "/";
    } else {
        basePath = scheme + "://" + request.getServerName() + path + "/";
    }
    pageContext.setAttribute("basePath",basePath);
%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Login</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <link href="${pageScope.basePath}css/signin.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>

<body class="text-center">
    <form class="form-signin" method="post" action="login">
    <h1 class="h3 mb-3 font-weight-normal">Log In</h1>
        <% if (request.getAttribute("fail") != null && (boolean)request.getAttribute("fail")) { %>
            <div class="incorrect">
                Incorrect username or password.
            </div>
        <% } %>
    <label for="username" class="sr-only">Username</label>
    <input type="text" id="username" name="username" class="form-control" placeholder="Username" required autofocus>
    <label for="password" class="sr-only">Password</label>
    <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
    <p class="mt-5 mb-3 text-muted">&copy; 2017-2018</p>
    </form>

</body>
</html>