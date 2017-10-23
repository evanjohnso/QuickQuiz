package com.evanrjohnso.quickquiz.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evanrjohnso.quickquiz.R;
import com.evanrjohnso.quickquiz.models.Sentence;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.SentenceViewHolder> {
    private ArrayList<Sentence> mSentences = new ArrayList<>();
    private Context mContext;

    public WordListAdapter(Context context, ArrayList<Sentence> sentences) {
        mContext = context;
        mSentences = sentences;
    }

    @Override
    public WordListAdapter.SentenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //onCreateViewHolder inflates XML layout and returns ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_sentences_layout, parent, false);
        SentenceViewHolder viewHolder = new SentenceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WordListAdapter.SentenceViewHolder childViewHolder, int position) {
        //sets the information in the sentencesList item view
        //the data from one sentence gets passed down to the viewHolder
        //binding the data together
        childViewHolder.bindSentenceDataToView(mSentences.get(position));
    }

    @Override
    public int getItemCount() {
        return mSentences.size();
    }









    //A ViewHolder is required to run Recycler Views and stores multiple Views
    //this way the views don't need to continuously be searched, increasing
    //performance
    public class SentenceViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        @Bind(R.id.wordTextView)
        TextView mWordTextView;
        @Bind(R.id.sentenceTextView)
        TextView mSentenceTextView;


        public SentenceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }
        public void bindSentenceDataToView(Sentence sentence) {
            mWordTextView.setText(sentence.getWord());
            mSentenceTextView.setText(sentence.getInSentence());
        }
    }
}
