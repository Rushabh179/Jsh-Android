package com.project.rushabh.jarvis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

import static com.project.rushabh.jarvis.Users.applyDim;
import static com.project.rushabh.jarvis.Users.clearDim;


public class Appliances extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private static SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @SuppressLint("StaticFieldLeak")
    static ViewPager mViewPager;

    static int room_id;
    static String room_name;
    static String[] items;
    @SuppressLint("StaticFieldLeak")
    static TabLayout tabLayout;

    @SuppressLint("StaticFieldLeak")
    static GridView appGridView;

    PopupWindow pw;
    Spinner aaSpinner;
    EditText aaEtName;
    String room_selected_spinner;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        room_id=getIntent().getIntExtra("room_id",0);//To start from a particular tab
        room_name=getIntent().getStringExtra("room_name");
        mViewPager.setCurrentItem(room_id);
        try {
            items = new ApplianceList().execute(room_name).get().split("  ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

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

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void addAppliance(View v) {
        try {
            final ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();
            LayoutInflater inflater = (LayoutInflater) Appliances.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.appliance_add_popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            pw = new PopupWindow(layout, 1000, 750, true);
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);
            applyDim(root, (float) 0.5);

            pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    clearDim(root);
                }
            });

            aaSpinner = (Spinner) layout.findViewById(R.id.aaSpinner);
            aaSpinner.setOnItemSelectedListener(this);
            ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Home.room_names_list);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            aaSpinner.setAdapter(aa);
            aaSpinner.setSelection(tabLayout.getSelectedTabPosition());
            aaEtName = (EditText) layout.findViewById(R.id.aaEtName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext() ,Home.room_names_list[position] ,Toast.LENGTH_LONG).show();
        room_selected_spinner = Home.room_names_list[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        //startActivity(getIntent());
        mSectionsPagerAdapter.notifyDataSetChanged();
        //mViewPager.setCurrentItem(room_id);
        overridePendingTransition(0, 0);
    }

    public void onCancelAa(View view) {
        pw.dismiss();
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_appliances, container, false);

            /*TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));*/

            appGridView = (GridView) rootView.findViewById(R.id.appGridView);
            ListAdapter myAdapter=new ApplianceCustomAdapter(getContext(),items);
            appGridView.setAdapter(myAdapter);

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    room_name = tab.getText().toString();
                    room_id = tab.getPosition();
                    try {
                        items = new ApplianceList().execute(room_name).get().split("  ");
                        GridView appGridView = (GridView) rootView.findViewById(R.id.appGridView);
                        ListAdapter myAdapter=new ApplianceCustomAdapter(getContext(),items);
                        appGridView.setAdapter(myAdapter);
                        //Toast.makeText(Appliances.this,items,Toast.LENGTH_SHORT).show();//TODO
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            appGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

            appGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    //deleteAppliance();
                    Toast.makeText(getContext(),"room_id"+Integer.valueOf(room_id).toString(),Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("delete?");
                    dialog.setMessage("Are you sure want to delete this appliance from "+room_name+"?");
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                boolean isDeleted = new ApplianceDelete().execute(room_name,items[position]).get();
                                if(isDeleted){
                                    Toast.makeText(getContext(),"Deleted",Toast.LENGTH_SHORT).show();
                                }//
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                            mSectionsPagerAdapter.notifyDataSetChanged();
                        }
                    });
                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                    return true;
                }
            });
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show total pages.
            return Home.room_names_list.length;
        }

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