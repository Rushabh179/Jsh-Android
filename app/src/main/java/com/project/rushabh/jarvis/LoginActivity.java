package com.project.rushabh.jarvis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A login screen that offers login via id and password.
 */

public class LoginActivity extends AppCompatActivity {

    // UI references.
    private TextInputEditText mIdView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private static final String APP_SHARED_PREFS = "preferences";
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    boolean isLoggedIn;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("...........","create");
        super.onCreate(savedInstanceState);

        i=new Intent(LoginActivity.this,Home.class);

        checkLogIn();
        setContentView(R.layout.activity_login);


        // Set up the login form.
        mIdView = (TextInputEditText) findViewById(R.id.id);

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
            try {
                Log.i("aa............bb",new LoginAuthentication().execute(id, password).get());
                String[] rolenumber = new LoginAuthentication().execute(id, password).get().split("  ");
                Log.i("aa............aa",new LoginAuthentication().execute(id, password).get());

                if(rolenumber.length!=0){
                    startActivity(i);
                    editor = sharedPrefs.edit();
                    editor.putBoolean("loggedInState", true);
                    editor.putString("number", rolenumber[1]);
                    editor.putString("id", id);
                    editor.putString("password", password);
                    editor.putString("role", rolenumber[0]);
                    editor.apply();
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
    protected void onStart() {
        Log.i("...........","start");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i("...........","restart");
        checkLogIn();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i("...........","resume");
        checkLogIn();
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

    public void checkLogIn(){
        sharedPrefs = getApplicationContext().getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        isLoggedIn = sharedPrefs.getBoolean("loggedInState", false);
        if(isLoggedIn){
            startActivity(i);
        }
    }

}