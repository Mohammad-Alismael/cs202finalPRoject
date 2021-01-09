<%--
  Created by IntelliJ IDEA.
  User: apple
  Date: 1/8/21
  Time: 12:13 AM
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
        top: 35%;
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
            <option>jarir</option>
            <option>jarir2</option>
            <option>jarir3</option>
            <option>jarir4</option>
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
            <% for (int i = 0; i < 9; i++) {
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
<div id="viewTable2">
    <h3>your books</h3>
    <table>
        <tr>
            <th>ISBN</th>
            <th>title</th>
            <th>author</th>
            <th>topics</th>
            <th>genre</th>
            <th>description</th>
            <th>number of borrowing</th>
            <th>number of sells</th>
            <th>publication date</th>
        </tr>


    </table>
</div>

<div id="myModal" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>are you sure that what you were looking for ?</p>
        <button type="button" onclick="update()">submit</button>
    </div>

</div>
<script type="text/javascript">
    const title = document.getElementsByName("title")[0];
    const author_name = document.getElementsByName("author_name")[0];
    const topics = document.getElementsByName("topics")[0];
    const genre = document.getElementsByName("genre")[0];
    var cb = document.getElementsByClassName("cb");
    var textSearch = document.getElementsByClassName("textSearch");
    var publisher = document.getElementById("publisher");
    var sortBy = document.getElementById("sortBy");
    // Get the button that opens the modal
    var btn = document.getElementById("myBtn");

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];
    function getFilters(){
        console.log(title.value,author_name.value,topics.value,genre.value)

        var title2 = title.value == "" ? "none" : title.value;
        var author_name2 = author_name.value == "" ? "none" : author_name.value;
        var topics2 = topics.value == "" ? "none" : topics.value;
        var genre2 = genre.value == "" ? "none" : genre.value;
        var selectedPublisher = publisher.item(publisher.selectedIndex).innerText == "select publisher" ? "none" :
            publisher.item(publisher.selectedIndex).innerText;
        var selectedSortBy = sortBy.item(sortBy.selectedIndex).innerText == "select publisher" ? "none" :
            sortBy.item(publisher.selectedIndex).innerText;
        var myActiveHoldings = document.getElementById("myActiveHoldings").checked;
        var currentlyOverdue = document.getElementById("currentlyOverdue").checked;
        var currentlyBorrowed = document.getElementById("currentlyBorrowed").checked;
        var publicationValue = document.getElementById("publicationValue");
        var publicationValue2 = publicationValue == "" ? "none" : publicationValue.value;
        var str_url = '/main_page_publisher/view_books_publisher?title='+title2+'&author_name='+
            author_name2+'&topics='+topics2+'&genre='+genre2+'&publisher='+selectedPublisher+
            '&myActiveHoldings='+myActiveHoldings+'&publicationYear='+publicationValue2+'&sortBy='+selectedSortBy+
            '&currentlyOverdue='+currentlyOverdue+'&currentlyBorrowed='+currentlyBorrowed;

        window.location.href = str_url
        console.log(str_url);
        console.log(window.location.href)

        <%--author_name=${author_name.value}?topics=${topics.value}?genre=${genre.value}`--%>
    }
</script>
</body>
</html>
