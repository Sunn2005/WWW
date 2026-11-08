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
    <title>Danh sach tin tuc</title>
</head>
<body>
<h1>Danh sách Tin tức</h1>
<a href="tintucs?action=CREATE" style="display: block">Thêm Tin tức</a>
<a href="danhmucs" style="display: block">Danh sách danh mục</a>

<table border="1" width="80%">
    <tr>
        <th>Ma danh muc</th>
        <th>Tieu de</th>
        <th>Noi dung</th>
        <th>Lien ket</th>
        <th>Ma danh muc</th>
        <th>Hanh dong</th>
    </tr>
    <c:forEach items="${tintucs}" var="tt">
        <tr>
            <td>${tt.id}</td>
            <td>${tt.tieuDe}</td>
            <td>${tt.ndTT}</td>
            <td>${tt.lienKet}</td>
            <td>${tt.danhMuc.id}</td>

            <td>
                <a href="tintucs?action=EIDT&maTT=${tt.id}" >Chinh sua</a>
                <form action="tintucs" method="post">
                    <input type="hidden" name="action" value="DELETE">
                    <input type="hidden" name="maTT" value="${tt.id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
