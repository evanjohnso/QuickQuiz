package com.evanrjohnso.quickquiz.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.evanrjohnso.quickquiz.Constants;
import com.evanrjohnso.quickquiz.R;
import com.evanrjohnso.quickquiz.ui.DictionaryActivity;
import com.evanrjohnso.quickquiz.ui.SentencesActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FirebaseWordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

    public FirebaseWordViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindWord(String word) {
        TextView text = (TextView) mView.findViewById(R.id.wordTextView);
        text.setText(word);
    }

    @Override
    public void onClick(View view) {
        TextView currentWord = (TextView) mView.findViewById(R.id.wordTextView);
        String chosenWord = currentWord.getText().toString();
        Toast.makeText(mContext, chosenWord, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, SentencesActivity.class);
        intent.putExtra(Constants.WORD_KEY, chosenWord);
        mContext.startActivity(intent);
    }
}
