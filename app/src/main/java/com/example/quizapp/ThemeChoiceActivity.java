package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.quizapp.Adapters.ThemeAdapter;
import com.example.quizapp.Listener.AnswerListener;
import com.example.quizapp.Models.Theme;
import com.example.quizapp.Utils.ThemeUtils;

import java.util.ArrayList;
import java.util.List;

public class ThemeChoiceActivity extends AppCompatActivity {

    List<Theme> themeList;
    RecyclerView themeRecycler;
    ThemeAdapter themeAdapter;
    private boolean isThemeSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_choice);

        themeRecycler = findViewById(R.id.themeRecycler);
        themeList = new ArrayList<>();
        themeList = ThemeUtils.loadAllThemes(this);
        //themeList.addAll();

        themeAdapter = new ThemeAdapter(themeList, themeClickListener);

        themeRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        themeRecycler.setAdapter(themeAdapter);
    }

    private final AnswerListener themeClickListener = new AnswerListener() {
        @Override
        public void onItemClick(int position) {
            Theme selectedTheme = themeList.get(position);

            Intent quizIntent = new Intent(ThemeChoiceActivity.this,
                    QuizActivity.class);
            quizIntent.putExtra("theme", selectedTheme.getTheme());
            Log.e("Image path: ", selectedTheme.getImagePath());
            startActivity(quizIntent);
        }
    };
}