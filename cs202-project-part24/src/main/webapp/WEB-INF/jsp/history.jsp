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
            top: 35%;
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
        #seeResult{
            margin-left: 130px;
            margin-bottom: 20px;
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
        <label>type</label>
        <select id="whichType">
            <option>author</option>
            <option>title</option>
            <option> genre </option>
            <option> topic </option>
            <option> publisher </option>
        </select>
        <p><%= session.getAttribute("favourite")%></p>
        <button type="button" id="seeResult">see result</button>
    </div>
</div>
<div id="viewTable">
    <h3>history</h3>
        <div id="innerFilterDiv">
            <select id="whichTable" onchange="displayTable()">
                <option selected >choose one</option>
                <option>borrowings</option>
                <option>purchases</option>
                <option> hold request</option>
                <option>notification request</option>
                <option>overdue</option>
            </select>

        </div>







        <table id="borrowingsTable" style="display: none">
            <tr>
                <th>ISBN</th>
                <th>title</th>
                <th>start date</th>
                <th>end date</th>
                <th>return date</th>
            </tr>
            <%
                String[][] data = (String[][]) session.getAttribute("UserBorrowHistory");
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
        <table id="purchasesTable" style="display: none">
            <tr>
                <th>ISBN</th>
                <th>title</th>
                <th>quantity</th>
                <th>total price</th>
                <th>date</th>
            </tr>
            <%
                String[][] data2 = (String[][]) session.getAttribute("UserPurchaseHistory");
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
        <table id="holdRequestTable" style="display: none">
            <tr>
                <th>ISBN</th>
                <th>title</th>
                <th>status</th>
                <th>created on</th>

            </tr>
            <%
                String[][] data3 = (String[][]) session.getAttribute("UserHoldRequestHistory");
                if (data3 != null)
                {
                    for (String[] item : data3)
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
        <table id="notificationTable" style="display: none">
            <tr>
                <th>ISBN</th>
                <th>title</th>
                <th>created on</th>

            </tr>
            <%
                String[][] data4 = (String[][]) session.getAttribute("UserNotificationRequestHistory");
                if (data4 != null)
                {
                    for (String[] item : data4)
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
                }else System.out.println("UserNotificationRequestHistory is null");
            %>
        </table>
        <table id="overdueTable" style="display: none">
            <tr>
                <th>ISBN</th>
                <th>title</th>
                <th>start date</th>
                <th>end date</th>
                <th>return date</th>
                <th>penalty</th>
            </tr>
            <%
                String[][] data5 = (String[][]) session.getAttribute("UserOverdueHistory");
                if (data5 != null)
                {
                    for (String[] item : data5)
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
    <p id="times" style="display: none;float: left;margin-left: 20px">number of times you overdue a book:</p>
    </div>

<script type="text/javascript">
    var borrowingsTable = document.getElementById("borrowingsTable");
    var purchasesTable = document.getElementById("purchasesTable");
    var holdRequestTable = document.getElementById("holdRequestTable");
    var notificationTable = document.getElementById("notificationTable");
    var overdueTable = document.getElementById("overdueTable");
    var whichTable = document.getElementById("whichTable");
    var times = document.getElementById("times");
    function displayTable() {

        if (whichTable.selectedIndex == 0) {
            alert("choose type")

        } else if (whichTable.selectedIndex == 1) {

            borrowingsTable.style.display = "block"

            purchasesTable.style.display = "none"
            holdRequestTable.style.display = "none"
            notificationTable.style.display = "none"
            overdueTable.style.display = "none"
            times.style.display = "none"
        } else if (whichTable.selectedIndex == 2) {
            borrowingsTable.style.display = "none"
            purchasesTable.style.display = "block"
            holdRequestTable.style.display = "none"
            notificationTable.style.display = "none"
            overdueTable.style.display = "none"
            times.style.display = "none"
        } else if (whichTable.selectedIndex == 3) {
            borrowingsTable.style.display = "none"
            purchasesTable.style.display = "none"
            holdRequestTable.style.display = "block"
            notificationTable.style.display = "none"
            overdueTable.style.display = "none"
            times.style.display = "none"
        } else if (whichTable.selectedIndex == 4) {
            borrowingsTable.style.display = "none"
            purchasesTable.style.display = "none"
            holdRequestTable.style.display = "none"
            notificationTable.style.display = "block"
            overdueTable.style.display = "none"
            times.style.display = "none"
        }else if(whichTable.selectedIndex == 5){
            borrowingsTable.style.display = "none"
            purchasesTable.style.display = "none"
            holdRequestTable.style.display = "none"
            notificationTable.style.display = "none"
            overdueTable.style.display = "block"
            times.style.display = "block"
        }
    }

    document.getElementById("seeResult").onclick = function (){
        var str_url = '/main_page_user/history?include='+document.getElementById("whichGroup").value+'&whichType='+
            document.getElementById("whichType").value;

        window.location.href = str_url;
        // console.log(str_url)
    }
</script>
</body>
</html>
