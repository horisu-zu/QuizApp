package com.example.quizapp.Adapters;

import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Listener.AnswerListener;
import com.example.quizapp.R;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private List<String> answers;
    private AnswerListener listener;
    public AnswerAdapter(List<String> answers, AnswerListener listener) {
        this.answers = answers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answers_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String answer = answers.get(position);

        holder.answerTextView.setText(answer);

        holder.answerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    public List<String> getAnswers() {return answers;}

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView answerCard;
        TextView answerTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            answerCard = itemView.findViewById(R.id.answers_container);
            answerTextView = itemView.findViewById(R.id.answer_text);
        }
    }
}
