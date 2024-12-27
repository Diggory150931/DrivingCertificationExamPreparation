/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Windows 10
 */
public class Question {
    private int questionId;
    private String content;
    private String correctAnswer;

    public Question() {
    }

    public Question(int questionId, String content, String correctAnswer) {
        this.questionId = questionId;
        this.content = content;
        this.correctAnswer = correctAnswer;
    }

    public List<String> getAnswerOptions() {
        List<String> options = new ArrayList<>();
        String[] lines = content.split("\n");
        for (String line : lines) {
            if (line.startsWith("A.") || line.startsWith("B.") || line.startsWith("C.") || line.startsWith("D.")) {
                options.add(line.trim());
            }
        }
        return options;
    }
    
    public String getQuestionContent() {
        String[] lines = content.split("\n");
        return lines.length > 0 ? lines[0].trim() : "";
    }
    
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
