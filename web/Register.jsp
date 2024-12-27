<%-- 
    Document   : Register
    Created on : Oct 30, 2024, 5:14:15 PM
    Author     : Windows 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Vector" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Ký</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-image: url('https://cdn.thuvienphapluat.vn/phap-luat/2022/2/19/QK/thi-bang-lai-xe%2001.png');
            background-size: cover; /* Đảm bảo hình nền phủ kín toàn bộ */
            background-repeat: no-repeat; /* Không lặp lại hình nền */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            color: black; /* Đặt màu chữ thành trắng để nổi bật trên nền */
        }
        .register-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 350px;
            text-align: center;
        }
        h2 {
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
            text-align: left;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="text"],
        input[type="password"],
        input[type="email"],
        input[type="date"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .message {
            margin: 10px 0;
            font-weight: bold;
        }
        .success {
            color: green;
        }
        .error {
            color: red;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #0056b3;
        }
        p {
            margin-top: 15px;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <h2>Đăng Ký</h2>
        
        <form action="register" method="post">
            <div class="form-group">
                <label for="username">Tên người dùng:</label>
                <input type="text" id="username" name="username" required/>
            </div>
            <div class="form-group">
                <label for="password">Mật khẩu:</label>
                <input type="password" id="password" name="password" required/>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required/>
            </div>
            <div class="form-group">
                <label for="phone">Số điện thoại:</label>
                <input type="text" id="phone" name="phone" required/>
            </div>
            <div class="form-group">
                <label for="address">Địa chỉ:</label>
                <input type="text" id="address" name="address" required/>
            </div>
            <div class="form-group">
                <label for="dob">Ngày sinh:</label>
                <input type="date" id="dob" name="dob" required/>
            </div>
            <button type="submit">Đăng Ký</button>
        </form>
        
        <div>
            <%
                String registerSuccess = (String) request.getAttribute("registerSuccess");
                String registerError = (String) request.getAttribute("registerError");
                if (registerSuccess != null) {
                    out.println("<p class='message success'>" + registerSuccess + "</p>");
                }
                if (registerError != null) {
                    out.println("<p class='message error'>" + registerError + "</p>");
                }
            %>
        </div>
        
        <p>Đã có tài khoản? <a href="Login.jsp">Đăng nhập tại đây</a></p> <!-- Liên kết đến trang đăng nhập -->
    </div>
</body>
</html>
