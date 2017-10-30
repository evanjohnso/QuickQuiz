package com.evanrjohnso.quickquiz.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.evanrjohnso.quickquiz.R;
import com.evanrjohnso.quickquiz.services.OxfordService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @OnClick(R.id.implicit_intent)
    public void clicked(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_path);
        ButterKnife.bind(this);
        String userId = FirebaseAuth.getInstance()
                .getCurrentUser().getUid();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String phone = mSharedPreferences.getString(MainActivity.PHONE_KEY, null);
        if (phone != null) {
            Toast.makeText(this, "Thanks for your number from shared preferences " + phone, Toast.LENGTH_SHORT).show();
        }

        mEasyWords.setOnClickListener(this);
        mMediumWords.setOnClickListener(this);
        mHardWords.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(SelectPathActivity.this, DictionaryActivity.class);
        if (view == mEasyWords) {
            intent.putExtra("category", "easy");
        } else if (view == mMediumWords) {
            intent.putExtra("category", "medium");
        } else if (view == mHardWords) {
            intent.putExtra("category", "hard");
        }
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(SelectPathActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
