<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 25/9/26
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Them tin tuc</title>
</head>
<body>
<a href="tintucs">Quay lai </a>
<form action="tintucs" method="post">
  <input type="hidden" name="action" value="SAVE">
  <c:if  test="${not empty tintuc}">
    <input type="hidden" name="maTT" value="${tintuc.id}">
  </c:if>

  <div>
    <label>Tieu de: </label>
    <input name="tieuDe" value="${tintuc.tieuDe}">
  </div>
  <div>
    <label>Noi dung tin tuc: </label>
    <input name="ndTT" value="${tintuc.ndTT}">
  </div>
  <div>
    <label>Lien ket: </label>
    <input name="lienKet" value="${tintuc.lienKet}">
  </div>
  <div>
  <label>Danh muc: </label>
    <select name="maDm">
      <c:forEach items="${danhmucs}" var="dm">
          <option value="${dm.id}">${dm.tenDM}</option>
      </c:forEach>
    </select>
  </div>

<button type="submit">Save</button>


</form>
</body>
</html>
