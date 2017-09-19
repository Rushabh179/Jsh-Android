package com.project.rushabh.jarvis;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A login screen that offers login via id and password.
 */

public class LoginActivity extends AppCompatActivity {


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    private LoginAuthentication mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mIdView;
    private EditText mPasswordView;
    //private View mProgressView;
    private View mLoginFormView;
    private static final String APP_SHARED_PREFS = "preferences";
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    boolean isUserLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPrefs = getApplicationContext().getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        isUserLoggedIn = sharedPrefs.getBoolean("userLoggedInState", false);
        if(isUserLoggedIn){
            startActivity(new Intent(this,AdminHome.class));
        }
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mIdView = (AutoCompleteTextView) findViewById(R.id.id);

        //Login on pressing enter
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        //Clicking on sign in button
        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        //mLoginFormView = findViewById(R.id.login_form);
        //mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid id, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mIdView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String id = mIdView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid id.
        if (TextUtils.isEmpty(id)) {
            mIdView.setError(getString(R.string.error_field_required));
            focusView = mIdView;
            cancel = true;
        } else if (!isIdValid(id)) {
            mIdView.setError(getString(R.string.error_invalid_id));
            focusView = mIdView;
            cancel = true;
        }

        // Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to perform the user login attempt.
            //showProgress(true);////
            try {
                String[] rolenumber = new LoginAuthentication().execute(id, password).get().split(" ");
                //String[] rolenumber=code.split(" ");
                //Log.d("..............", code);
                if (Objects.equals(rolenumber[0], "0")){
                    startActivity(new Intent(LoginActivity.this,AdminHome.class));
                    editor = sharedPrefs.edit();
                    editor.putBoolean("userLoggedInState", true);
                    editor.apply();
                }
                else if (Objects.equals(rolenumber[0], "1")){
                    startActivity(new Intent(LoginActivity.this,LoginActivity.class));
                }
                else{
                    Toast.makeText(this, R.string.incorrect_message,Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isIdValid(String id) {
        return id.length() > 2;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void onBackPressed() {
        Log.i("...........","backpressed");
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        Log.i("...........","restart");
        sharedPrefs = getApplicationContext().getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        isUserLoggedIn = sharedPrefs.getBoolean("userLoggedInState", false);
        if(isUserLoggedIn){
            startActivity(new Intent(this,AdminHome.class));
        }
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i("...........","resume");
        sharedPrefs = getApplicationContext().getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        isUserLoggedIn = sharedPrefs.getBoolean("userLoggedInState", false);
        if(isUserLoggedIn){
            startActivity(new Intent(this,AdminHome.class));
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("...........","pause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i("...........","destroy");
        super.onDestroy();
    }
}