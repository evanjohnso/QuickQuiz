package com.evanrjohnso.quickquiz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.evanrjohnso.quickquiz.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    }

    @Override
    public void onClick(View view) {
        final ArrayList<String> words = new ArrayList<>();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child()
    }


}
