<%-- 
    Document   : EditQuestion
    Created on : Oct 31, 2024, 6:48:24 AM
    Author     : Windows 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Edit Question</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
        }
        .container {
            width: 50%;
            margin: 50px auto;
            padding: 20px;
            background-color: #ffffff;
            box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
            border-radius: 8px;
        }
        h2 {
            text-align: center;
            color: #ffc107;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        input[type="text"], textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #dddddd;
            border-radius: 4px;
            font-size: 16px;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #ffc107;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }
        button:hover {
            background-color: #e0a800;
        }
        .back-link {
            display: inline-block;
            margin-top: 10px;
            color: #007bff;
            text-decoration: none;
        }
        .error-message {
            color: red;
            font-size: 14px;
            margin-bottom: 10px;
        }
        .success-message {
            color: green;
            font-size: 14px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Edit Question</h2>
        
        <!-- Hiển thị lỗi nếu có -->
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <!-- Hiển thị thông báo thành công nếu cập nhật thành công -->
        <c:if test="${not empty success}">
            <div class="success-message">${success}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/adm" method="post">
            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="questionId" value="${question.questionId}">
            
            <div class="form-group">
                <label for="content">Question Content:</label>
                <textarea id="content" name="content" rows="4" required>${question.content}</textarea>
            </div>
            
            <div class="form-group">
                <label for="correctAnswer">Correct Answer:</label>
                <input type="text" id="correctAnswer" name="correctAnswer" value="${question.correctAnswer}" required>
            </div>
            
            <button type="submit">Update Question</button>
        </form>

        <a href="${pageContext.request.contextPath}/adm" class="back-link">Back to Question List</a>
    </div>

    <!-- Optional: Thêm JavaScript để kiểm tra các trường trước khi submit -->
    <script>
        document.querySelector('form').addEventListener('submit', function(event) {
            const content = document.getElementById('content').value.trim();
            const correctAnswer = document.getElementById('correctAnswer').value.trim();
            if (!content || !correctAnswer) {
                alert("Vui lòng điền đầy đủ thông tin cho các trường bắt buộc.");
                event.preventDefault(); // Ngăn không cho form submit
            }
        });
    </script>
</body>
</html>