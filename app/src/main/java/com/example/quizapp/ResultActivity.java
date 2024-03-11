package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

public class ResultActivity extends AppCompatActivity {

    MaterialCardView home;
    TextView correctt, wrongt, resultinfo;
    ImageView resultImage;
    ProgressBar percentageProgressBar;
    TextView percentText;
    int AllScore, correct, wrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        home = findViewById(R.id.returnHome);
        correctt = findViewById(R.id.correctScore);
        wrongt = findViewById(R.id.wrongScore);
        resultinfo = findViewById(R.id.resultInfo);
        resultImage = findViewById(R.id.resultImage);
        percentText = findViewById(R.id.resultPercentText);
        percentageProgressBar = findViewById(R.id.percentageProgressBar);

        correct = getIntent().getIntExtra("correct", 0);
        wrong = getIntent().getIntExtra("wrong", 0);
        AllScore = correct + wrong;

        percentText.setText(setPercent() + " %");
        percentageProgressBar.setProgress(setPercent());

        correctt.setText("" + correct);
        wrongt.setText("" + wrong);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private int setPercent() {
        int correctPercentage = (int) ((float) correct / AllScore * 100);

        return correctPercentage;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ResultActivity.this, MainActivity.class));
        finish();
    }
}