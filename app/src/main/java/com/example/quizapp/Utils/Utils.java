package com.example.quizapp.Utils;

import static com.example.quizapp.QuizActivity.answersList;
import static com.example.quizapp.QuizActivity.getInstance;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.quizapp.Models.QuizItem;
import com.example.quizapp.QuizActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static JSONArray createNoteJSONObject(List<QuizItem> quizItems) throws JSONException {
        JSONArray notesArray = new JSONArray();

        for (int i = 0; i < quizItems.size(); i++) {
            JSONObject noteObject = new JSONObject();

            noteObject.put("question", quizItems.get(i).getQuizQuestion());
            List<String> answersList = quizItems.get(i).getQuizAnswers();
            for (int j = 0; j < answersList.size(); j++) {
                noteObject.put("answer#" + j, answersList.get(j));
            }
            noteObject.put("correct", quizItems.get(i).getQuizCorrectAnswer());
            noteObject.put("theme", quizItems.get(i).getQuizTheme());

            notesArray.put(noteObject);
        }

        return notesArray;
    }

    public static List<QuizItem> loadAllQuestions(Context context) {
        List<QuizItem> quizItems = new ArrayList<>();
        String result = readNotes(context);

        Log.e("questions", result);

        if (result.equals("")) {
            Log.d("questions", "No notes found in the file.");
            return quizItems;
        }

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray questionsArray = jsonObject.getJSONArray("questions");

            for (int i = 0; i < questionsArray.length(); i++) {
                JSONObject noteObject = questionsArray.getJSONObject(i);

                String question = noteObject.optString("question", "");
                String correct = noteObject.optString("correct", "");
                String theme = noteObject.optString("theme", "");
                List<String> answersList = new ArrayList<>();
                int j = 0;
                while (noteObject.has("answer#" + j)) {
                    String answer = noteObject.getString("answer#" + j);
                    answersList.add(answer);
                    j++;
                }

                quizItems.add(new QuizItem(question, answersList, correct, theme));
            }
        } catch (JSONException e) {
            Log.e("questions", "Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        }

        return quizItems;
    }

    /*public static void deleteQuestion(int position) {
        List<QuizItem> quizItems = loadAllQuestions();

        if (position >= 0 && position < quizItems.size()) {
            quizItems.remove(position);
            saveNotes(quizItems);
        } else {
            Log.e("Utils", "Invalid position for deletion: " + position);
        }
    }*/

    private static String readNotes(Context context) {
        StringBuilder result = new StringBuilder();
        File notesFile = new File(context.getFilesDir(), "questions.json");
        Log.e("questions path: ", context.getFilesDir().getPath());

        if (!notesFile.exists()) {
            try {
                notesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileInputStream in = context.openFileInput("questions.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;

            while ((line = br.readLine()) != null)
                result.append(line).append("\n");

        } catch (Exception e) {
            Log.e("Questions: ", e.getMessage());
        }

        return result.toString();
    }

    public static void saveNotes(List<QuizItem> quizItems, Context context) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("questions", createNoteJSONObject(quizItems));
            saveNotes(jsonObject.toString(), context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void saveNotes(Context context) {
        JSONObject jsonObject = new JSONObject();

        try {
            List<QuizItem> versionNotesData = getInstance().questionsItems;

            jsonObject.put("questions", createNoteJSONObject(versionNotesData));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        saveNotes(jsonObject.toString(), context);
    }

    private static void saveNotes(String file, Context context) {
        try {
            FileOutputStream out = context.openFileOutput("questions.json", Context.MODE_PRIVATE);

            out.write(file.getBytes());
            out.close();

            Toast.makeText(context, "Файл збережено!",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Помилка: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

}
