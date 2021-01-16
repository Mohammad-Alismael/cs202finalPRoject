<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 1/6/21
  Time: 4:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>    <style>
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
        <a href="/main_page_manager/view_books_manager">view books</a>
        <a href="/main_page_user/place_hold_request">manage books</a>
        <a href="/main_page_manager/add_publisher">add publisher</a>
        <a href="/main_page_manager/history_manager">history(for all users)</a>
        <a href="/logout">log out</a>
    </div>

</div>

</body>
</html>
