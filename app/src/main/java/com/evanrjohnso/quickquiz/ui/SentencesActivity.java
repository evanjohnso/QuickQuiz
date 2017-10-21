package com.evanrjohnso.quickquiz.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.evanrjohnso.quickquiz.R;
import com.evanrjohnso.quickquiz.models.Sentence;
import com.evanrjohnso.quickquiz.services.OxfordService;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SentencesActivity extends AppCompatActivity {
    public static OxfordService oxfordService;
    public static ArrayList<Sentence> list;
    @Bind(R.id.display_sentences_list_view)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentences);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String word = intent.getStringExtra(DictionaryActivity.WORD_KEY);
        Toast.makeText(this, word, Toast.LENGTH_SHORT).show();
        oxfordService = new OxfordService();
        oxfordService.grabSentence(word, sentenceCallback());
    }

    private Callback sentenceCallback() {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                list = oxfordService.processAsyncResponse(response);
                SentencesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] array = new String[list.size()];
                        for (int i = 0; i < array.length; i++) {
                            array[i] = list.get(i).getInSentence();
                        }
                        ArrayAdapter adapter = new ArrayAdapter(SentencesActivity.this,
                                android.R.layout.simple_list_item_1, array);
                        mListView.setAdapter(adapter);
                    }
                });

            }
        };
    }
}
