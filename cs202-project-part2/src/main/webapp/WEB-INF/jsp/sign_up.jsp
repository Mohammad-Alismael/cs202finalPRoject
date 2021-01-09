<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 12/31/20
  Time: 6:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>sign up</title>
</head>
<style>
    h2{
        text-transform: capitalize;
        position: absolute;
        left: 47%;
        top: 10%;
    }
    #signInForm{
        width: 170px;
        height: 300px;
        padding: 15px;
        margin: 200px auto;
        background: sandybrown;
        display: block;
    }
    #signInForm form{

    }

    #signInForm form input{
        display: flex;
        justify-content: center;
        align-items: center;
        width: 150px;
        height: 30px;
        margin: 10px;
    }
</style>
<body>
<h2>sign_up</h2>

    <div id="signInForm">
        <form method="post">
            <input type="text" name="username" placeholder="username"/>
            <input type="text" name="name" placeholder="name"/>
            <input type="text" name="surname" placeholder="surname"/>
            <input type="password" name="password" placeholder="password"/>
            <input type="email" name="email" placeholder="email" />
            <input type="text" name="phoneNumber" placeholder="phone number">
            <input type="submit" value="Sign up" />
        </form>
    </div>

<%--    <button onclick="[window.location.href='/change-status']"> change status</button>--%>
<%
    String feedback = (String) session.getAttribute("feedback");
    if (feedback != null ){
%>
<script type="text/javascript">
    window.onload = function () {
        alert("${feedback}");
    };
</script>
<% } %>
</body>
</html>
