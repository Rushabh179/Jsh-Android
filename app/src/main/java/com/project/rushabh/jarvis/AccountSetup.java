package com.project.rushabh.jarvis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class AccountSetup extends AppCompatActivity {

    private static final String APP_SHARED_PREFS = "preferences";
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    EditText asEtName,asEtId,asEtOldPassword,asEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        asEtName = (EditText) findViewById(R.id.asEtName);
        asEtId = (EditText) findViewById(R.id.asEtId);
        asEtOldPassword = (EditText) findViewById(R.id.asEtOldPassword);
        asEtPassword = (EditText) findViewById(R.id.asEtPassword);

        sharedPrefs = getApplicationContext().getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);

        asEtId.setText(sharedPrefs.getString("id","Id"));
        //asEtPassword.setText(sharedPrefs.getString("password","........................."));

        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void asSaveBuClicked(View view) {
        if(asEtOldPassword.getText().toString().length()<4){
            Toast.makeText(this,"Password too short",Toast.LENGTH_SHORT).show();
        }
        else if(!Objects.equals(asEtOldPassword.getText().toString(), sharedPrefs.getString("password", ""))){
            Toast.makeText(this,"Wrong Password",Toast.LENGTH_SHORT).show();
        }
        else if(!asEtPassword.getText().toString().isEmpty()){
            if(asEtPassword.getText().toString().length()<4){
                Toast.makeText(this,"Password too short",Toast.LENGTH_SHORT).show();
            }
            else {
                try {
                    Boolean a=new ChangeAccountInfo().execute(asEtOldPassword.getText().toString(),asEtPassword.getText().toString()).get();
                    if(a){
                        Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void asCancelBuClicked(View view) {
        finish();
    }
}