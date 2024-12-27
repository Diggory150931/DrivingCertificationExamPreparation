<%-- 
    Document   : ExamHistory
    Created on : Nov 4, 2024, 5:07:13 AM
    Author     : Windows 10
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lịch sử làm bài</title>
    <style>
        .container {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #007bff;
            text-align: center;
        }
        .exam-item {
            margin-bottom: 15px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Lịch sử làm bài</h1>
        <c:forEach var="exam" items="${examHistory}">
            <div class="exam-item">
                <p><strong>Thời gian làm bài:</strong> ${exam.takenAt}</p>
                <p><strong>Điểm số:</strong> ${exam.score}</p>
            </div>
        </c:forEach>
        <c:if test="${empty examHistory}">
            <p>Không có lịch sử làm bài nào.</p>
        </c:if>
        <a href="UserHome.jsp">Quay lại trang chủ</a>
    </div>
</body>
</html>