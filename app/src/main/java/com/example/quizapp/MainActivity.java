package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.quizapp.Models.Theme;
import com.example.quizapp.Utils.ThemeUtils;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MaterialCardView quizCard, createCard, aboutCard;
    private static final String PREF_NAME = "MyPrefsFile";
    private static final String RUN_KEY = "firstRun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quizCard = findViewById(R.id.quizCard);
        createCard = findViewById(R.id.addCard);
        aboutCard = findViewById(R.id.aboutCard);

        SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);
        boolean firstRun = settings.getBoolean(RUN_KEY, true);

        if (firstRun) {
            ThemeUtils.clearThemes(this);

            createInitialThemes();

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(RUN_KEY, false);
            editor.apply();
        }

        //createInitialThemes();

        quizCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        ThemeChoiceActivity.class);
                startActivity(intent);
            }
        });

        createCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createInitialThemes() {
        List<Theme> initialThemes = new ArrayList<>();

        initialThemes.add(new Theme("Universal", getResourceUri(R.drawable.quiz_icon)));
        initialThemes.add(new Theme("History", getResourceUri(R.drawable.globe_icon)));
        initialThemes.add(new Theme("Sport", getResourceUri(R.drawable.sport_icon)));
        initialThemes.add(new Theme("Videogames", getResourceUri(R.drawable.videogame_icon)));
        initialThemes.add(new Theme("Anime", getResourceUri(R.drawable.anime_icon)));

        ThemeUtils.saveThemes(initialThemes, this);
    }

    private String getResourceUri(int resourceId) {
        return Uri.parse("android.resource://" + getPackageName() + "/" + resourceId).toString();
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();

        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(MainActivity.this);
        materialAlertDialogBuilder.setTitle(R.string.app_name);
        materialAlertDialogBuilder.setMessage("Are you sure want to exit the app?");
        materialAlertDialogBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        materialAlertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        materialAlertDialogBuilder.show();
    }*/
}