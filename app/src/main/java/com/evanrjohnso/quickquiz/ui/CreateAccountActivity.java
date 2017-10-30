package com.evanrjohnso.quickquiz.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.evanrjohnso.quickquiz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateAccountActivity extends AppCompatActivity {
    @OnClick(R.id.createUserButton)
    public void createUserButtonClicked(View v) {
        attemptCreateNewUser();
    }
    @Bind(R.id.nameEditText)
    EditText mNameEditText;
    @Bind(R.id.emailEditText)
    EditText mEmailEditText;
    @Bind(R.id.passwordEditText)
    EditText mPasswordEditText;
    @Bind(R.id.confirmPasswordEditText)
    EditText mConfirmPasswordEditText;
    @OnClick(R.id.loginTextView)
    public void logInHereWasClicked(View v) {
        Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener dbAuthAcctListener;
    private String mName;
    private ProgressDialog mAuthProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);

        firebaseAuth = firebaseAuth.getInstance();

        createAuthStateListener();
        createAuthProgressDialog();

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(dbAuthAcctListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (dbAuthAcctListener != null) {
            firebaseAuth.removeAuthStateListener(dbAuthAcctListener);
        }
    }

    private void createAuthStateListener() {
        dbAuthAcctListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }
    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Please hold, reaching out to google...");
        mAuthProgressDialog.setCancelable(false);
    }


    private boolean isValidEmail(String email) {
        return (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isValidName(String name) {
        return !name.equals("");
    }

    private boolean isValidPassword(String password) {
        return password.length() > 6;
    }

    private void attemptCreateNewUser() {
        String name = mNameEditText.getText().toString().trim();
        mName = name;
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();

        // Reset errors.
        mNameEditText.setError(null);
        mEmailEditText.setError(null);
        mPasswordEditText.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Check for valid name
        if (!isValidName(name)) {
            mNameEditText.setError(getString(R.string.error_invalid_nane));
            focusView = mNameEditText;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isValidPassword(password)) {
            mPasswordEditText.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordEditText;
            cancel = true;
        } else if (!password.equals(confirmPassword)) {
            mPasswordEditText.setError(getString(R.string.password_not_matching));
            focusView = mPasswordEditText;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailEditText.setError(getString(R.string.error_field_required));
            focusView = mEmailEditText;
            cancel = true;
        } else if (!isValidEmail(email)) {
            mEmailEditText.setError(getString(R.string.error_invalid_email));
            focusView = mEmailEditText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthProgressDialog.show();
            firebaseLogin(email, password);
        }
    }

    private void firebaseLogin(String email, String password) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mAuthProgressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateAccountActivity.this, "Authentication Successful!", Toast.LENGTH_SHORT).show();
                            createFirebaseUserProfile(task.getResult().getUser());
                        } else {
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
//                            Snackbar.make(mName, "Authentication failed", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                        }
                    }
                });
    }

    private void createFirebaseUserProfile(final FirebaseUser user) {


        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(mName)
                .build();

        user.updateProfile(addProfileName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("userNameFromFirebase", user.getDisplayName());
                        }
                    }
                });
    }

}
