package com.evanrjohnso.quickquiz.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.evanrjohnso.quickquiz.Constants;
import com.evanrjohnso.quickquiz.R;
import com.evanrjohnso.quickquiz.ui.SentencesActivity;
import com.evanrjohnso.quickquiz.ui.WordDefinition;

public class FirebaseWordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;
    public View imageViewForClickListenerForDragGestures;

    public FirebaseWordViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindWord(String word) {
        TextView text = (TextView) mView.findViewById(R.id.wordTextView);
        imageViewForClickListenerForDragGestures = (View) mView.findViewById(R.id.detailsOfWordImage);
        text.setText(word);
    }

    @Override
    public void onClick(View view) {
        TextView currentWord = (TextView) mView.findViewById(R.id.wordTextView);
        String chosenWord = currentWord.getText().toString();
//        Intent intent = new Intent(mContext, SentencesActivity.class);
        Intent intent = new Intent(mContext, WordDefinition.class);
        intent.putExtra(Constants.WORD_KEY, chosenWord);
        mContext.startActivity(intent);
    }
}
