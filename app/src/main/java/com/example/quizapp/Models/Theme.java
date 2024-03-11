package com.example.quizapp.Models;

public class Theme {
    private String theme;
    private String imagePath;

    public Theme(String theme, String imagePath) {
        this.theme = theme;
        this.imagePath = imagePath;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
