package com.evanrjohnso.quickquiz.adapters;


import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import java.util.ArrayList;

//public class FirebaseWordRecylcerAdapter extends FirebaseRecyclerAdapter<String, FirebaseWordRecylcerAdapter.ViewHolder> {
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView mView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            mView = (TextView) itemView;
//        }
//    }
//
//    public FirebaseWordRecylcerAdapter(Query query, @Nullable ArrayList<String> words,
//                                       @Nullable ArrayList<String> keys) {
//        super(query, words, keys);
//    }
//
//
//    @Override
//    protected void populateViewHolder(String viewHolder, FirebaseWordRecylcerAdapter.ViewHolder model, int position) {
//
//    }
//}
