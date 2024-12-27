<%-- 
    Document   : AdminHome
    Created on : Oct 31, 2024, 2:57:02 AM
    Author     : Windows 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Question" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Admin Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }
        .header {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .user-info {
            position: relative;
        }
        .user-info span {
            margin-right: 15px;
            font-weight: bold;
        }
        .dropdown {
            display: none;
            position: absolute;
            background-color: white;
            border: 1px solid #ccc;
            box-shadow: 0 2px 5px rgba(0,0,0,0.2);
            z-index: 1;
        }
        .user-info:hover .dropdown {
            display: block;
        }
        .dropdown a {
            display: block;
            padding: 10px;
            color: black;
            text-decoration: none;
        }
        .dropdown a:hover {
            background-color: #f1f1f1;
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
        .pagination {
            text-align: center;
            margin-top: 20px;
        }
        .pagination a {
            margin: 0 5px;
            text-decoration: none;
            color: #4CAF50;
        }
        .pagination strong {
            margin: 0 5px;
            color: #4CAF50;
        }
        .add-button {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            margin-left: 20px;
        }
        .add-button:hover {
            background-color: #0056b3;
        }
        .action-button {
            padding: 5px 10px;
            margin: 0 5px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .edit-button {
            background-color: #ffc107;
            color: white;
        }
        .delete-button {
            background-color: #dc3545;
            color: white;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Question List</h1>
        <a class="add-button" href="${pageContext.request.contextPath}/AddQuestion.jsp">Add Question</a>
        <a class="add-button" href="${pageContext.request.contextPath}/manageAccount">Account Manager</a> 
        <div class="user-info">
            <c:choose>
                <c:when test="${not empty sessionScope.username}">
                    <span>${sessionScope.username}</span>
                    <div class="dropdown">
                        <a href="${pageContext.request.contextPath}/MyProfile.jsp">My Profile</a>
                        <a href="${pageContext.request.contextPath}/logout">Logout</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <span>Guest</span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

<!--    <div class="search-container" style="margin: 20px auto; text-align: center;">
        <form action="${pageContext.request.contextPath}/adm" method="get">
            <input type="text" name="search" placeholder="Search Question" value="${param.search}" style="padding: 8px 15px; width: 300px;">
            <button type="submit" class="add-button" style="padding: 8px 15px;">Search</button>
        </form>
    </div>-->

    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Question</th>
                    <th>Correct Answer</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty questionList}">
                        <c:forEach var="question" items="${questionList}">
                            <tr>
                                <td>${question.questionId}</td>
                                <td><c:out value="${question.content}" escapeXml="false"/></td>
                                <td><c:out value="${question.correctAnswer}" escapeXml="false"/></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/adm?action=edit&questionId=${question.questionId}">
                                        <button class="action-button edit-button">Edit Question</button>
                                    </a>
                                    <form action="adm" method="post" style="display: inline;" onsubmit="return confirmDelete(this, ${question.questionId});">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="questionId" value="${question.questionId}">
                                        <button type="submit" class="action-button delete-button">Delete Question</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="4">Không có câu hỏi nào được tìm thấy.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>

    <div class="pagination">
        <c:set var="currentPage" value="${currentPage}"/>
        <c:set var="totalPages" value="${totalPages}"/>
        <c:forEach var="i" begin="1" end="${totalPages}">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <strong>${i}</strong>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/adm?page=${i}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
</body>
<script>
    function confirmDelete(form, questionId) {
        if (confirm('Are you sure you want to delete this question?')) {
            const row = form.closest('tr');
            row.style.display = 'none';
            return true;
        }
        return false;
    }
</script>
</html>
