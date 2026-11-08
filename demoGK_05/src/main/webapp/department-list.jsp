<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 21/9/26
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Department List </title>
</head>
<body>
<h1>Department List Page</h1>
<a href="department-form.jsp" style="display: block">Add department page</a>
<a href="employees" style="display: block">Employee list page</a>

<form action="departments" method="get">
  <label>Tìm phòng ban</label>
  <input name="name">
  <button type="submit">Search</button>
</form>

<table border="1" width="80%">
  <tr>
    <th>Id</th>
    <th>Name</th>
    <th>Action</th>
  </tr>

  <c:forEach items="${departments}" var="d">
    <tr>
      <td>${d.id}</td>
      <td>${d.name}</td>
      <td>
        <a href="">Edit</a>
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
