/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Exam;

/**
 *
 * @author Windows 10
 */
public class ExamDAO extends DBContext {

    public void saveExam(Exam exam) {
        String sql = "INSERT INTO Exam (userId, score, taken_at) VALUES (?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stm.setInt(1, exam.getUserId());
            stm.setInt(2, exam.getScore()); // Cần đảm bảo rằng score được thiết lập trong đối tượng Exam
            stm.setTimestamp(3, exam.getTakenAt());

            // Thực thi câu lệnh chèn
            int affectedRows = stm.executeUpdate();
            if (affectedRows > 0) {
                // Lấy examId được tự động sinh ra
                try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        exam.setExamId(generatedKeys.getInt(1)); // Thiết lập examId cho đối tượng Exam
                    } else {
                        throw new SQLException("Creating exam failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi nếu có
        }
    }



    public List<Exam> getExamHistoryByUserId(int userId) {
        List<Exam> examHistory = new ArrayList<>();
        String sql = "SELECT * FROM Exam WHERE userId = ? ORDER BY taken_at DESC";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Exam exam = new Exam();
                exam.setExamId(rs.getInt("examId"));
                exam.setUserId(rs.getInt("userId"));
                exam.setScore(rs.getInt("score"));
                exam.setTakenAt(rs.getTimestamp("taken_at"));
                examHistory.add(exam);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return examHistory;
    }

    public void saveExamQuestion(int examId, int questionId, String userAnswer) {
        String sql = "INSERT INTO ExamQuestion (examId, questionId, userAnswer) VALUES (?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, examId);
            stm.setInt(2, questionId);
            stm.setString(3, userAnswer);

            // Thực thi câu lệnh chèn
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi nếu có
        }
    }
}
