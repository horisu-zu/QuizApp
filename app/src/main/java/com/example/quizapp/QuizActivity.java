    package com.example.quizapp;
    import static com.example.quizapp.Utils.Utils.loadAllQuestions;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.annotation.SuppressLint;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.example.quizapp.Adapters.AnswerAdapter;
    import com.example.quizapp.Listener.AnswerListener;
    import com.example.quizapp.Models.QuizItem;
    import com.example.quizapp.Utils.Utils;
    import com.google.android.material.dialog.MaterialAlertDialogBuilder;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.io.BufferedReader;
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Objects;

    public class QuizActivity extends AppCompatActivity {
        private static QuizActivity instance;
        TextView questionView;
        RecyclerView answersRecycler;
        public List<QuizItem> questionsItems;
        public static List<String> answersList;
        AnswerAdapter answerAdapter;
        private int correctAnswers = 0, wrongAnswers = 0, currentQuestion = 0;
        private String correctAnswer;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_quiz);
            instance = this;

            questionView = findViewById(R.id.questionView);
            answersRecycler = findViewById(R.id.answersRecycler);
            answersList = new ArrayList<>();
            loadQuestions();

            answerAdapter = new AnswerAdapter(answersList, answerListener);

            answersRecycler.setLayoutManager(new LinearLayoutManager(this));
            answersRecycler.setAdapter(answerAdapter);

            setQuestion(currentQuestion);
        }

        public void removeItem(int position) {
            questionsItems.remove(position);
        }

        @SuppressLint("NotifyDataSetChanged")
        private void setQuestion(int currentQuestion) {
            answersList.clear();

            QuizItem currentQuizItem = questionsItems.get(currentQuestion);
            answersList.addAll(currentQuizItem.getQuizAnswers());

            Log.d("QuizActivity", "Question: " + currentQuizItem.getQuizQuestion());
            Log.d("QuizActivity", "Answers: " + answersList.toString());

            questionView.setText(currentQuizItem.getQuizQuestion());
            correctAnswer = currentQuizItem.getQuizCorrectAnswer();

            answerAdapter.notifyDataSetChanged();
        }

        private void loadQuestions() {
            questionsItems = loadAllQuestions(this);
        }

        public static QuizActivity getInstance() {
            return instance;
        }

        private final AnswerListener answerListener = new AnswerListener() {
            @Override
            public void onItemClick(int position) {
                String selectedAnswer = answerAdapter.getAnswers().get(position);

                checkIfAnswerCorrect(selectedAnswer);

                if (currentQuestion == questionsItems.size() - 1) {
                    Intent resultIntent = new Intent(QuizActivity.this, ResultActivity.class);
                    resultIntent.putExtra("correct", correctAnswers);
                    resultIntent.putExtra("wrong", wrongAnswers);
                    startActivity(resultIntent);
                    finish();
                } else {
                    setQuestion(++currentQuestion);
                }
            }
        };

        private void checkIfAnswerCorrect(String selectedAnswer) {
            if(selectedAnswer.equals(correctAnswer))
                correctAnswers++;
            else
                wrongAnswers++;
        }

        /*@Override
        public void onBackPressed() {
            super.onBackPressed();

            MaterialAlertDialogBuilder materialAlertDialogBuilder =
                    new MaterialAlertDialogBuilder(QuizActivity.this);
            materialAlertDialogBuilder.setTitle(R.string.app_name);
            materialAlertDialogBuilder.setMessage("Are you sure want to exit the quiz?");
            materialAlertDialogBuilder.setNegativeButton(android.R.string.no,
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            materialAlertDialogBuilder.setPositiveButton(android.R.string.yes,
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(QuizActivity.this, MainActivity.class));
                    finish();
                }
            });
            materialAlertDialogBuilder.show();
        }*/
    }