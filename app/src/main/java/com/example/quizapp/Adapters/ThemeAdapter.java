package com.example.quizapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizapp.Models.Theme;
import com.example.quizapp.R;

import org.w3c.dom.Text;

import java.util.List;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder>{

    List<Theme> themeList;

    public ThemeAdapter(List<Theme> themeList) {
        this.themeList = themeList;
    }

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.theme_item, parent, false);
        return new ThemeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeViewHolder holder, int position) {
        Theme theme = themeList.get(position);

        holder.themeTitle.setText(theme.getTheme());

        Glide.with(holder.itemView)
                .load(getImageUrlForTheme(theme))
                .placeholder(R.drawable.placeholder_image)
                .into(holder.themeIcon);
    }

    @Override
    public int getItemCount() {
        return themeList.size();
    }

    public static class ThemeViewHolder extends RecyclerView.ViewHolder {
        ImageView themeIcon;
        TextView themeTitle;

        public ThemeViewHolder(@NonNull View itemView) {
            super(itemView);

            themeIcon = itemView.findViewById(R.id.themeIcon);
            themeTitle = itemView.findViewById(R.id.themeTitle);
        }
    }

    private String getImageUrlForTheme(Theme theme) {
        return theme.getImagePath();
    }
}
