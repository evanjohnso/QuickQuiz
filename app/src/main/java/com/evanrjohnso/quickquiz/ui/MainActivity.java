package com.evanrjohnso.quickquiz.ui;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.evanrjohnso.quickquiz.R;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_PERMISSION_WRITE = 2345;
    public static final String PHONE_KEY = "phone";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor preferencesEditor;
    @Bind(R.id.main_app_title)
    TextView mMainAppTitle;
    @Bind(R.id.phone_number)
    EditText mUserPhoneNumber;

    @OnClick(R.id.start_button)
    public void onClick(View view) {
        String phone = mUserPhoneNumber.getText().toString();
        if (!isPhoneNumberValid(phone)) {
            mUserPhoneNumber.setError("Please enter a valid number!");
            return;
        }
        addToSharedPreferences(phone);
        Intent intent = new Intent(MainActivity.this, SelectPathActivity.class);
//        intent.putExtra(PHONE_KEY, phone);
        startActivity(intent);
    }
    private boolean permissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mUserPhoneNumber.setText("1234567890");

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        preferencesEditor = mSharedPreferences.edit();

        Typeface raleway = Typeface.createFromAsset(getAssets(), "fonts/raleway-black.tff");
        mMainAppTitle.setTypeface(raleway);

        if (!permissionGranted) {
            checkPermissions();
            return;
        }

    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return patternMatcher(phoneNumber);
    }
    private boolean patternMatcher(String phoneNumber) {
        String pattern = "\\d{10}|"+
                "(?:\\d{3}-){2}\\d{4}|" +     // 123-456-7890
                "\\(\\d{3}\\)\\d{3}-?\\d{4}"; // (123)456-7890
        Pattern pat = Pattern.compile(pattern);
        return pat.matcher(phoneNumber).matches();
    }

    // Checks if external storage is available for read and write
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    // Initiate request for permissions.
    private boolean checkPermissions() {

        if (!isExternalStorageWritable()) {
            Toast.makeText(this, "This app only works on devices with usable external storage",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //check to see if previously granted
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE);
            return false;
        } else {
            return true;
        }
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Toast.makeText(this, "External storage permission granted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You must grant permission!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void addToSharedPreferences(String phone) {
        preferencesEditor.putString(PHONE_KEY, phone).apply();
    }
}
