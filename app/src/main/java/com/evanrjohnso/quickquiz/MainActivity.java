package com.evanrjohnso.quickquiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.start_button)
    Button mStartLearningButton;
    @Bind(R.id.main_app_title) TextView mMainAppTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Typeface raleway = Typeface.createFromAsset(getAssets(), "fonts/raleway-black.tff");
        mMainAppTitle.setTypeface(raleway);
        mStartLearningButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, SelectPathActivity.class);
        startActivity(intent);

    }
}
