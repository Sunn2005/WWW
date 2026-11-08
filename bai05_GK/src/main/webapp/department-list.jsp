<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 16/9/26
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Department List Page</title>
</head>
<body>
    <h1>Department List</h1>
    <a href="department-form.jsp" style="display: block">Add department page</a>
    <a href="employees" style="display: block">Employee list page</a>

    <form method="get" action="departments">
        <label>Tìm phòng ban</label>
        <input name="name">
        <button type="submit">Search</button>
    </form>

    <table border="1" width="80%">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Action</th>
        </tr>

        <c:forEach items="${departments}" var="d">
            <tr>
                <td>${d.id}</td>
                <td>${d.name}</td>
                <td style="display: flex; align-items: center;">
                    <a href="departments?action=EDIT&id=${d.id}">Edit</a>
                    <form action="departments" method="post">
                        <input type="hidden" name="action" value="DELETE">
                        <input type="hidden" name="id" value="${d.id}">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>

    </table>
</body>
</html>
