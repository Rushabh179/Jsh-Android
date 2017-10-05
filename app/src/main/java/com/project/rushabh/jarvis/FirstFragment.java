package com.project.rushabh.jarvis;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

/**
 * Created by Rushabh on 05-Oct-17.
 */

public class FirstFragment extends Fragment {

    //private String title;
    private String page;
    String items[];
    TabLayout tl;

    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance(String page) {
        FirstFragment fragmentFirst = new FirstFragment();
        Bundle args = new Bundle();
        args.putString("someArray", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getString("someArray");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appliances, container, false);

        items = page.split("  ");
        GridView appGridView = (GridView) view.findViewById(R.id.appGridView);
        if (!items[0].isEmpty()) {
            ListAdapter myAdapter = new ApplianceCustomAdapter(getContext(), items);
            appGridView.setAdapter(myAdapter);
        }

        appGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    boolean isChanged = new ChangeStatus().execute(position).get();
                    if(isChanged){
                        Toast.makeText(getContext(),"Done",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        appGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                /*if(Objects.equals(Home.roleOfLogger, "0")){
                    //deleteAppliance();
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
                        }
                    });
                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }*/
                return true;
            }
        });

        return view;
    }
}