<%-- 
    Document   : AccountManager
    Created on : Dec 6, 2024, 11:46:55 AM
    Author     : Windows 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>
<% response.setHeader("Pragma", "no-cache"); %>
<% response.setDateHeader("Expires", 0); %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Account Manager</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
                margin: 0;
                padding: 0;
            }
            .header {
                background-color: #4CAF50;
                color: white;
                padding: 10px 20px;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            h1 {
                margin: 0;
            }
            .table-container {
                width: 80%;
                margin: 20px auto;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                background-color: white;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
            th, td {
                border: 1px solid #dddddd;
                text-align: left;
                padding: 8px;
            }
            th {
                background-color: #f2f2f2;
            }
            .switch {
                position: relative;
                display: inline-block;
                width: 34px;
                height: 20px;
            }
            .switch input {
                opacity: 0;
                width: 0;
                height: 0;
            }
            .slider {
                position: absolute;
                cursor: pointer;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: #ccc;
                transition: .4s;
            }
            .slider:before {
                position: absolute;
                content: "";
                height: 12px;
                width: 12px;
                left: 4px;
                bottom: 4px;
                background-color: white;
                transition: .4s;
            }
            input:checked + .slider {
                background-color: #2196F3;
            }
            input:checked + .slider:before {
                transform: translateX(14px);
            }
            .slider.round {
                border-radius: 34px;
            }
            .slider.round:before {
                border-radius: 50%;
            }
            .search-container {
                margin-bottom: 20px;
                text-align: center;
            }
            .search-container input {
                padding: 10px;
                width: 50%;
                font-size: 16px;
            }
            button {
                padding: 10px 20px;
                font-size: 16px;
                cursor: pointer;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 5px;
                transition: background-color 0.3s;
            }

            button:hover {
                background-color: #45a049;
            }
        </style>

    </head>
    <body>
        <div class="header">
            <h1>Account Manager</h1>
            <a class="add-button" href="${pageContext.request.contextPath}/adm">Back to Home</a>
        </div>

        <div class="search-container">
            <form action="${pageContext.request.contextPath}/manageAccount" method="get">
                <input type="text" name="search" placeholder="Search by Username or Email" value="${param.search}">
                <button type="submit">Search</button>
            </form>
        </div>

        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Username</th>
                        <th>Phone Number</th>
                        <th>Email</th>
                        <th>Status</th>
                        <th>Ban this user?</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty userList}">
                            <c:forEach var="user" items="${userList}">
                                <tr>
                                    <td>${user.userId}</td>
                                    <td>${user.username}</td>
                                    <td>${user.phoneNumber}</td>
                                    <td>${user.email}</td>
                                    <td>${user.status == 1 ? 'Active' : 'Banned'}</td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/manageAccount" method="post" >
                                            <input type="hidden" name="userId" value="${user.userId}">
                                            <label class="switch">
                                                <input type="checkbox" name="ban" value="2" ${user.status == 2 ? 'checked' : ''} onclick="return confirmBanChange(this, ${user.userId});">
                                                <span class="slider round"></span>
                                            </label>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="6">No users found.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </body>
    <script>
        function confirmBanChange(checkbox) {
            var form = checkbox.form;
            var isBanned = checkbox.checked;
            var confirmMessage;

            if (isBanned) {
                confirmMessage = "Are you sure you want to ban this user?";
            } else {
                confirmMessage = "Are you sure you want to unban this user?";
            }

            if (confirm(confirmMessage)) {
                // Nếu người dùng xác nhận, cho phép form submit
                form.submit();
            } else {
                // Nếu người dùng hủy, trả lại trạng thái checkbox ban đầu
                checkbox.checked = !isBanned;
                return false; // Ngừng gửi form
            }
        }
    </script>
</html>
