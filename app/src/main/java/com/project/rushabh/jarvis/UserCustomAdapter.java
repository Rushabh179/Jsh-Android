package com.project.rushabh.jarvis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by Rushabh on 20-Sep-17.
 */

public class UserCustomAdapter extends ArrayAdapter<String> {

    UserCustomAdapter(@NonNull Context context, String[] names) {
        super(context,R.layout.user_custom_row ,names);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder")
        View customView=inflater.inflate(R.layout.user_custom_row,parent,false);
        return customView;
    }

}
