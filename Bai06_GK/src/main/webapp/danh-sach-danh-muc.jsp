<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 24/9/26
  Time: 22:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danh sach danh muc</title>
</head>
<body>
<h1>Danh sách Danh mục</h1>
<a href="danh-muc-form.jsp" style="display: block">Thêm danh mục</a>
<a href="tintucs?maDm&id=${id}" style="display: block">Trang danh sách Tin tức</a>

<table border="1" width="80%">
    <tr>
        <th>Ma danh muc</th>
        <th>Ten danh muc</th>
        <th>Nguoi quan ly</th>
        <th>Ghi chu</th>
        <th>Hanh dong</th>
    </tr>
    <c:forEach items="${danhmucs}" var="dm">
        <tr>

            <td>${dm.id}</td>
            <td>${dm.tenDM}</td>
            <td>${dm.nguoiQuanLy}</td>
            <td>${dm.ghiChu}</td>
            <td>
                <a href="danhmucs?action=EDIT&maDm=${dm.id}" >Chinh sua</a>
                <form action="danhmucs" method="post">
                    <input type="hidden" name="action" value="DELETE">
                    <input type="hidden" name="maDm" value="${dm.id}">
                    <button type="submit">Xoa</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
