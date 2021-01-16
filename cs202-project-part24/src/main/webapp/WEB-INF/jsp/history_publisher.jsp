<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 1/8/21
  Time: 3:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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
        .innerFilterDiv{
            display: flex;
            justify-content: left;
            /*align-items:;*/
            margin-left: 35px;
            width: 95%;
            height: 40px;


        }
        .innerFilterDiv label{
            margin-right: 10px;
        }
        .innerFilterDiv select{
            margin-right: 10px;
            height: 20px;
        }
        .innerFilterDiv p{
            margin-right: 10px;
            font-size: 25px;
            position: absolute;
            top: 25px;
            left: 350px;

        }
        #statisticsDiv h3,#viewTable h3{
            text-transform: uppercase;
            padding-left: 10px;
        }
        #viewTable{
            background: #2ba7ef;
            width: 90%;
            height: auto;
            max-height: 350px;
            display: block;
            position: absolute;
            top: 10%;
            left: 5%;
            border-radius: 7px;
            overflow: auto;

        }

        #viewTable2{
            background: #2ba7ef;
            width: 90%;
            height: auto;
            max-height: 350px;
            display: block;
            position: absolute;
            top: 70%;
            left: 5%;
            border-radius: 7px;
            overflow: auto;

        }
        table{
            border-collapse: collapse;
            /*table-layout:fixed;*/
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
            width: 25%;
        }

        table td{
            /* border: 1px solid black; */
            padding: 4px 8px;
            text-align: center;
            border-bottom: 1px solid black;
            white-space: normal;
            word-wrap: break-word;
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
        #whichTable{
            margin-left:35px ;
            margin-bottom: 20px;
        }
        #viewTable h3,#viewTable2 h3{
            text-transform: uppercase;
            padding-left: 10px;
        }
    </style>
</head>
<body>
<div id="viewTable">
    <h3>Transactions on your books</h3>
    <table>
        <tr>
            <th>ISBN</th>
            <th>title</th>
            <th>Transaction type</th>
            <th>created by</th>
            <th>created on</th>
        </tr>

        <%
            String[][] data = (String[][]) session.getAttribute("PublisherBookHistory");
            if (data != null)
            {
                for (String[] item : data)
                {
        %>
        <tr>
            <% for (String temp : item) {
            %>
            <td> <%= temp %></td>
            <%
                }
            %>


        </tr>
        <%
                }
            }
        %>
    </table>

</div>
<div id="viewTable2" style="top: 110%">
    <h3>Your add/remove book requests</h3>
    <table>
        <tr>
            <th>ISBN</th>
            <th>title</th>
            <th>request type</th>
            <th>status</th>
            <th>quantity</th>
            <th>created on</th>
        </tr>

        <%
            String[][] data2 = (String[][]) session.getAttribute("PublisherRequestHistory");
            if (data2 != null)
            {
                for (String[] item : data2)
                {
        %>
        <tr>
            <% for (String temp : item) {
            %>
            <td> <%= temp %></td>
            <%
                }
            %>


        </tr>
        <%
                }
            }
        %>

    </table>
</div>
</body>
</html>
