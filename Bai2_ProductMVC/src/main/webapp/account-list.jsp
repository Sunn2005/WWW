<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>

<html>
<head>
  <meta charset="UTF-8">

  <title>Account List</title>

  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f9;
      padding: 30px;
    }

    .container {
      max-width: 1000px;
      margin: auto;
      background-color: white;
      padding: 25px;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    h2 {
      text-align: center;
      color: #333;
      margin-bottom: 25px;
    }

    table {
      width: 100%;
      border-collapse: collapse;
    }

    table th,
    table td {
      border: 1px solid #ddd;
      padding: 10px;
      text-align: center;
    }

    table th {
      background-color: #087df5;
      color: white;
    }

    table tr:nth-child(even) {
      background-color: #f8f8f8;
    }

    table tr:hover {
      background-color: #e9f7ef;
    }

    .empty {
      text-align: center;
      color: #777;
      padding: 20px;
    }

    .btn-detail {
      display: inline-block;
      padding: 6px 12px;
      background-color: #087df5;
      color: white;
      text-decoration: none;
      border-radius: 4px;
    }

    .btn-detail:hover {
      background-color: #087df5;
    }

    .btn-register {
      display: inline-block;
      margin-top: 20px;
      padding: 10px 20px;
      background-color: #087df5;
      color: white;
      text-decoration: none;
      border-radius: 5px;
    }

    .btn-register:hover {
      background-color: #28a745;
    }
  </style>

</head>

<body>

<div class="container">

  <h2>Account List</h2>

  <c:choose>

    <c:when test="${not empty accounts}">

      <table>

        <thead>
        <tr>
          <th>ID</th>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Email</th>
          <th>Password</th>
          <th>Date of Birth</th>
          <th>Action</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="account" items="${accounts}">

          <tr>

            <td>${account.id}</td>

            <td>${account.firstName}</td>

            <td>${account.lastName}</td>

            <td>${account.email}</td>

            <td>${account.password}</td>

            <td>${account.dateOfBirth}</td>

            <td>
              <a class="btn-detail"
                 href="${pageContext.request.contextPath}/detail-account.jsp">
                Detail
              </a>
            </td>

          </tr>

        </c:forEach>

        </tbody>

      </table>

    </c:when>

    <c:otherwise>

      <div class="empty">
        No accounts found.
      </div>

    </c:otherwise>

  </c:choose>

  <a class="btn-register"
     href="${pageContext.request.contextPath}/register.jsp">
    Register New Account
  </a>

</div>

</body>
</html>
