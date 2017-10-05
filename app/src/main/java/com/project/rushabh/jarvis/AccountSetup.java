package com.project.rushabh.jarvis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.widget.EditText;

public class AccountSetup extends AppCompatActivity {

    private static final String APP_SHARED_PREFS = "preferences";
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    EditText asEtName,asEtId,asEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        asEtName = (EditText) findViewById(R.id.asEtName);
        asEtId = (EditText) findViewById(R.id.asEtId);
        asEtPassword = (EditText) findViewById(R.id.asEtPassword);

        sharedPrefs = getApplicationContext().getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);

        asEtId.setText(sharedPrefs.getString("id","Id"));
        asEtPassword.setText(sharedPrefs.getString("password","........................."));

        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}