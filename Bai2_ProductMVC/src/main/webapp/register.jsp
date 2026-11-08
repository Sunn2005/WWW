<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Registration Form</title>
  <style>
    * {
      box-sizing: border-box;
    }

    body {
      margin: 0;
      font-family: Arial, sans-serif;
      background-color: #f5f5f5;
    }

    .container {
      width: 650px;
      max-width: 90%;
      margin: 40px auto;
      padding: 30px;
      background-color: white;
      border: 1px solid #ccc;
    }

    h1 {
      margin-top: 0;
      text-align: center;
    }

    .name-row,
    .birthday-row {
      display: flex;
      gap: 15px;
    }

    .form-control {
      width: 100%;
      padding: 12px;
      margin-bottom: 18px;
      border: 1px solid #ccc;
      border-radius: 5px;
      font-size: 16px;
    }

    label.title {
      display: block;
      margin-bottom: 10px;
      font-size: 18px;
    }

    .gender-group {
      margin: 5px 0 25px;
    }

    .gender-group label {
      margin-right: 25px;
    }

    button {
      width: 100%;
      padding: 13px;
      border: none;
      border-radius: 5px;
      color: white;
      background-color: #087df5;
      font-size: 18px;
      cursor: pointer;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>User Registration Form</h1>

  <form action="${pageContext.request.contextPath}/registerform" method="post">
    <div class="name-row">
      <input class="form-control" type="text" name="firstname"
             placeholder="First Name" required>

      <input class="form-control" type="text" name="lastname"
             placeholder="Last Name" required>
    </div>

    <input class="form-control" type="email" name="email"
           placeholder="Your Email" required>

    <input class="form-control" type="password" name="password"
           placeholder="Password" required>

    <label class="title">Birthday</label>
    <div class="birthday-row">
      <select class="form-control" name="month" required>
        <option value="" disabled selected>Month</option>
        <% for (int month = 1; month <= 12; month++) { %>
        <option value="<%= month %>"><%= month %></option>
        <% } %>
      </select>

      <select class="form-control" name="day" required>
        <option value="" disabled selected>Day</option>
        <% for (int day = 1; day <= 31; day++) { %>
        <option value="<%= day %>"><%= day %></option>
        <% } %>
      </select>

      <select class="form-control" name="year" required>
        <option value="" disabled selected>Year</option>
        <% for (int year = 2026; year >= 1900; year--) { %>
        <option value="<%= year %>"><%= year %></option>
        <% } %>
      </select>
    </div>

    <label class="title">Gender</label>
    <div class="gender-group">
      <label>
        <input type="radio" name="gender" value="Female" required>
        Female
      </label>

      <label>
        <input type="radio" name="gender" value="Male">
        Male
      </label>
    </div>

    <button type="submit">Sign Up</button>
  </form>
</div>
</body>
</html>
