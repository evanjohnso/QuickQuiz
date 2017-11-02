package com.evanrjohnso.quickquiz.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.evanrjohnso.quickquiz.Constants;
import com.evanrjohnso.quickquiz.R;
import com.evanrjohnso.quickquiz.adapters.FirebaseWordViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DictionaryActivity extends AppCompatActivity {
    private DatabaseReference firebaseRef;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_recycler);
        ButterKnife.bind(this);
        String category = getIntent().getStringExtra(Constants.INTENT_CATEGORY);
        firebaseRef = FirebaseDatabase.getInstance()
                .getReference().child(category);

        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<String, FirebaseWordViewHolder>
                (String.class,
                        R.layout.activity_dictionary,
                        FirebaseWordViewHolder.class,
                        firebaseRef) {

            @Override
            protected void populateViewHolder(FirebaseWordViewHolder viewHolder,
                                              String wordModel, int position) {
                viewHolder.bindWord(wordModel);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
