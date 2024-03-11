package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.example.quizapp.Adapters.ThemeAdapter;
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

        themeAdapter = new ThemeAdapter(themeList);

        themeRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        themeRecycler.setAdapter(themeAdapter);
    }
}