/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.QuestionDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Question;
import java.sql.*;
import java.util.Vector;
import model.User;

/**
 *
 * @author Windows 10
 */
public class AdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuestionDAO questionDAO = new QuestionDAO();
        String action = request.getParameter("action");
        String searchQuery = request.getParameter("searchQuery");
        
        if ("search".equals(action) || searchQuery != null) {
            handleSearch(request, response, questionDAO, searchQuery);
        } else if ("add".equals(action)) {
            handleAddGet(request, response);
        } else if ("edit".equals(action)) {
            handleEditGet(request, response, questionDAO);
        } else if ("delete".equals(action)) {
            handleDeleteGet(request, response, questionDAO);
        } else if ("listQuestions".equals(action) || action == null) {
            String pageParam = request.getParameter("page");
            int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
            int pageSize = 10;

            ArrayList<Question> questionList = questionDAO.getQuestionsByPage(page, pageSize);
            for (Question question : questionList) {
                String content = question.getContent().replace("\n", "<br>");
                question.setContent(content);
            }
            int totalQuestions = questionDAO.getTotalQuestions();
            int totalPages = (int) Math.ceil((double) totalQuestions / pageSize);

            request.setAttribute("questionList", questionList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("AdminHome.jsp").forward(request, response);
        }
    }

    private void handleSearch(HttpServletRequest request, HttpServletResponse response, QuestionDAO questionDAO, String searchQuery)
            throws ServletException, IOException {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/adm"); // Nếu không có từ khóa tìm kiếm, trả về trang chính
            return;
        }

        // Tìm câu hỏi theo từ khóa
        ArrayList<Question> questionList = (ArrayList<Question>) questionDAO.searchQuestions(searchQuery);

        // Lấy số trang và các tham số liên quan như khi liệt kê câu hỏi
        int pageSize = 10;
        int totalQuestions = questionList.size();
        int totalPages = (int) Math.ceil((double) totalQuestions / pageSize);
        int currentPage = 1;

        // Thiết lập lại các tham số vào request
        request.setAttribute("questionList", questionList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchQuery", searchQuery); // Truyền từ khóa tìm kiếm vào JSP

        // Chuyển tiếp đến AdminHome.jsp
        request.getRequestDispatcher("AdminHome.jsp").forward(request, response);
    }

    private void handleAddGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // -> AddQuestion.jsp để nhập câu hỏi
        request.getRequestDispatcher("/AddQuestion.jsp").forward(request, response);
    }

    private void handleEditGet(HttpServletRequest request, HttpServletResponse response, QuestionDAO questionDAO)
            throws ServletException, IOException {
        String idParam = request.getParameter("questionId");
        if (idParam != null) {
            int questionId = Integer.parseInt(idParam);
            Question question = questionDAO.getQuestionById(questionId);
            if (question != null) {
                request.setAttribute("question", question);
                request.getRequestDispatcher("/EditQuestion.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/adm");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/adm");
        }
    }

    private void handleDeleteGet(HttpServletRequest request, HttpServletResponse response, QuestionDAO questionDAO)
            throws ServletException, IOException {
        String idParam = request.getParameter("questionId");
        if (idParam != null) {
            int questionId = Integer.parseInt(idParam);
            boolean isDeleted = questionDAO.deleteQuestion(questionId);
            if (isDeleted) {
                response.sendRedirect(request.getContextPath() + "/adm");
            } else {
                request.setAttribute("error", "Không thể xóa câu hỏi.");
                response.sendRedirect(request.getContextPath() + "/adm");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/adm");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (null != action) {
            switch (action) {
                case "add" ->
                    handleAddQuestion(request, response);
                case "edit" ->
                    handleEditQuestion(request, response);
                case "delete" ->
                    handleDeleteQuestion(request, response);
                default -> {
                }
            }
        }
    }

    private void handleEditQuestion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("questionId");
        String content = request.getParameter("content");
        String correctAnswer = request.getParameter("correctAnswer");

        if (idParam == null || content == null || correctAnswer == null) {
            request.setAttribute("error", "Các trường không được để trống.");
            request.getRequestDispatcher("/EditQuestion.jsp").forward(request, response);
            return;
        }

        int questionId = Integer.parseInt(idParam);
        Question question = new Question();
        question.setQuestionId(questionId);
        question.setContent(content);
        question.setCorrectAnswer(correctAnswer);

        try {
            QuestionDAO questionDAO = new QuestionDAO();
            boolean isUpdated = questionDAO.updateQuestion(question);

            if (isUpdated) {
                response.sendRedirect(request.getContextPath() + "/adm");
            } else {
                request.setAttribute("error", "Có lỗi khi cập nhật câu hỏi.");
                request.getRequestDispatcher("/EditQuestion.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("/EditQuestion.jsp").forward(request, response);
        }
    }

    private void handleDeleteQuestion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("questionId");

        if (idParam == null) {
            request.setAttribute("error", "ID câu hỏi không hợp lệ.");
            response.sendRedirect(request.getContextPath() + "/adm");
            return;
        }

        int questionId = Integer.parseInt(idParam);

        QuestionDAO questionDAO = new QuestionDAO();
        boolean isDeleted = questionDAO.deleteQuestion(questionId);
        if (isDeleted) {
            response.sendRedirect(request.getContextPath() + "/adm");
        } else {
            request.setAttribute("error", "Có lỗi khi xóa câu hỏi.");
            response.sendRedirect(request.getContextPath() + "/adm");
        }
    }

    private void handleAddQuestion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String content = request.getParameter("content");
        String correctAnswer = request.getParameter("correctAnswer");

        if (content == null || content.isEmpty() || correctAnswer == null || correctAnswer.isEmpty()) {
            request.setAttribute("error", "Các trường nội dung và câu trả lời không được để trống.");
            request.getRequestDispatcher("/AddQuestion.jsp").forward(request, response);
            return;
        }

        Question question = new Question();
        question.setContent(content);
        question.setCorrectAnswer(correctAnswer);

        QuestionDAO questionDAO = new QuestionDAO();
        try {
            boolean isAdded = questionDAO.addQuestion(question);
            if (isAdded) {
                //Add ok -> về home
                response.sendRedirect(request.getContextPath() + "/adm");
            } else {
                request.setAttribute("error", "Có lỗi khi thêm câu hỏi.");
                request.getRequestDispatcher("/AddQuestion.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("/AddQuestion.jsp").forward(request, response);
        }
    }
}
