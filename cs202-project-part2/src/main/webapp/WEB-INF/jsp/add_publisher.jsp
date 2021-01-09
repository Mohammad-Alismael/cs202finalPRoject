<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 1/8/21
  Time: 4:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add publisher</title>
    <style>
        body{
            background-image: url("${pageContext.request.contextPath}/resources/library_Background.jpg");
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-position: center;
            background-size: cover;

        }

        #signInForm{
            width: 170px;
            height: 320px;
            padding: 15px;
            margin: 170px auto;
            background: #2ba7ef;
            display: block;
        }
        #signInForm h2{
          display: block;
            margin: 10px auto;
            margin-left: 11px;
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
</head>
<body>


<div id="signInForm">
    <h2>add publisher</h2>
    <form method="post">

        <input type="text" name="username" placeholder="username"/>
        <input type="text" name="company_name" placeholder="company name"/>
        <input type="text" name="address" placeholder="address"/>
        <input type="password" name="password" placeholder="password"/>
        <input type="email" name="email" placeholder="email" />
        <input type="text" name="phoneNumber" placeholder="phone number">

        <input type="submit" value="add publisher" />
    </form>
</div>

</body>
</html>
