package com.example.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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
import com.example.quizapp.Models.Theme;
import com.example.quizapp.Utils.ThemeUtils;
import com.example.quizapp.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CreateActivity extends AppCompatActivity {
    List<String> answers = new ArrayList<>();
    List<Theme> themeList = new ArrayList<>();
    EditText questionEdit;
    ImageButton addButton, removeButton, saveButton;
    RecyclerView answersRecycler;
    TextView correctAnswerView, themeView;
    CreateAnswerAdapter answerAdapter;

    private static int MAX_ANSWERS = 4, MIN_ANSWERS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        questionEdit = findViewById(R.id.questionEdit);
        correctAnswerView = findViewById(R.id.correctAnswerView);
        themeView = findViewById(R.id.themeView);
        answersRecycler = findViewById(R.id.answersRecyclerView);
        addButton = findViewById(R.id.addButton);
        removeButton = findViewById(R.id.removeButton);
        saveButton = findViewById(R.id.saveButton);

        themeList.addAll(ThemeUtils.loadAllThemes(this));

        answerAdapter = new CreateAnswerAdapter(answers, createAnswerListener);
        answersRecycler.setAdapter(answerAdapter);

        correctAnswerView.setText("There's no correct answer right now...");

        themeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showThemeDialog();
            }
        });

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

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answerAdapter.getItemCount() > MIN_ANSWERS) {
                    answers.remove(answers.size() - 1);
                    answerAdapter.setAnswers(answers);
                } else {
                    Toast.makeText(CreateActivity.this, "You need at least "
                                    + MIN_ANSWERS + " answer", Toast.LENGTH_SHORT).show();
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
        String theme = themeView.getText().toString();
        if(theme.equals("theme..."))
            theme = "default";

        List<String> answers = answerAdapter.getAnswers();

        QuizItem newQuizItem = new QuizItem(question, answers, correctAnswer, theme);

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

    private void showThemeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateActivity.this);
        builder.setTitle("Select Theme")
                .setItems(getThemesTitles(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Theme selectedTheme = themeList.get(which);
                        themeView.setText(selectedTheme.getTheme());
                    }
                });
        builder.create().show();
    }

    private String[] getThemesTitles() {
        String [] themesTitles = new String[themeList.size()];
        for(int i = 0; i < themeList.size(); i++) {
            themesTitles[i] = themeList.get(i).getTheme();
        }
        return themesTitles;
    }
}