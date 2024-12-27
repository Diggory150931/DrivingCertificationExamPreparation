/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.User;

/**
 *
 * @author Windows 10
 */
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(); 

//        if (session != null) {
//            // Chuyển hướng người dùng đến trang đăng nhập hoặc hiển thị thông báo
//            response.sendRedirect(request.getContextPath() + "/login"); // Hoặc trang khác
//            return;
//        }

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            request.setAttribute("updateError", "Bạn cần đăng nhập để cập nhật thông tin!");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return; 

        }
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(userId);

        request.setAttribute("user", user);
        request.getRequestDispatcher("MyProfile.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        // Kiểm tra userId = null
        if (userId == null) {
            request.setAttribute("updateError", "Bạn cần đăng nhập để cập nhật thông tin!");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phonenumber");
        String address = request.getParameter("address");
        String dobString = request.getParameter("dob");

        Date dob = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (dobString != null && !dobString.isEmpty()) {
                dob = dateFormat.parse(dobString); 
            }
        } catch (ParseException e) {
        }
        
        User user = new User();
        user.setUserId(userId); 
        user.setUsername(username);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);
        user.setDob((java.sql.Date) dob);

        UserDAO userDAO = new UserDAO();
        boolean isUpdated = userDAO.update(user); 

        if (isUpdated) {
            request.setAttribute("updateSuccess", "Cập nhật thông tin thành công!");
        } else {
            request.setAttribute("updateError", "Có lỗi xảy ra khi cập nhật thông tin.");
        }
        request.getRequestDispatcher("MyProfile.jsp").forward(request, response);

    }
}
