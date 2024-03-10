package com.example.quizapp.Adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Listener.AnswerListener;
import com.example.quizapp.R;

import java.util.List;

public class CreateAnswerAdapter extends RecyclerView.Adapter<CreateAnswerAdapter.CreateViewHolder> {
    private List<String> answers;

    private AnswerListener listener;

    public CreateAnswerAdapter(List<String> answers, AnswerListener listener) {
        this.answers = answers;
        this.listener = listener;
        initializeAnswers(answers);
    }

    @NonNull
    @Override
    public CreateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.create_answers, parent, false);
        return new CreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateViewHolder holder, int position) {
        String answer = answers.get(position);

        holder.answerEditText.setText(answer);

        holder.setAnswerAsCorrectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(holder.getAdapterPosition());
                }
            }
        });

        holder.answerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                answers.set(holder.getAdapterPosition(), charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void initializeAnswers(List<String> answers) {
        for (int i = 0; i < 2; i++) {
            answers.add("");
        }
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public List<String> getAnswers() {return answers;}

    public void setAnswers(List<String> newAnswers) {
        this.answers = newAnswers;
        notifyDataSetChanged();
    }

    public static class CreateViewHolder extends RecyclerView.ViewHolder {
        EditText answerEditText;
        ImageButton setAnswerAsCorrectButton;

        public CreateViewHolder(View itemView) {
            super(itemView);

            answerEditText = itemView.findViewById(R.id.answer_edit);
            setAnswerAsCorrectButton = itemView.findViewById(R.id.setAnswerAsCorrectButton);
        }

        public void setItemClickListener(AnswerListener listener) {
            setAnswerAsCorrectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}


