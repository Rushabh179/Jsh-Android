package com.project.rushabh.jarvis;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.Objects;

import static com.project.rushabh.jarvis.Users.applyDim;
import static com.project.rushabh.jarvis.Users.clearDim;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String APP_SHARED_PREFS = "preferences";
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    boolean isLoggedIn;
    String roleOfLogger;

    ListView hrListView;
    static String[] names;
    String item;

    PopupWindow pw;
    EditText arEtName;

    Intent i;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.i("...........2","create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        i=new Intent(this,LoginActivity.class);
        sharedPrefs = getApplicationContext().getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        roleOfLogger = sharedPrefs.getString("role", "");

        try {
            names = new HomeRoomList().execute().get().split("  ");
            //names = null;
            hrListView = (ListView) findViewById(R.id.hrListView);
            ListAdapter hrAdapter = new HomeRoomCustomAdapter(this, names);
            hrListView.setAdapter(hrAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        hrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item=String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(Home.this,item,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this,Appliances.class).putExtra("room_id",position).putExtra("room_name",item));
            }
        });

        hrListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (Objects.equals(roleOfLogger, "0")){
                    item=String.valueOf(parent.getItemAtPosition(position));
                    deleteDialog(item);
                }
                return true;
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(Objects.equals(roleOfLogger, "1")){
            fab.setVisibility(View.GONE);
        }
        fab.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                Snackbar.make(view, "Add Room", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRoom(v);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_opt1) {//Account setup
            startActivity(new Intent(this,AccountSetup.class));
            finish();
        }
        else if (id == R.id.nav_opt2) {//Users
            startActivity(new Intent(this,Users.class));
            finish();
        }
        else if (id == R.id.nav_opt3) {//FAQs

        } else if (id == R.id.nav_opt4) {//About

        } else if (id == R.id.nav_opt5) {//Logout
            editor = sharedPrefs.edit();
            //editor.putBoolean("loggedInState", false);
            editor.clear();
            editor.apply();
            startActivity(i);

        } /*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void deleteDialog(final String room_name) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Delete?");
        dialog.setMessage("Are you sure want to delete this room?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    boolean isDeleted = new HomeRoomDelete().execute(room_name).get();
                    if(isDeleted){
                        Toast.makeText(Home.this,"Deleted",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                //overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void addRoom(View v) {
        try {
            final ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();
            LayoutInflater inflater = (LayoutInflater) Home.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.home_room_add_popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            pw = new PopupWindow(layout, 1000, 650, true);
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);
            applyDim(root, (float) 0.5);

            pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    clearDim(root);
                }
            });

            arEtName = (EditText) layout.findViewById(R.id.arEtName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void checkLogin(){
        sharedPrefs = getApplicationContext().getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        isLoggedIn = sharedPrefs.getBoolean("loggedInState", false);
        if(!isLoggedIn){
            startActivity(i);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void onCancelRa(View view) {
        pw.dismiss();
    }

    public void onSaveRa(View view) {
        String name = arEtName.getText().toString();
        try {
            Boolean a=new HomeRoomAdd().execute(name).get();
            if(a){
                Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pw.dismiss();
        //overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        Log.i("...........2","backpressed");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        Log.i("...........2","start");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i("...........2","restart");
        checkLogin();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i("...........2","resume");
        checkLogin();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("...........2","pause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i("...........2","destroy");
        super.onDestroy();
    }
}