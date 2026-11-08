<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 26/9/26
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thêm danh mục</title>
</head>
<body>
<a href="danhmucs">Quay lai </a>
<form action="danhmucs" method="post">
  <input type="hidden" name="action" value="SAVE">
  <c:if  test="${not empty danhmuc}">
  <input type="hidden" name="maDm" value="${danhmuc.id}">
  </c:if>

  <div>
    <label>Ten danh muc: </label>
    <input name="tenDM" value="${danhmuc.tenDM}">
  </div>
  <div>
    <label>Nguoi quan ly: </label>
    <input name="nguoiQuanLy" value="${danhmuc.nguoiQuanLy}">
  </div>
  <div>
    <label>Lien ket: </label>
    <input name="ghiChu" value="${danhmuc.ghiChu}">
  </div>

  <button type="submit">Luu</button>
</form>
</body>
</html>
