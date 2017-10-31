package com.evanrjohnso.quickquiz.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.evanrjohnso.quickquiz.Constants;
import com.evanrjohnso.quickquiz.R;
import com.evanrjohnso.quickquiz.adapters.WordListAdapter;
import com.evanrjohnso.quickquiz.models.Sentence;
import com.evanrjohnso.quickquiz.services.OxfordService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SentencesActivity extends AppCompatActivity {
    public static OxfordService oxfordService;
    public static ArrayList<Sentence> sentencesList;
    private WordListAdapter mAdapter;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentences);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String word = intent.getStringExtra(Constants.WORD_KEY);
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
                sentencesList = oxfordService.processAsyncResponse(response);
                SentencesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new WordListAdapter(getApplicationContext(), sentencesList);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SentencesActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(false);
                    }
                });
            }
        };
    }
}
