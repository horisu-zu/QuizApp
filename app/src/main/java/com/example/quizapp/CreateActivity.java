package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Adapters.CreateAnswerAdapter;
import com.example.quizapp.Listener.AnswerListener;
import com.example.quizapp.Models.QuizItem;
import com.example.quizapp.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CreateActivity extends AppCompatActivity {
    List<String> answers = new ArrayList<>();
    EditText questionEdit;
    ImageButton addButton, saveButton;
    RecyclerView answersRecycler;
    TextView correctAnswerView;
    CreateAnswerAdapter answerAdapter;

    private static int MAX_ANSWERS = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        questionEdit = findViewById(R.id.questionEdit);
        correctAnswerView = findViewById(R.id.correctAnswerView);
        answersRecycler = findViewById(R.id.answersRecyclerView);
        addButton = findViewById(R.id.addButton);
        saveButton = findViewById(R.id.saveButton);

        answerAdapter = new CreateAnswerAdapter(answers, createAnswerListener);
        answersRecycler.setAdapter(answerAdapter);

        correctAnswerView.setText("There's no correct answer right now...");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(answerAdapter.getItemCount() < MAX_ANSWERS) {
                    answers.add("");
                    answerAdapter.setAnswers(answers);
                }
                else {
                    Toast.makeText(CreateActivity.this,
                            "You already created maximum of answers", Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveQuiz();
                startActivity(new Intent(CreateActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void saveQuiz() {
        String question = questionEdit.getText().toString();
        String correctAnswer = correctAnswerView.getText().toString();
        List<String> answers = answerAdapter.getAnswers();

        QuizItem newQuizItem = new QuizItem(question, answers, correctAnswer);

        List<QuizItem> existingQuizItems = Utils.loadAllQuestions(this);

        existingQuizItems.add(newQuizItem);

        Utils.saveNotes(existingQuizItems, this);
    }

    private final AnswerListener createAnswerListener = new AnswerListener() {
        @Override
        public void onItemClick(int position) {
            String selectedAnswer = answerAdapter.getAnswers().get(position);

            correctAnswerView.setText(selectedAnswer);
        }
    };
}