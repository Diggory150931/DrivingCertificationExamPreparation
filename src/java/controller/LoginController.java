/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Windows 10
 */
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleAutoLogin(request, response);
    }

    private void handleAutoLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String savedUsername = null;
        String savedPassword = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    savedUsername = cookie.getValue();
                } else if ("password".equals(cookie.getName())) {
                    savedPassword = cookie.getValue();
                }
            }
        }

        if (savedUsername != null && savedPassword != null) {
            try {
                UserDAO userDAO = new UserDAO();
                User user = userDAO.authenticateUser(savedUsername, savedPassword);

                if (user != null) {
                    if (user.getStatus() == 2) { 
                        response.sendRedirect("AccessDenied.jsp");
                        return;
                    }

                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("username", savedUsername);
                    redirectUser(response, user.getRole());
                    return;
                }
            } catch (SQLException e) {
                throw new ServletException("Database connection error.");
            }
        }
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        HttpSession session = request.getSession();

        try {
            UserDAO userDao = new UserDAO();
            User user = userDao.authenticateUser(username, password);

            if (user != null) {
                // check status
                if (user.getStatus() == 2) { 
                    response.sendRedirect("AccessDenied.jsp");
                    return;
                }

                session.setAttribute("user", user);
                session.setAttribute("username", username);

                if ("on".equals(rememberMe)) {
                    Cookie c_username = new Cookie("username", username);
                    c_username.setMaxAge(3600*24*7); 
                    Cookie c_password = new Cookie("password", password);
                    c_password.setMaxAge(3600*24*7); 

                    response.addCookie(c_username);
                    response.addCookie(c_password);
                } else {
                    Cookie c_username = new Cookie("username", "");
                    c_username.setMaxAge(0);
                    Cookie c_password = new Cookie("password", "");
                    c_password.setMaxAge(0);

                    response.addCookie(c_username);
                    response.addCookie(c_password);
                }

                Cookie userRoleCookie = new Cookie("userRole", String.valueOf(user.getRole()));
                userRoleCookie.setMaxAge(3600 * 24 * 7); 
                response.addCookie(userRoleCookie);

                redirectUser(response, user.getRole());
            } else {
                request.setAttribute("errorMessage", "Invalid Username or Passowrd!");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Database error during login process.");
        }
    }

    private void redirectUser(HttpServletResponse response, int role) throws IOException {
        if (role == 1) { 
            response.sendRedirect("adm?action=listQuestions");
        } else if (role == 2) { // user
            response.sendRedirect("UserHome.jsp");
        }
    }
}
