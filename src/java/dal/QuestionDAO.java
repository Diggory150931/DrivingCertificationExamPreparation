/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Question;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Exam;

/**
 *
 * @author Windows 10
 */
public class QuestionDAO extends DBContext {

    public List<Question> getRandomQuestions(int count) {
        ArrayList<Question> questions = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM Question ORDER BY NEWID() OFFSET 0 ROWS FETCH NEXT ? ROWS ONLY";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, count);
            rs = stm.executeQuery();

            while (rs.next()) {
                Question question = new Question();
                question.setQuestionId(rs.getInt("questionId"));
                question.setContent(rs.getString("content"));
                question.setCorrectAnswer(rs.getString("correctAnswer"));
                questions.add(question);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Đóng ResultSet và PreparedStatement nếu cần thiết
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return questions;

    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        PreparedStatement stm = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Question";
        try {
            stm = connection.prepareStatement(query);
            rs = stm.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setQuestionId(rs.getInt("questionId"));
                question.setContent(rs.getString("content"));
                question.setCorrectAnswer(rs.getString("correctAnswer"));
                questions.add(question);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return questions;
    }

    public ArrayList<Question> getQuestionsByPage(int page, int pageSize) {
        ArrayList<Question> questions = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        // Cập nhật câu lệnh SQL với OFFSET và FETCH
        String query = "SELECT * FROM Question ORDER BY questionId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try {
            stm = connection.prepareStatement(query);
            stm.setInt(1, (page - 1) * pageSize); // Tính toán offset
            stm.setInt(2, pageSize);
            rs = stm.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setQuestionId(rs.getInt("questionId"));
                question.setContent(rs.getString("content"));
                question.setCorrectAnswer(rs.getString("correctAnswer"));
                questions.add(question);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return questions;
    }

    public int getTotalQuestions() {
        int total = 0;
        Statement stmt = null;
        ResultSet rs = null;

        String query = "SELECT COUNT(*) FROM Question";
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return total;
    }

    public boolean addQuestion(Question question) throws SQLException {
        String query = "INSERT INTO Question (content, correctAnswer) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, question.getContent());
            stmt.setString(2, String.valueOf(question.getCorrectAnswer()));
            return stmt.executeUpdate() > 0;
        }
    }

    // Phương thức cập nhật câu hỏi
    public boolean updateQuestion(Question question) throws SQLException {
        String query = "UPDATE Question SET content = ?, correctAnswer = ? WHERE questionId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, question.getContent());
            stmt.setString(2, String.valueOf(question.getCorrectAnswer()));
            stmt.setInt(3, question.getQuestionId());
            return stmt.executeUpdate() > 0;
        }
    }

    public Question getQuestionById(int questionId) {
        Question question = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Question WHERE questionId = ?";
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, questionId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                question = new Question();
                question.setQuestionId(rs.getInt("questionId"));
                question.setContent(rs.getString("content"));
                question.setCorrectAnswer(rs.getString("correctAnswer"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return question;
    }

    public boolean deleteQuestion(int questionId) {
        boolean isDeleted = false;
        PreparedStatement pstmt = null;

        String query = "DELETE FROM Question WHERE questionId = ?";
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, questionId);
            isDeleted = pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return isDeleted;
    }

    public List<Question> searchQuestions(String search) {
        List<Question> questionList = new ArrayList<>();
        if (search != null && !search.trim().isEmpty()) {
            try {
                String sql = "SELECT * FROM question WHERE content LIKE ?";
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, "%" + search.trim() + "%"); 
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            Question question = new Question();
                            question.setQuestionId(rs.getInt("questionId"));
                            question.setContent(rs.getString("content"));
                            question.setCorrectAnswer(rs.getString("correctAnswer"));
                            questionList.add(question);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return questionList;
    }

//        public void testGetAllQuestions() {
//        ArrayList<Question> questions = getAllQuestions();
//        if (questions.isEmpty()) {
//            System.out.println("No questions found.");
//        } else {
//            for (Question question : questions) {
//                System.out.println("Question ID: " + question.getQuestionId());
//                System.out.println("Content: " + question.getContent());
//                System.out.println("Correct Answer: " + question.getCorrectAnswer());
//                System.out.println("---------------------------");
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        QuestionDAO questionDAO = new QuestionDAO();
//        questionDAO.testGetAllQuestions();
//    }
//    public static void main(String[] args) {
//        QuestionDAO questionDAO = new QuestionDAO();
//        List<Question> questions = questionDAO.getRandomQuestions(5); // Lấy 5 câu hỏi ngẫu nhiên
//
//        if (questions.isEmpty()) {
//            System.out.println("Không có câu hỏi nào được tìm thấy.");
//        } else {
//            for (Question question : questions) {
//                System.out.println("ID: " + question.getQuestionId());
//                System.out.println("Nội dung: " + question.getContent());
//                System.out.println("Đáp án đúng: " + question.getCorrectAnswer());
//                System.out.println("----------");
//            }
//        }
//
//        // Đóng kết nối nếu không còn sử dụng
//        try {
//            if (questionDAO.connection != null && !questionDAO.connection.isClosed()) {
//                questionDAO.connection.close();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
