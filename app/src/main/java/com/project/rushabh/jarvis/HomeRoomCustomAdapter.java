package com.project.rushabh.jarvis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Rushabh on 01-Oct-17.
 */

public class HomeRoomCustomAdapter extends ArrayAdapter<String> {

    HomeRoomCustomAdapter(@NonNull Context context, String[] names) {
        super(context,R.layout.home_room_custom_row ,names);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder")
        View customView=inflater.inflate(R.layout.home_room_custom_row,parent,false);

        String getSingleItem=getItem(position);
        TextView hrTvName=(TextView) customView.findViewById(R.id.hrTvName);

        hrTvName.setText(getSingleItem);

        return customView;
    }
}