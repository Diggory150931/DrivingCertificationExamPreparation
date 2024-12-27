/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Windows 10
 */
public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String dobString = request.getParameter("dob");

        Vector<User> users = (new UserDAO()).getAll();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                request.setAttribute("duplicateUsername", "Username already exists");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
                return; // Thêm return để tránh tiếp tục thực hiện các câu lệnh bên dưới
            }
        }

        Date dob = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (dobString != null && !dobString.isEmpty()) {
                dob = dateFormat.parse(dobString);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        UserDAO userDao = new UserDAO();
        User user = new User(0, username, password, 2, email, dob, address, phone, 1);

        try {
            // Chuyển đổi java.util.Date thành java.sql.Date
            if (dob != null) {
                java.sql.Date sqlDob = new java.sql.Date(dob.getTime());
                user.setDob(sqlDob);
            }

            userDao.insert(user);
            request.setAttribute("registerSuccess", "Register successful");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("registerError", "Registration failed. Please try again.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }
    }
}
