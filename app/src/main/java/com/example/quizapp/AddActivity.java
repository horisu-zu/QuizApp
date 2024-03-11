package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

public class AddActivity extends AppCompatActivity {
    MaterialCardView quizCard, themeCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        quizCard = findViewById(R.id.quizCard);
        themeCard = findViewById(R.id.themeCard);

        quizCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });

        themeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, ThemeCreateActivity.class);
                startActivity(intent);
            }
        });
    }
}