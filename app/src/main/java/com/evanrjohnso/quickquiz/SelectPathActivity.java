package com.evanrjohnso.quickquiz;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectPathActivity extends AppCompatActivity {
    @Bind(R.id.easy_word)
    TextView mEasyWords;
    @Bind(R.id.medium_words)
    TextView mMediumWords;
    @Bind(R.id.hard_words)
    TextView mHardWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_path);

        ButterKnife.bind(this);

        Resources res = getResources();
        String easyCompleted = String.format(res.getString(R.string.words_completed), "Easy ", 10, 10);
        String mediumCompleted = String.format(res.getString(R.string.words_completed), "Medium", 4, 10);
        String hardCompleted = String.format(res.getString(R.string.words_completed), "Hard", 1, 10);

        mEasyWords.setText(easyCompleted);
        mMediumWords.setText(mediumCompleted);
        mHardWords.setText(hardCompleted);
    }
}
