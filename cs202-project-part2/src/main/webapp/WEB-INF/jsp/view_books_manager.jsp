<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 1/9/21
  Time: 1:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>view books manager</title>
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
        #filterDiv{
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
        #selectBook{
            background: #2ba7ef;
            width: 90%;
            max-height: 350px;
            display: block;
            position: absolute;
            top: 80%;
            left: 5%;
            border-radius: 7px;
            padding-bottom: 5px;

        }
        #filterDiv h3,#selectBook h3{
            text-transform: uppercase;
            padding-left: 10px;
        }
        .innerFilterDiv{
            display: flex;
            justify-content: left;
            /*align-items:;*/
            margin-left: 35px;
            width: 95%;
            border-bottom: 0.5px solid #ded9d9;
            padding-bottom:10px ;
            padding-top:10px ;
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
        #filter{
            float: right;
            width: 50px;
            height: 30px;
            margin-right: 30px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div id="filterDiv">
    <h3>filters</h3>
    <div class="innerFilterDiv">
        <label>sort by</label>
        <select id="sortBy">
            <option>author</option>
            <option>title</option>
            <option> genre </option>
            <option> topic </option>
            <option> publisher </option>
        </select>
    </div>
    <div class="innerFilterDiv">

        <label>filter</label>
        <input type="checkbox" class="cb">title
        <input type="text" name="title" class="textSearch" disabled/>
        <input type="checkbox" class="cb">author name
        <input type="text" name="author_name" class="textSearch" disabled/>
        <input type="checkbox" class="cb">topics
        <input type="text" name="topics" class="textSearch" disabled/>
        <input type="checkbox" class="cb">genre
        <input type="text" name="genre" class="textSearch" disabled/>
        <input type="checkbox" id="myActiveHoldings" />my active holdings
        <input type="checkbox" id="currentlyOverdue">currently overdue
        <input type="checkbox" id="currentlyBorrowed">currently borrowed

        <%--            <a href="/main_page_user/view_books?ISBN=54684665">filter</a>--%>


    </div>
    <div class="innerFilterDiv">
        <label>year Date of publication</label>
        <input type="text" id="publicationValue"/>
        <select id="publisher">
            <option selected>select publisher</option>
            <%
                String[][] data2 = (String[][]) session.getAttribute("select_publisher");
                if (data2 != null)
                {
                    for (String[] item2 : data2)
                    {%>
            <option>  <%= item2[0] %> </option>
            <%}
            }
            %>
            <%--                <option>jarir</option>--%>
            <%--                <option>jarir2</option>--%>
            <%--                <option>jarir3</option>--%>
            <%--                <option>jarir4</option>--%>
        </select>
    </div>
    <button onclick="getFilters()" id="filter">filter</button>

</div>
<div id="viewTable">
    <table>
        <tr>
            <th>ISBN</th>
            <th>title</th>
            <th>author</th>
            <th>topics</th>
            <th>genre</th>
            <th>description</th>
            <th>publishers</th>
            <th>borrow units</th>
            <th>sell units</th>
            <th>publication date</th>
        </tr>


        <%
            String[][] data = (String[][]) session.getAttribute("books_info");
            if (data != null)
            {
                for (String[] item : data)
                {
        %>
        <tr>
            <% for (int i = 0; i < 10; i++) {
            %>
            <td> <%= item[i] %></td>
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
