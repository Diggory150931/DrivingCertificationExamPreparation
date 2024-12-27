<%-- 
    Document   : QuestBank
    Created on : Oct 31, 2024, 3:05:12 AM
    Author     : Windows 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Question" %>
<%@ page import="dal.QuestionDAO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Ngân Hàng Câu Hỏi</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
            background-color: #f2f2f2;
        }
        .header h1 {
            margin: 0;
        }
        .question-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
        .back-button {
            margin: 20px 0;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .back-button:hover {
            background-color: #0056b3;
        }
        .pagination {
            text-align: center;
            margin-top: 20px;
        }
        .pagination a {
            margin: 0 5px;
            text-decoration: none;
            color: #007bff;
            padding: 5px 10px;
            border: 1px solid #007bff;
            border-radius: 5px;
        }
        .pagination a:hover {
            background-color: #e7f3ff;
        }
        .pagination strong {
            margin: 0 5px;
            color: #4CAF50;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Ngân Hàng Câu Hỏi</h1>
        <a class="back-button" href="${pageContext.request.contextPath}/UserHome.jsp">Quay lại</a>
    </div>

    <table class="question-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Question</th>
                <th>Correct Answer</th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${empty questionList}">
                <tr>
                    <td colspan="3">Không có câu hỏi nào được tìm thấy.</td>
                </tr>
            </c:if>
            <c:forEach var="question" items="${questionList}">
                <tr>
                    <td>${question.questionId}</td>
                    <td>
                        <c:out value="${question.content}" escapeXml="false" />
                    </td>
                    <td>
                        <c:out value="${question.correctAnswer}" escapeXml="false" />
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="pagination">
        <%
            int currentPage = (Integer) request.getAttribute("currentPage");
            int totalPages = (Integer) request.getAttribute("totalPages");
            for (int i = 1; i <= totalPages; i++) {
                if (i == currentPage) {
        %>
                    <strong><%= i %></strong>
        <%
                } else {
        %>
                    <a href="<%= request.getContextPath() %>/user?page=<%= i %>"><%= i %></a>
        <%
                }
            }
        %>
    </div>
</body>
</html>
