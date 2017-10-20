package com.evanrjohnso.quickquiz.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.evanrjohnso.quickquiz.R;
import com.evanrjohnso.quickquiz.services.OxfordService;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SelectPathActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.easy_word)
    TextView mEasyWords;
    @Bind(R.id.medium_words)
    TextView mMediumWords;
    @Bind(R.id.hard_words)
    TextView mHardWords;
    private OxfordService oxfordService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_path);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        Toast.makeText(this, "Thanks for your number " + phone, Toast.LENGTH_SHORT).show();

        Resources res = getResources();
        String textToFill = res.getString(R.string.words_completed);

        mEasyWords.setText( String.format(textToFill, "Easy ", 10, 10));
        mMediumWords.setText( String.format(textToFill, "Medium", 4, 10));
        mHardWords.setText( String.format(textToFill, "Hard", 1, 10));

        mEasyWords.setOnClickListener(this);
        mMediumWords.setOnClickListener(this);
        mHardWords.setOnClickListener(this);
        oxfordService = new OxfordService();
        oxfordService.grabSentence("imbroglio", sentenceCallback());
    }

    @Override
    public void onClick(View view) {
        Intent intent =  new Intent(SelectPathActivity.this, DictionaryActivity.class);
        if (view == mEasyWords) {
            intent.putExtra("category", "easy");
            startActivity(intent);
        } else if (view == mMediumWords) {
            intent.putExtra("category", "medium");
            startActivity( intent );
        } else if (view == mHardWords) {
            intent.putExtra("category", "hard");
            startActivity( intent );
        }
    }


    private Callback sentenceCallback() {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                oxfordService.processAsyncResponse(response);
            }
        };
    }


}
