package com.evanrjohnso.quickquiz.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.evanrjohnso.quickquiz.Constants;
import com.evanrjohnso.quickquiz.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPathActivity extends AppCompatActivity {
    @OnClick(R.id.lsat_words)
    public void clicked() {
        categoryClicked(Constants.FIREBASE_LSAT);
    }
    @OnClick(R.id.sat_words)
    public void clicked1() {
        categoryClicked(Constants.FIREBASE_SAT);
    }
    @OnClick(R.id.gre_words)
    public void clicked2() {
        categoryClicked(Constants.FIREBASE_GRE);
    }

    @OnClick(R.id.implicit_intent)
    public void clicked(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "This phone does not seem to have a browser!", Toast.LENGTH_SHORT).show();
        }
    }
//    @OnClick(R.id.populate_database)
//    public void populateDB(View v) {
//        Map<String, Object> wordMapForFirebase = readFile("words/GRE.txt");
//        //change the file name && Constants.FIREBASE_... depending on the data to be added
//        DatabaseReference categoryFireRef = FirebaseDatabase.getInstance()
//                .getReference().child(Constants.FIREBASE_GRE);
//        DatabaseReference root = FirebaseDatabase.getInstance()
//                .getReference().child(Constants.WORDS);
//        categoryFireRef.updateChildren(wordMapForFirebase);
//        root.updateChildren(wordMapForFirebase);
//    }
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_path);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };
    }

    private void categoryClicked(String category) {
        Intent intent = new Intent(SelectPathActivity.this, DictionaryActivity.class);
        Log.v("categoryClicked", category);
        intent.putExtra(Constants.INTENT_CATEGORY, category);
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

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private Map<String, Object> readFile(String filePath) {
        BufferedReader reader = null;
        Map<String, Object> map = new HashMap<>();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(filePath)));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                map.put(mLine, mLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader  != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
}
