<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 1/9/21
  Time: 6:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>manage books</title>
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
            top: 10%;
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
        #viewTable h3,#selectBook h3,#addBooks h3{
            text-transform: uppercase;
            padding-left: 10px;
        }
        form{
            margin-top: 10px;
            margin-left: 10px;
        }
        #addBooks{
            background: #2ba7ef;
            width: 90%;
            max-height: 350px;
            display: block;
            position: absolute;
            top: 65%;
            left: 5%;
            border-radius: 7px;
            padding-bottom: 5px;
        }
        #submit{
            float: right;
            width: 60px;
            height: 30px;
            margin-right: 30px;
            margin-top: 10px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div id="viewTable">
        <h3>pending publisher request</h3>
        <table>
            <tr>
                <th>id</th>
                <th>ISBN</th>
                <th>title</th>
                <th>quantity</th>
                <th>book type</th>
                <th>created by</th>
            </tr>
            <%
                String[][] data = (String[][]) session.getAttribute("pendingPublisherRequests");
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
        <div>
            <form>
                <input type="text" name="id" placeholder="select an id"/>
                <select id="whichStatus" onchange="c()">
                    <option selected>choose one</option>
                    <option>accept</option>
                    <option>reject</option>
                </select>
                <input type="button" value="submit" onclick="check()">
            </form>
            <div id="hide" style="display: none">
                <input type="text" class="info" placeholder="units"/>
                <input type="text" class="info" placeholder="penalty per day or price"/>
            </div>
        </div>
    </div>
    <div id="addBooks">
        <h3>add books</h3>
        <div class="innerFilterDiv">
            <input type="radio"  class="cb" name="q" onchange="check()">existing
            <input type="radio"  class="cb" name="q" onchange="check()">none existing
        </div>
        <div class="innerFilterDiv" id="existing" style="display: none">
            <input type="text" placeholder="add ISBN number" class="AddExisting" />
            <input type="text" placeholder="quantity" class="AddExisting"/>

            <input type="radio" class="which" name="w"> borrow
            <input type="radio" class="which" name="w"> sell
        </div>
        <div class="innerFilterDiv" id="none_existing" style="display: none">
            <input type="text" placeholder="add ISBN number" class="NoneExisting" />
            <input type="text" placeholder="title" class="NoneExisting"/>
            <input type="text" placeholder="author name" class="NoneExisting"/>
            <input type="text" placeholder="topics" class="NoneExisting"/>
            <input type="text" placeholder="genre" class="NoneExisting"/>
            <input type="text" placeholder="description" class="NoneExisting"/>
            <input type="text" placeholder="units" class="NoneExisting"/>
            <%--        <input type="text" placeholder="borrow units" class="NoneExisting"/>--%>
            <%--        <input type="text" placeholder="sell units" class="NoneExisting"/>--%>
            <input type="text" placeholder="publication date" class="NoneExisting"/>

            <input type="radio" class="which" name="w"> borrow
            <input type="radio" class="which" name="w"> sell
        </div>
        <button id="submit" onclick="submit()">submit</button>
    </div>
    <script type="text/javascript">

        const status = document.getElementById("whichStatus");
        const info = document.getElementsByClassName("info");
        var hide = document.getElementById("hide");
        function c() {
            if (status.selectedIndex == 0) {
                alert("select status")
            } else if (status.selectedIndex == 1) {
                hide.style.display = "block"

            } else if (status.selectedIndex == 2) {
                hide.style.display = "none"
            }
        }
        function check(){
            // if (info[0].value == ""){
            //     alert("enter units")
            // }else if (info[1].value == ""){
            //     alert("enter penalty per day")
            // }
            // else if (info[0].value == "" && info[1].value == ""){
            //     alert("enter units & penalty per day")
            // } else {
            if (status.selectedIndex == 1){
                var str_url = "/main_page_manager/manage_books?id="+document.getElementsByName("id")[0].value+
                    "&units="+info[0].value+'&penalty='+info[1].value+"&status="+status.value;

                window.location.href = str_url
            }else if (status.selectedIndex == 2){
                var str_url = "/main_page_manager/manage_books?id="+document.getElementsByName("id")[0].value+"&status="+status.value;

                window.location.href = str_url
            }

        }

        var cb = document.getElementsByClassName("cb");

        function check(){
            if (cb[0].checked){
                cb[1].checked = false;
                document.getElementById("existing").style.display = "block";
                document.getElementById("none_existing").style.display = "none";
            }else if (cb[1].checked){
                cb[0].checked = false;
                document.getElementById("none_existing").style.display = "block";
                document.getElementById("existing").style.display = "none";
            }
        }

        var AddExisting = document.getElementsByClassName("AddExisting");

        var NoneExisting = document.getElementsByClassName("NoneExisting");
        var which = document.getElementsByClassName("which");
        function submit(){
            if (cb[0].checked){
                var str_url = '/main_page_manager/manage_books?ISBN='+
                    AddExisting[0].value+'&quantity='+AddExisting[1].value+'&borrow='+which[0].checked+'&sell='+which[1].checked;

                window.location.href = str_url
                console.log(str_url);
            }
            if (cb[1].checked){
                var isbn = NoneExisting[0].value;
                var title = NoneExisting[1].value;
                var author_name = NoneExisting[2].value;
                var str_url = '/main_page_manager/manage_books?ISBN='+
                    isbn+'&title='+title+'&author_name='+author_name
                    +'&topics='+NoneExisting[3].value+'&genre='+NoneExisting[4].value+'&description='+NoneExisting[5].value+
                    '&units='+NoneExisting[6].value+'&publication_date='+NoneExisting[7].value
                    +'&borrow='+which[2].checked+'&sell='+which[3].checked;


                window.location.href = str_url
            }

        }
    </script>
</body>
</html>
