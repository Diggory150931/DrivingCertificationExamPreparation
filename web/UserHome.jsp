<%-- 
    Document   : UserHome
    Created on : Oct 30, 2024, 6:13:55 PM
    Author     : Windows 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                display: flex;
                flex-direction: column;
                height: 100vh;
            }
            .header {
                display: flex;
                justify-content: flex-end;
                align-items: center;
                background-color: #f4f4f4;
                padding: 15px 50px;
                border-bottom: 1px solid #ddd;
            }
            .header .user-info {
                position: relative;
                cursor: pointer;
            }
            .header .user-info:hover .dropdown {
                display: block;
            }
            .dropdown {
                display: none;
                position: absolute;
                right: 0;
                background-color: #fff;
                border: 1px solid #ddd;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                z-index: 1;
            }
            .dropdown a {
                display: block;
                padding: 10px;
                text-decoration: none;
                color: #333;
            }
            .dropdown a:hover {
                background-color: #f4f4f4;
            }
            .container {
                display: flex;
                flex: 1;
                padding: 200px;
                align-items: flex-start; /* Căn chỉnh phần tử lên trên cùng */
            }
            .text-box {
                flex: 0 0 33%; /* Đặt chiều rộng của ô text box là 1/3 không gian */
                border: 1px solid #ddd;
                padding: 20px;
                margin-right: 50px; /* Giảm khoảng cách bên phải */
                overflow-y: auto; /* Thêm thanh cuộn nếu cần */
            }
            .buttons {
                display: flex;
                flex-direction: column;
                margin-top: 120px; /* Thêm khoảng cách từ ô text box xuống button (100px + 20px cho tiêu đề) */
                margin-left: 250px; /* Nhích sang bên phải */
            }
            .buttons button {
                padding: 15px 55px; /* Tăng kích thước button */
                margin-bottom: 30px;
                border: 1px solid #ddd;
                background-color: #f4f4f4;
                cursor: pointer;
                font-size: 20px; /* Tăng kích thước chữ */
                transition: background-color 0.3s; /* Thêm hiệu ứng chuyển tiếp */
            }
            .buttons button:hover {
                background-color: aquamarine; /* Đổi màu thành xanh khi di chuột qua */
                color: red; /* Đổi màu chữ thành trắng khi di chuột qua */
            }
            .clo {
                color: green; /* Đảm bảo class clo được sử dụng đúng */
                font-size: 24px; /* Tăng kích thước chữ */
            }
            .fon {
                font-size: 50px;
                margin-top: 40px; /* Nhích xuống dưới khoảng 20px */
                text-align: center;
                margin-bottom: -100px;
            }
            .fon1{
                font-size: 20px;
            }
        </style>
    </head>
    <body>
        <div class="header">
            <div class="user-info">
                <% 
                    String username = (String) session.getAttribute("username");
                    if (username != null) { 
                %>
                <span><%= username %></span>
                <div class="dropdown">
                    <a href="${pageContext.request.contextPath}/MyProfile.jsp">My Profile</a>
                    <a href="${pageContext.request.contextPath}/logout">Logout</a>
                </div>
                <% } else { %>
                <span>Guest</span>
                <% } %>
            </div>
        </div>
        <h2 class="fon">Chúc bạn sớm thành công thi đậu</h2>
        <div class="container">
            <div class="text-box">
                <h5 class="clo">PHẦN MỀM LUYỆN THI LÝ THUYẾT 200 CÂU A1</h5>
                <h class="fon1">Phần mềm được phát triển dựa trên cấu trúc bộ đề thi sát hạch lý thuyết lái xe mô tô hạng A1 do Tổng Cục Đường Bộ Việt Nam quy định trong kỳ thi sát hạch chính thức.

                    Để tập phần thi lý thuyết bằng lái xe A1 tốt nhất, các học viên có thể sử dụng trực tiếp 8 bộ đề thi này. Bởi chúng tôi đã tổng hợp đầy đủ 200 câu hỏi thi bằng lái xe máy A1 đã đánh dấu sẵn đáp án và câu hỏi điểm liệt.

                    Học viên có thể sử dụng trực tiếp phần mềm luyện ôn thi GPLX A1 online này trực tiếp trên điện thoại iphone & android hoặc máy tính mà không cần phải tải về hay cài đặt. Chỉ yêu cầu có kết nối mạng wifi/4G vô cùng tiện lợi.

                    Nếu có bất kỳ thắc mắc cần giải đáp về câu hỏi trong đề thi, học viên hãy liên hệ với chúng tôi qua fanpage <a href="https://www.facebook.com/profile.php?id=61568973376133">Cùng Học Lái Xe</a> để chúng tôi giải đáp trực tiếp!</h>

            </div>
            <div class="buttons">
                
                <button onclick="location.href = '${pageContext.request.contextPath}/user'">Ngân hàng câu hỏi</button>
                
                <form action="user" method="post">
                    <button onclick="location.href = '${pageContext.request.contextPath}/Exam.jsp'">Tham gia thi thử</button>
                </form>
                
                <button onclick="location.href = '${pageContext.request.contextPath}/exhistory'">Kiểm tra lịch sử điểm</button>
            </div>
        </div>
    </body>
</html>