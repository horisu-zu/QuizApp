package com.example.quizapp.Models;

import java.util.List;

public class QuizItem {
    private String quizQuestion;
    private List<String> quizAnswers;
    private String quizCorrectAnswer;
    private String quizTheme;

    public QuizItem() {
    }

    public QuizItem(String quizQuestion, List<String> quizAnswers,
                    String quizCorrectAnswer, String quizTheme) {
        this.quizQuestion = quizQuestion;
        this.quizAnswers = quizAnswers;
        this.quizCorrectAnswer = quizCorrectAnswer;
        this.quizTheme = quizTheme;
    }

    public QuizItem(String quizQuestion, List<String> quizAnswers, String quizCorrectAnswer) {
        this.quizQuestion = quizQuestion;
        this.quizAnswers = quizAnswers;
        this.quizCorrectAnswer = quizCorrectAnswer;
    }

    public String getQuizTheme() {
        return quizTheme;
    }

    public void setQuizTheme(String quizTheme) {
        this.quizTheme = quizTheme;
    }

    public String getQuizQuestion() {
        return quizQuestion;
    }

    public void setQuizQuestion(String quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    public List<String> getQuizAnswers() {
        return quizAnswers;
    }

    public void setQuizAnswers(List<String> quizAnswers) {
        this.quizAnswers = quizAnswers;
    }

    public String getQuizCorrectAnswer() {
        return quizCorrectAnswer;
    }

    public void setQuizCorrectAnswer(String quizCorrectAnswer) {
        this.quizCorrectAnswer = quizCorrectAnswer;
    }
}
