<%--
  Created by IntelliJ IDEA.
  User: freedrone
  Date: 10.12.2020
  Time: 08:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List Items Page</title>
</head>
<body>
<%

        String[][] data = (String[][]) session.getAttribute("Publisher_books");
        if (data != null)
        {
            for (String[] item : data)
            {
%>
<tr>
    <br>
        <% for (String temp : item) {
                %>
    <td> <%= temp %></td>
        <%
                    }
            }

        }
                %>
</body>
</html>
