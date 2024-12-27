/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ExamDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Exam;
import model.User;

/**
 *
 * @author Windows 10
 */
public class ExamHistoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            int userId = user.getUserId();
            ExamDAO examDAO = new ExamDAO();
            List<Exam> examHistory = examDAO.getExamHistoryByUserId(userId);

            request.setAttribute("examHistory", examHistory);
            request.getRequestDispatcher("ExamHistory.jsp").forward(request, response);
        } else {
            response.sendRedirect("login"); 
        }
    }
}
