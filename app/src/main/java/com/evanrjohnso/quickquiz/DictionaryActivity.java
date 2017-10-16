package com.evanrjohnso.quickquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class DictionaryActivity extends AppCompatActivity {
    private ListView mListView;
    private TextView mCategoryTextView;
    private String[] easyWords = new String[] {"this", "will", "be", "easier", "words"};
    private String[] mediumWords = new String[] {"once", "I", "decide", "the","setup", "of", "the", "app"};
    private String[] hardWords = new String[] {"cacophony", "tempest", "affectation", "protean", "abstruse", "jejune", "machinate", "ignominious", "expound"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        mListView = (ListView) findViewById(R.id.listView);
        mCategoryTextView = (TextView) findViewById(R.id.categoryTextView);
        String category = getIntent().getStringExtra("category");
        mCategoryTextView.setText(category + " words");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayProperDictionary(category));
        mListView.setAdapter(adapter);

    }

    public String[] displayProperDictionary(String category) {
        if (category.equals("easy")) {
            return easyWords;
        } else if(category.equals("medium")) {
            return mediumWords;
        } else {
            return hardWords;
        }
    }
}
