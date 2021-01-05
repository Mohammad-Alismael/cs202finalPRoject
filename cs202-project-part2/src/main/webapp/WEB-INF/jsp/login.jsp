<%--
  Created by IntelliJ IDEA.
  User: freedrone
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Login to System</title>
    <link rel="stylesheet" href="../css/login.css">
</head>
<style>
    h2{
        text-transform: capitalize;
        position: absolute;
        left: 40%;
        top: 10%;
    }
    #loginForm{
        display: block;
        width: 170px;
        height: 150px;
        padding: 15px;
        margin: 200px auto;
        background: sandybrown;
    }

    #loginForm form input[placeholder = "username"],#loginForm form input[placeholder = "password"]{
        width: 150px;
        height: 30px;
        margin: 0px 10px 0px 10px;
    }

    #loginForm form input[placeholder = "username"]{
        margin: 10px 10px 5px 10px;
    }

    #loginForm form input[placeholder = "password"]{
        margin: 5px 10px 5px 10px;
    }

    #loginForm form input[type = "submit"]{
        width: 150px;
        height: 30px;
        margin: 5px 10px 5px 10px;
    }
    #signinLink{
        margin: -5px 10px 5px 20px;
    }
</style>
<body>
<%
    String username = (String) session.getAttribute("username");
    if (username == null)
    {
%>
<h2>Library management system</h2>
<div id="loginForm">
    <form method="post">
        <input type="text" name="username" placeholder="username"/>
        <input type="password" name="password" placeholder="password"/>
        <input type="submit" value="Login" />
    </form>
    <%
        if ((Boolean) session.getAttribute("errorStatus")){
        %>
            <script type="text/javascript">
                    window.onload = function () {
                alert("invalid credentials");
            };
            </script>
     <%   }
    %>

    <div id="signinLink">
        not registered?<a href="/sign_up">sign up</a>
    </div>
</div>

<%
    } else {
%>
<p>You are logged in as : <%= username %> </p>
<a href="/logout">Logout</a>
<%
    }
%>
</body>
</html>
