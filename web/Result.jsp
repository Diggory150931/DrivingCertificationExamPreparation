<%-- 
    Document   : Result
    Created on : Nov 4, 2024, 4:06:55 AM
    Author     : Windows 10
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Kết quả bài thi</title>
        <style>
            .container {
                max-width: 600px;
                margin: 20px auto;
                padding: 20px;
                background-color: #f8f9fa;
                border-radius: 8px;
                text-align: center;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            h1 {
                color: #007bff;
            }
            .result {
                font-size: 18px;
                margin: 20px 0;
            }
            .result.pass {
                color: green;
            }
            .result.fail {
                color: red;
            }
            .question {
                margin-bottom: 15px;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Kết quả của bạn</h1>
            <p>Bạn đã trả lời đúng ${correctAnswers} trên ${totalQuestions} câu.</p>
            <c:choose>
                <c:when test="${isPassed}">
                    <p class="result pass">Chúc mừng! Bạn đã đậu.</p>
                </c:when>
                <c:otherwise>
                    <p class="result fail">Bạn đã rớt.</p>
                </c:otherwise>
            </c:choose>

            <h2>Chi tiết bài làm:</h2>
            <c:forEach var="question" items="${questionList}">
                <div class="question">
                    <p><strong>Câu hỏi:</strong> ${question.content}</p>
                    <p><strong>Đáp án của bạn:</strong>
                        <span style="color: ${userAnswers[question.questionId] == question.correctAnswer ? 'green' : 'red'}">
                            ${userAnswers[question.questionId]}
                        </span>
                    </p>
                    <c:if test="${userAnswers[question.questionId] != question.correctAnswer}">
                        <p><strong>Đáp án đúng:</strong> ${question.correctAnswer}</p>
                    </c:if>
                </div>
            </c:forEach>
            <a href="UserHome.jsp">Quay lại trang chủ</a>
        </div>
    </body>
</html>