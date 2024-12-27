<%-- 
    Document   : MyProfile
    Created on : Oct 31, 2024, 8:14:56 AM
    Author     : Windows 10
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hồ Sơ Cá Nhân</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-image: url('https://static.vecteezy.com/system/resources/previews/000/833/489/original/black-abstract-background-with-diamond-shape-layers-vector.jpg');
            background-size: cover;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .profile-container {
            background-color: rgba(255, 255, 255, 0.9); /* Thêm độ trong suốt */
            padding: 30px;
            width: 400px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3); /* Tăng độ đổ bóng */
        }
        h1 {
            text-align: center;
            color: #4CAF50;
            font-size: 26px; /* Tăng kích thước chữ */
            margin-bottom: 20px; /* Thêm khoảng cách dưới tiêu đề */
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
            color: #333; /* Đổi màu chữ */
        }
        input[type="text"], input[type="password"], input[type="date"], input[type="email"], input[type="tel"] {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border-radius: 5px;
            border: 1px solid #ddd;
            transition: border-color 0.3s; /* Hiệu ứng chuyển tiếp */
        }
        .form-group input:focus {
            outline: none;
            border-color: #4CAF50;
            box-shadow: 0 0 5px rgba(76, 175, 80, 0.5); /* Thêm bóng cho ô nhập */
        }
        .button-container {
            display: flex;
            justify-content: space-between;
        }
        .button-container button {
            padding: 10px 20px;
            border: none;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s; /* Hiệu ứng chuyển tiếp cho nút */
        }
        .save-button {
            background-color: #4CAF50;
            color: white;
        }
        .save-button:hover {
            background-color: #45a049; /* Tối màu khi hover */
        }
        .cancel-button {
            background-color: #f44336;
            color: white;
        }
        .cancel-button:hover {
            background-color: #e53935; /* Tối màu khi hover */
        }
        p {
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="profile-container">
        <h1>Hồ Sơ Cá Nhân</h1>
        <form action="profile" method="POST">
            <div class="form-group">
                <label for="username">User Name:</label>
                <input type="text" id="username" name="username" required value="${user.username}">
            </div>
            <div class="form-group">
                <label for="username">Password:</label>
                <input type="text" id="password" name="password" required value="${user.password}">
            </div>
            
            <div class="form-group">
                <label for="dob">Date of Birth:</label>
                <input type="date" id="dob" name="dob" required value="${dob}">
            </div>
            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" id="address" name="address" required value="${user.address}">
            </div>
            <div class="form-group">
                <label for="phonenumber">Phone Number:</label>
                <input type="tel" id="phonenumber" name="phonenumber" required value="${user.phoneNumber}">
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required value="${user.email}">
            </div>
            <div class="button-container">
                <button type="submit" class="save-button">Save</button>
                <button type="button" class="cancel-button" onclick="window.history.back()">Cancel</button>
            </div>
        </form>
        <c:if test="${not empty updateSuccess}">
            <p style="color: green;">${updateSuccess}</p>
        </c:if>
        <c:if test="${not empty updateError}">
            <p style="color: red;">${updateError}</p>
        </c:if>
    </div>
</body> 
</html>
