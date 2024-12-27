<%-- 
    Document   : Login
    Created on : Oct 30, 2024, 3:43:02 PM
    Author     : Windows 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="css/style.css"> <!-- Liên kết đến file CSS tùy chọn -->
    <style>
        body {
            font-family: Arial, sans-serif;
            /*background-image: url('https://cdn.thuvienphapluat.vn/phap-luat/2022/2/19/QK/thi-bang-lai-xe%2001.png');*/
            background-size: cover; /* Đảm bảo hình nền phủ kín toàn bộ */
            background-repeat: no-repeat; /* Không lặp lại hình nền */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            color: black; /* Đặt màu chữ thành trắng để nổi bật trên nền */
        }
        .login-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
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
        input[type="password"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .error-message {
            color: red;
            margin: 10px 0;
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
    <div class="login-container">
        <h2>Đăng Nhập</h2>
        
        <!-- Form đăng nhập -->
        <form action="login" method="post">
            <div class="form-group">
                <label for="username">Tên người dùng:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Mật khẩu:</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <input type="checkbox" id="rememberMe" name="rememberMe">Remember me
            </div>

            <!-- Hiển thị thông báo lỗi nếu có -->
            <c:if test="${not empty errorMessage}">
                <p class="error-message">${errorMessage}</p>
            </c:if>
            
            <button type="submit">Đăng Nhập</button>
        </form>

        <p>Chưa có tài khoản? <a href="Register.jsp">Đăng ký tại đây</a></p> <!-- Liên kết đến trang đăng ký -->
    </div>
</body>
</html>