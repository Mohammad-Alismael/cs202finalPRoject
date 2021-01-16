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
            top: 33%;
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
            <option>genre</option>
            <option>topic</option>
            <option>publisher</option>
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
            <option><%= item2[0] %> </option>
            <%}
            }
            %>

        </select>
        <input type="text" name="filteredIsbn" placeholder="filter by ISBN">
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
            <th>status</th>
            <th>borrow units</th>
            <th>sell units</th>
            <th>publication date</th>
            <th>penalty per day</th>
            <th>price</th>
            <th>number of borrowing</th>
            <th>number of purchases</th>
        </tr>


        <%
            String[][] data = (String[][]) session.getAttribute("books_info");
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
<div id="selectBook">
    <h3>add book to the user</h3>
    <div id="selectBookInner">
        <input type="text" placeholder="add username" id="addUser" onblur="checkBook()"/>
        <select id="whichTable" onchange="checkBook()">
            <option selected>
                where to add a book
            </option>
            <option>borrow</option>
            <option>purchase</option>
            <option>hold request</option>
            <option>preorder book</option>
            <option>return books</option>
        </select>
        <input type="text" placeholder="add ISBN number" id="isbn"/>
        <input type="text" name="quantity" placeholder="quantity" style="display:none"/>
        <button onclick="update()">submit</button>


    </div>
    <div style="margin-left: 1000px;margin-top: -25px">
        <input type="text" placeholder="add ISBN number to penalty" id="isbnPenalty"/>
        <input type="button" value="see penalty info" onclick="seePenalty()">
        <p>penalty : ${penalty}</p>
    </div>
</div>

<div id="selectBook" style="top: 105%">
    <h3>add units</h3>
    <div id="selectBookInner">
        <form method="post">
            <input type="text" name="isbn_number_update" placeholder="ISBN number" />
            <select name="whichTableUpdate">
                <option>borrow</option>
                <option>sell</option>
            </select>
            <input type="text" name="updated_quantity" placeholder="quantity"/>
            <input type="submit" value="submit">
        </form>
    </div>
</div>
<div id="selectBook" style="top: 125%">
    <h3>remove book</h3>
    <form id="selectBookInner" method="post">
        <input type="text" name="isbn_number_remove" placeholder="ISBN number" />
        <input type="submit" value="submit">
    </form>
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

    var parentTable = document.getElementById("checkSelectedBook");
    var totalPenalty = document.getElementById("totalPenalty");

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

        var publicationValue = document.getElementById("publicationValue");
        var publicationValue2 = publicationValue == "" ? "none" : publicationValue.value;
        var filteredIsbn = document.getElementsByName("filteredIsbn")[0];
        var str_url = '/main_page_manager/view_books_manager?title='+title2+'&author_name='+
            author_name2+'&topics='+topics2+'&genre='+genre2+'&publisher='+selectedPublisher+
            '&publicationYear='+publicationValue2+'&sortBy='+selectedSortBy+'&filteredIsbn='+filteredIsbn.value;

        window.location.href = str_url
        console.log(str_url);
        // console.log(window.location.href)

        <%--author_name=${author_name.value}?topics=${topics.value}?genre=${genre.value}`--%>
    }


    var isbn = document.getElementById("isbn");

    var quantitySelect = document.getElementsByName("quantity")[0];
    var addUser = document.getElementById("addUser");
    var whichTable = document.getElementById("whichTable");
    var quantitySelect = document.getElementsByName("quantity")[0];
    function update(){
        var str_url = '/main_page_manager/view_books_manager?isbn=' + isbn.value + '&which_table=' +
            '&quantity='+quantitySelect.value+'&username='+addUser.value+'&which_table='+
        whichTable.item(whichTable.selectedIndex).innerText;

        window.location.href = str_url
        console.log(str_url)

    }
    var isbnPenalty = document.getElementById("isbnPenalty");
    function seePenalty(){
        var str_url = '/main_page_manager/view_books_manager?isbn=' + isbnPenalty.value;
        window.location.href = str_url
    }

    <%--function checkBook(){--%>

    <%--    if (whichTable.item(whichTable.selectedIndex).innerText == "borrow"){--%>

    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[0].innerText = "${username}";// title--%>
    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[1].innerText = "${username}";// penalty--%>
    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[2].innerText = "any";--%>
    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[3].innerText = "any";--%>

    <%--        totalPenalty.style.display = "none";--%>
    <%--        quantitySelect.style.display = "none";--%>

    <%--    }else if (whichTable.item(whichTable.selectedIndex).innerText == "purchase"){--%>

    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[0].innerText = "${username}";// title--%>
    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[1].innerText = "any";// penalty--%>
    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[2].innerText = "${username}";// price--%>
    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[3].innerText = "${username}";//units--%>



    <%--        totalPenalty.style.display = "none";--%>
    <%--        quantitySelect.style.display = "block";--%>

    <%--    }else if (whichTable.item(whichTable.selectedIndex).innerText == "hold request"){--%>

    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[0].innerText = "${username}";// title--%>
    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[1].innerText = "none";// penalty--%>
    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[2].innerText = "none";// price--%>
    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[3].innerText = "none";//units--%>
    <%--        totalPenalty.style.display = "none";--%>
    <%--        quantitySelect.style.display = "none";--%>
    <%--    }else if (whichTable.item(whichTable.selectedIndex).innerText == "preorder book"){--%>
    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[0].innerText = "${username}";// title--%>
    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[1].innerText = "none";// penalty--%>
    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[2].innerText = "none";// price--%>
    <%--        parentTable.getElementsByTagName("tr")[1].getElementsByTagName("td")[3].innerText = "none";//units--%>
    <%--        totalPenalty.style.display = "none";--%>
    <%--        quantitySelect.style.display = "none";--%>
    <%--    }else if (whichTable.item(whichTable.selectedIndex).innerText == "return books"){--%>

    <%--        totalPenalty.style.display = "block";--%>
    <%--        totalPenalty.innerText =  "total penalty : ${username}"--%>
    <%--        quantitySelect.style.display = "none";--%>
    <%--    }--%>
    <%--}--%>
    function check(){
        for (var i = 0; i < textSearch.length; i++) {
            if (cb[i].checked){
                textSearch[i].disabled = false
            }
            if (!cb[i].checked){
                textSearch[i].disabled = true
                textSearch[i].value = "";
            }
        }

    }

    for (var i = 0; i < cb.length; i++) {
        cb[i].addEventListener("change", check);
    }

    var modal = document.getElementById("myModal");





    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        modal.style.display = "none";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
</script>
</body>
</html>
