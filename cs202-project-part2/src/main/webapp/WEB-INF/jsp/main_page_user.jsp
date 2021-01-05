<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 1/1/21
  Time: 1:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<html>
<head>
    <title>main page</title>
    <style>
        body{
            background-image: url("${pageContext.request.contextPath}/resources/library_Background.jpg");
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-position: center;
            background-size: cover;

        }
        h2{
            text-transform: capitalize;
            display: block;
            font-weight: bolder;
            width: 250px;
            text-align: center;
            font-size: 30px;
            color: black;
            font-weight: bolder;
        }
        div{
            width: 170px;
            height: 200px;
            padding: 15px;
            display: block;
            margin: 0 auto;
        }
        div a{
            display: flex;
            justify-content: center;
            align-items: center;
            width: 150px;
            height: 30px;
            margin: 10px;
            color: black;
            font-size: 18px;
            text-transform: none;
            text-decoration: none;
        }
        div a:hover{
            color: black;
            font-weight: bolder;
        }
        #outterbackground{
            /*opacity: 0.8;*/
            background: #2ba7ef;
            width: 250px;
            height: 350px;

            display: block;
            position: absolute;
            top: 15%;
            left: 40%;
            border-radius: 7px;
        }
    </style>
</head>
<body>
    <div id="outterbackground">
        <h2>welcome ${username}</h2>
        <div>
            <a href="/main_page_user/view_books">view books</a>
<%--            <a href="/main_page_user/borrow_books">borrow books</a>--%>
<%--            <a href="/main_page_user/place_hold_request">place hold request</a>--%>
<%--            <a href="/main_page_user/notification_request">notification request</a>--%>
            <a href="/main_page_user/history">history</a>
        </div>
    </div>
</body>
</html>
