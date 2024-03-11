package com.example.quizapp.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.quizapp.Models.Theme;

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

public class ThemeUtils {

    private static JSONArray createThemeJSONObject(List<Theme> themes) throws JSONException {
        JSONArray themesArray = new JSONArray();

        for (Theme theme : themes) {
            JSONObject themeObject = new JSONObject();
            themeObject.put("theme", theme.getTheme());
            themeObject.put("imagePath", theme.getImagePath());
            themesArray.put(themeObject);
        }

        return themesArray;
    }

    public static List<Theme> loadAllThemes(Context context) {
        List<Theme> themes = new ArrayList<>();
        String result = readThemes(context);

        Log.e("themes", result);

        if (result.equals("")) {
            Log.d("themes", "No themes found in the file.");
            return themes;
        }

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray themesArray = jsonObject.getJSONArray("themes");

            for (int i = 0; i < themesArray.length(); i++) {
                JSONObject themeObject = themesArray.getJSONObject(i);
                String theme = themeObject.optString("theme", "");
                String imagePath = themeObject.optString("imagePath", "");
                themes.add(new Theme(theme, imagePath));
            }
        } catch (JSONException e) {
            Log.e("themes", "Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        }

        return themes;
    }

    private static String readThemes(Context context) {
        StringBuilder result = new StringBuilder();
        File themesFile = new File(context.getFilesDir(), "themes.json");
        Log.e("themes path: ", context.getFilesDir().getPath());

        if (!themesFile.exists()) {
            try {
                themesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileInputStream in = context.openFileInput("themes.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;

            while ((line = br.readLine()) != null)
                result.append(line).append("\n");

        } catch (Exception e) {
            Log.e("Themes: ", e.getMessage());
        }

        return result.toString();
    }

    public static void saveThemes(List<Theme> themes, Context context) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("themes", createThemeJSONObject(themes));
            saveThemes(jsonObject.toString(), context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void saveThemes(String file, Context context) {
        try {
            FileOutputStream out = context.openFileOutput("themes.json", Context.MODE_PRIVATE);

            out.write(file.getBytes());
            out.close();

            Toast.makeText(context, "Themes file saved!",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
