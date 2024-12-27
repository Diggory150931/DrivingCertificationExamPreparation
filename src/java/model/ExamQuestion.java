/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Windows 10
 */
public class ExamQuestion {
    private int examQuestionId;
    private int examId;
    private int questionId;
    private char userAnswer;

    public ExamQuestion() {
    }

    public ExamQuestion(int examQuestionId, int examId, int questionId, char userAnswer) {
        this.examQuestionId = examQuestionId;
        this.examId = examId;
        this.questionId = questionId;
        this.userAnswer = userAnswer;
    }

    public int getExamQuestionId() {
        return examQuestionId;
    }

    public void setExamQuestionId(int examQuestionId) {
        this.examQuestionId = examQuestionId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public char getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(char userAnswer) {
        this.userAnswer = userAnswer;
    }
    
    
}
