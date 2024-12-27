<%-- 
    Document   : Exam
    Created on : Oct 31, 2024, 9:35:19 AM
    Author     : Windows 10
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Question" %>
<%@ page import="model.User" %>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thi thử</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
        }
        .container {
            max-width: 800px;
            margin: 20px;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .side-panel {
            width: 150px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            position: fixed;
            top: 20px;
            right: 20px;
        }
        h1 {
            text-align: center;
            color: #007bff;
        }
        .question {
            border: 1px solid #007bff;
            border-radius: 5px;
            padding: 15px;
            margin: 10px 0;
            background-color: #e7f3ff;
        }
        .question p {
            font-weight: bold;
        }
        .answer {
            margin-top: 10px;
        }
        button {
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            padding: 10px 15px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
            width: 100%;
        }
        button:hover {
            background-color: #0056b3;
        }
        .question-status {
            list-style: none;
            padding: 0;
            text-align: center;
        }
        .question-status li {
            display: inline-block;
            width: 30px;
            height: 30px;
            margin: 5px;
            text-align: center;
            line-height: 30px;
            border-radius: 50%;
            cursor: pointer;
        }
        .question-status .answered {
            background-color: #007bff;
            color: #000;
        }
        .question-status .not-answered {
            background-color: #ffc107;
            color: #155724;
        }
        .timerr {
            font-size: 24px;
            text-align: center;
            margin-bottom: 20px;
            display: block !important;
            visibility: visible !important;
            color: black !important;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Thi thử</h1>
        
        <c:if test="${not empty questionList}">
            <form action="exem" method="post" id="examForm">
                <c:forEach var="question" items="${questionList}" varStatus="status">
                    <div class="question" id="question_${status.index}">
                        <p>${question.questionContent}</p>
                        
                        <div class="answer">
                            <c:forEach var="option" items="${question.answerOptions}">
                                <label>
                                    <input type="radio" name="answer_${question.questionId}" value="${option.substring(0, 1)}" onclick="markAnswered(${status.index})">
                                    ${option}
                                </label><br>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
                <div class="side-panel">
                    <div class="timerr" id="thoigian">00:00:00</div>
                    <ul class="question-status">
                        <c:forEach var="i" begin="0" end="${fn:length(questionList) - 1}">
                            <li class="not-answered" id="question_${i}" onclick="scrollToQuestion('question_${i}'); markAnswered('question_${i}')">
                                <c:out value="${i + 1}"/>
                                <script>
                                    console.log('Created li element with id: question_${i}');
                                </script>
                            </li>
                        </c:forEach>
                    </ul>
                    <button type="submit">Nộp bài</button>
                </div>
            </form>
        </c:if>
        
        <c:if test="${empty questionList}">
            <p>Không có câu hỏi nào để hiển thị.</p>
        </c:if>
    </div>

    <script>
        function markAnswered(questionId) {
            console.log('Mark Answered for Question ID:', questionId);
            
            const statusElement = document.getElementById(questionId);
            if (statusElement) {
                statusElement.classList.add('answered');
                statusElement.classList.remove('not-answered');
                console.log('Updated Question Element:', statusElement);
            } else {
                console.error('No element found with id:', questionId);
            }
        }
        
        function scrollToQuestion(questionId) {
            console.log('Scroll to Question:', questionId);
            
            const questionElement = document.getElementById(questionId);
            if (questionElement) {
                questionElement.scrollIntoView({behavior: 'smooth'});
            } else {
                console.error('No element found to scroll to with id:', questionId);
            }
        }
        
        let timerDuration = 300; // Set timer in seconds
                function startTimer() {
            let display = document.getElementById('thoigian');
            let timer = timerDuration, minutes, seconds;

            const countdown = setInterval(function () {
                minutes = parseInt(timer / 60, 10);
                seconds = parseInt(timer % 60, 10);

                minutes = minutes < 10 ? "0" + minutes : minutes;
                seconds = seconds < 10 ? "0" + seconds : seconds;

                display.textContent = minutes + ":" + seconds;

                if (--timer < 0) {
                    clearInterval(countdown);
                    document.getElementById('examForm').submit(); // Auto-submit 
                }
            }, 1000);
        }

        window.onload = startTimer; 
    </script>
</body>
</html>