package com.evanrjohnso.quickquiz.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.evanrjohnso.quickquiz.Constants;
import com.evanrjohnso.quickquiz.R;
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
    @Bind(R.id.listView)
    ListView mListView;
    @Bind(R.id.categoryTextView)
    TextView mCategoryTextView;
    public static final String WORD_KEY = "word";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        ButterKnife.bind(this);

        String category = getIntent().getStringExtra(Constants.INTENT_CATEGORY);
        mCategoryTextView.setText(category + " words");
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference().child(category);

        ref.addListenerForSingleValueEvent(createValueEventListener());

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view;
                Intent intent = new Intent(DictionaryActivity.this, SentencesActivity.class);
                intent.putExtra(WORD_KEY, tv.getText().toString());
                startActivity(intent);
            }
        });
    }


    private ValueEventListener createValueEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> wordsArray = new ArrayList<>();
                Map<String, Object> data = (Map<String, Object>) dataSnapshot.getValue();
                for (String key: data.keySet()) {
                    wordsArray.add(key);
                }
                createArrayAdapater(wordsArray);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("words canceled", "load words failed");
            }
        };
    }

    private void createArrayAdapater(List<String> wordArray) {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, wordArray);
        mListView.setAdapter(adapter);
    }
}
