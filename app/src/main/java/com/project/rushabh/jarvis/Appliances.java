package com.project.rushabh.jarvis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

import static com.project.rushabh.jarvis.Users.applyDim;
import static com.project.rushabh.jarvis.Users.clearDim;


public class Appliances extends AppCompatActivity {

    PopupWindow pw;
    Spinner aaSpinner;
    EditText aaEtName;
    String room_selected_spinner;
    static int room_id;
    static String room_name;

    ViewPager vpPager;
    @SuppressLint("StaticFieldLeak")
    static TabLayout tabLayout;

    FragmentPagerAdapter adapterViewPager;
    private int selectedtab;
    private static final String APP_SHARED_PREFS = "preferences";
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vpPager = (ViewPager) findViewById(R.id.container);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        //TODO: set sharedpreferences relating to room_id
        sharedPrefs = getApplicationContext().getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        selectedtab=sharedPrefs.getInt("selectedtab",0);
        vpPager.setCurrentItem(selectedtab);


        room_id=getIntent().getIntExtra("room_id",0);
        room_name=getIntent().getStringExtra("room_name");
        vpPager.setCurrentItem(room_id);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vpPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(Objects.equals(Home.roleOfLogger, "1")){
            fab.setVisibility(View.GONE);
        }
        fab.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                Snackbar.make(view, "Add Appliance", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAppliance(view);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                room_name=tab.getText().toString();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void addAppliance(View view) {
        try {
            final ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();
            LayoutInflater inflater = (LayoutInflater) Appliances.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.appliance_add_popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            pw = new PopupWindow(layout, 1000, 750, true);
            pw.showAtLocation(view, Gravity.CENTER, 0, 0);
            applyDim(root, (float) 0.5);

            pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    clearDim(root);
                }
            });

            aaSpinner = (Spinner) layout.findViewById(R.id.aaSpinner);
            ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Home.room_names_list);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            aaSpinner.setAdapter(aa);
            room_selected_spinner = Home.room_names_list[tabLayout.getSelectedTabPosition()];
            aaSpinner.setSelection(tabLayout.getSelectedTabPosition());
            aaEtName = (EditText) layout.findViewById(R.id.aaEtName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSaveAa(View view) {
        String app_name = aaEtName.getText().toString();
        try {
            Boolean a=new ApplianceAdd().execute(room_selected_spinner,app_name).get();
            if(a){
                Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pw.dismiss();
        //overridePendingTransition(0, 0);
        editor = sharedPrefs.edit();
        selectedtab=tabLayout.getSelectedTabPosition(); // or something else depending on your tab widget
        editor.putInt("selectedtab",selectedtab).apply();
        editor.putBoolean("isrefresh",true);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void onCancelAa(View view) {
        pw.dismiss();
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return Home.room_names_list.length;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            String items = null;
            try {
                items=new ApplianceList().execute(Home.room_names_list[position]).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
                return FirstFragment.newInstance(items);

        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            try {
                return Home.room_names_list[position];
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
}