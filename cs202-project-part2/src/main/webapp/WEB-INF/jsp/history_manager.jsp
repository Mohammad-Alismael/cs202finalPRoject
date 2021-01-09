<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 1/9/21
  Time: 10:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>history manager</title>
    <style>
        body{
            background-image: url("${pageContext.request.contextPath}/resources/library_Background.jpg");
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-position: center;
            background-size: cover;
        }
        #viewTable{
            background: #2ba7ef;
            width: 90%;
            height: auto;
            max-height: 350px;
            display: block;
            position: absolute;
            top: 35%;
            left: 5%;
            border-radius: 7px;
            overflow: auto;
        }
        table{
            border-collapse: collapse;
            width: 100%;
            border-bottom: 3px solid black;
            border-left: 3px solid black;
            border-right: 3px solid black;

        }
        table th{
            text-align: center;
            background-color: #000017;
            color: white;
            padding: 2px 30px 2px 8px;
            text-transform: uppercase;
        }

        table td{
            /* border: 1px solid black; */
            padding: 4px 8px;
            text-align: center;
            border-bottom: 1px solid black;
        }
        table td:nth-child(1){
            font-weight: bolder;
            font-size: 20px;
        }
        table tr:nth-child(even){
            background-color: #0878b1;
        }

        table td p{
            margin: 0;
            padding: 0;
        }
        #viewTable h3,#selectBook h3{
            text-transform: uppercase;
            padding-left: 10px;
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
        .innerFilterDiv{
            display: flex;
            justify-content: left;
            /*align-items:;*/
            margin-left: 35px;
            width: 95%;
            height: 30px;
            border-bottom: 1px solid black;

        }
    </style>
</head>
<body>
<div id="statisticsDiv">
    <h3>favorites</h3>
    <div class="innerFilterDiv">
        <label>include</label>
        <select id="whichGroup">
            <option>borrow</option>
            <option>purchase</option>
            <option>both</option>
        </select>
<%--        <label>type</label>--%>
<%--        <select id="whichType">--%>
<%--            <option>author</option>--%>
<%--            <option>title</option>--%>
<%--            <option> genre </option>--%>
<%--            <option> topic </option>--%>
<%--            <option> publisher </option>--%>
<%--        </select>--%>
        <p>....result....</p>

    </div>
    <div class="innerFilterDiv">
        <label>Which books are borrowed are borrowed the most last 3 months</label>
        <table>
            <tr>
                <th>ISBN</th>
                <th>title</th>
            </tr>
        </table>
    </div>
    <div class="innerFilterDiv">
        <label>Which publisher has most borrowed books (all time): ${username}</label>
    </div>
</div>
    <div id="viewTable">
        <h3>user's info who borrowed the most borrowed book</h3>
        <table>
            <tr>
                <th>username</th>
                <th>name</th>
                <th>name</th>
                <th>surname</th>
                <th>email</th>
                <th>phone number</th>
            </tr>
        </table>
    </div>
    <div id="viewTable" style="top: 110%">
        <h3>users overdue a borrowed book</h3>
        <table>
            <tr>
                <th>username</th>
                <th>name</th>
                <th>name</th>
                <th>surname</th>
                <th>email</th>
                <th>phone number</th>
                <th>ISBN</th>
                <th>title</th>
                <th>overdue books</th>
                <th>returned a book</th>
            </tr>
        </table>
    </div>
</body>
</html>
