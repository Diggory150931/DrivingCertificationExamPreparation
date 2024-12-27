/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ExamDAO;
import dal.QuestionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import model.Exam;
import model.Question;
import model.User;

/**
 *
 * @author Windows 10
 */
public class ExamController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ExamController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("doGet called");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int correctAnswers = 0;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            int userId = user.getUserId();

            QuestionDAO questionDAO = new QuestionDAO();
            List<Question> questionList = questionDAO.getRandomQuestions(20);

            ExamDAO examDAO = new ExamDAO();
            Exam exam = new Exam();
            exam.setUserId(userId);
            exam.setTakenAt(new java.sql.Timestamp(System.currentTimeMillis()));


            Map<Integer, String> userAnswers = new HashMap<>();
            for (Question question : questionList) {
                String answer = request.getParameter("answer_" + question.getQuestionId());
                if (answer == null || answer.trim().isEmpty()) {
                    logger.log(Level.WARNING, "No answer received for question ID: {0}", question.getQuestionId());
                } else {
                    userAnswers.put(question.getQuestionId(), answer);
                    logger.log(Level.INFO, "Answer for question ID {0}: {1}", new Object[]{question.getQuestionId(), answer});
                    examDAO.saveExamQuestion(exam.getExamId(), question.getQuestionId(), answer);
                }
            }

            // So sánh câu trả lời với đáp án
            for (Question question : questionList) {
                String correctAnswer = question.getCorrectAnswer().trim().toLowerCase();
                String userAnswer = userAnswers.get(question.getQuestionId());
                if (userAnswer != null && userAnswer.trim().toLowerCase().equals(correctAnswer)) {
                    correctAnswers++;
                }
            }

            // Tính điểm đậu/rớt
            int totalQuestions = questionList.size();
            double scorePercentage = ((double) correctAnswers / totalQuestions) * 100;
            boolean isPassed = scorePercentage >= 80;

            int score = correctAnswers;

            exam.setScore(score);
            examDAO.saveExam(exam);

            request.setAttribute("correctAnswers", correctAnswers);
            request.setAttribute("totalQuestions", totalQuestions);
            request.setAttribute("scorePercentage", scorePercentage);
            request.setAttribute("isPassed", isPassed);
            request.setAttribute("userAnswers", userAnswers);
            request.setAttribute("questionList", questionList);

            request.getRequestDispatcher("Result.jsp").forward(request, response);
        } else {
            response.sendRedirect("Login.jsp");
        }
    }
}
