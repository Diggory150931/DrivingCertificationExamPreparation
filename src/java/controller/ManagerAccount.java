/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;
import model.User;

/**
 *
 * @author Windows 10
 */
public class ManagerAccount extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        handleAccountManagerGet(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        handleAccountManagerPost(request, response);
    }

    private void handleAccountManagerGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("search"); 
        UserDAO userDAO = new UserDAO();
        Vector<User> userList;

        if (search != null && !search.trim().isEmpty()) {
            userList = userDAO.searchUsers(search);
        } else {
            userList = userDAO.getAllAccount();
        }

        request.setAttribute("userList", userList);
        request.getRequestDispatcher("/AccountManager.jsp").forward(request, response);
    }

    private void handleAccountManagerPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        boolean ban = request.getParameter("ban") != null;

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByIdd(userId);
        if (user != null) {
            int newStatus = ban ? 2 : 1; 
            userDAO.updateUserStatus(userId, newStatus);
        }
        response.sendRedirect(request.getContextPath() + "/manageAccount");
    }
}
