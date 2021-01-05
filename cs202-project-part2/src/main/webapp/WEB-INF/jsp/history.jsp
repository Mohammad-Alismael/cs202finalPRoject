<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 1/1/21
  Time: 9:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>history page</title>
    <style>
        body{
            background-image: url("${pageContext.request.contextPath}/resources/library_Background.jpg");
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-position: center;
            background-size: cover;
        }
        #statisticsDiv{
            background: #2ba7ef;
            width: 90%;
            max-height: 350px;
            display: block;
            position: absolute;
            top: 5%;
            left: 5%;
            border-radius: 7px;
            padding-bottom: 5px;
        }
        #statisticsDiv h3{
            text-transform: uppercase;
            padding-left: 10px;
        }
    </style>
</head>
<body>
<div id="statisticsDiv">
    <h3>statistics</h3>
    <div class="innerFilterDiv">
        <label>sort by</label>
        <select id="whichGroup">
            <option>author</option>
            <option>title</option>
            <option> genre </option>
            <option> topic </option>
            <option> publisher </option>
        </select>
    </div>
</body>
</html>
