package com.example.quizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quizapp.Models.Theme;
import com.example.quizapp.Utils.ThemeUtils;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class ThemeCreateActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextTheme;
    private ImageView imageViewTheme;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_create);

        editTextTheme = findViewById(R.id.editTextTheme);
        imageViewTheme = findViewById(R.id.imageViewTheme);

        ExtendedFloatingActionButton buttonCreateTheme = findViewById(R.id.buttonCreateTheme);
        buttonCreateTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTheme();
            }
        });

        imageViewTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });
    }

    private void createTheme() {
        String themeName = editTextTheme.getText().toString();

        if (themeName.isEmpty()) {
            Toast.makeText(this, "Please enter a theme name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imagePath == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        Theme newTheme = new Theme(themeName, imagePath);

        List<Theme> existingThemes = ThemeUtils.loadAllThemes(this);
        existingThemes.add(newTheme);
        ThemeUtils.saveThemes(existingThemes, this);

        Toast.makeText(this, "Theme created successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagePath = data.getData().toString();
            imageViewTheme.setImageURI(data.getData());
        }
    }
}
