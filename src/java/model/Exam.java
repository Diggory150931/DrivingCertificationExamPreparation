/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;

/**
 *
 * @author Windows 10
 */
public class Exam {
    private int examId;
    private int userId;
    private int score;
    private Timestamp takenAt;

    public Exam() {
    }

    public Exam(int examId, int userId, int score, Timestamp takenAt) {
        this.examId = examId;
        this.userId = userId;
        this.score = score;
        this.takenAt = takenAt;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Timestamp getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Timestamp takenAt) {
        this.takenAt = takenAt;
    }
    
    
}
