package com.evanrjohnso.quickquiz.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.evanrjohnso.quickquiz.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DictionaryActivity extends AppCompatActivity {
    @Bind(R.id.listView)
    ListView mListView;
    @Bind(R.id.categoryTextView)
    TextView mCategoryTextView;
    public static final String WORD_KEY = "word";
    private String[] easyWords = new String[]{"practice", "harrow", "hollow", "definition", "computer"};
    private String[] mediumWords = new String[]{"symbiotic", "disturbance", "decide", "affection", "setup", "earth"};
    private String[] hardWords = new String[] {"cacophony", "tempest", "affectation", "protean", "abstruse", "jejune", "machinate", "ignominious", "expound"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        ButterKnife.bind(this);

        String category = getIntent().getStringExtra("category");
        mCategoryTextView.setText(category + " words");
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.display_sentences_layout, displayProperDictionary(category));
        mListView.setAdapter(adapter);

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
