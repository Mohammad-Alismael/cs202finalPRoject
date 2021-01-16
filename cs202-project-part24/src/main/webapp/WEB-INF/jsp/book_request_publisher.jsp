<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 1/8/21
  Time: 12:33 AM
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
        #viewTable{
            background: #2ba7ef;
            width: 90%;
            height: auto;
            max-height: 350px;
            display: block;
            position: absolute;
            top: 58%;
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
            top: 75%;
            left: 5%;
            border-radius: 7px;
            overflow: auto;
        }
        #addBooks{
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
        #removeBooks{
            background: #2ba7ef;
            width: 90%;
            max-height: 350px;
            display: block;
            position: absolute;
            top: 35%;
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
        #addBooks h3,#removeBooks h3,#viewTable h3{
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
        #selectBookInner{
            display: flex;
            justify-content: left;
            margin-left: 35px;
            width: 95%;
            /*border-bottom: 0.5px solid #ded9d9;*/
            padding-bottom:10px ;
            padding-top:10px ;
        }
        #selectBookInner input,#whichTable,#selectBookInner button{
            margin-right: 10px;
        }
        .innerFilterDiv:nth-last-child(1){
            border-bottom: none;
        }
        .innerFilterDiv label{
            padding-right: 20px;
            text-transform: capitalize;
        }
        #publisher{
            margin-left: 10px;
            margin-right: 10px;
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
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            padding-top: 100px; /* Location of the box */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }

        /* Modal Content */
        .modal-content {
            background-color: #fefefe;
            margin: auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
        }

        /* The Close Button */
        .close {
            color: #aaaaaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: #000;
            text-decoration: none;
            cursor: pointer;
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
<div id="removeBooks">
    <h3>remove books</h3>
    <div calss="innerFilterDiv">
        <input type="text" placeholder="add ISBN number" id="isbnRemove" />
        <button onclick="submitRemove()">submit</button>
    </div>
</div>


<script type="text/javascript">
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
            var str_url = '/main_page_publisher/book_request_publisher?ISBN='+
                AddExisting[0].value+'&quantity='+AddExisting[1].value+'&borrow='+which[0].checked+'&sell='+which[1].checked;

            window.location.href = str_url
            console.log(str_url);
        }
        if (cb[1].checked){
            var isbn = NoneExisting[0].value;
            var title = NoneExisting[1].value;
            var author_name = NoneExisting[2].value;
         var str_url = '/main_page_publisher/book_request_publisher?ISBN='+
                isbn+'&title='+title+'&author_name='+author_name
                +'&topics='+NoneExisting[3].value+'&genre='+NoneExisting[4].value+'&description='+NoneExisting[5].value+
                '&units='+NoneExisting[6].value+'&publication_date='+NoneExisting[7].value
                +'&borrow='+which[2].checked+'&sell='+which[3].checked;


            window.location.href = str_url
        }

    }

    function submitRemove(){
        var str_url = '/main_page_publisher/book_request_publisher?ISBN='+
             document.getElementById("isbnRemove").value;
        window.location.href = str_url
    }

</script>
</body>
</html>
