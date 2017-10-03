package com.project.rushabh.jarvis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Rushabh on 03-Oct-17.
 */

public class ApplianceCustomAdapter extends ArrayAdapter<String> {

    ApplianceCustomAdapter(@NonNull Context context, String[] names) {
        super(context, R.layout.appliances_custom_grid, names);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder")
        View customView = inflater.inflate(R.layout.appliances_custom_grid,parent,false);

        String getSingleItem = getItem(position);
        TextView appTvName = (TextView) customView.findViewById(R.id.appTvName);
        ImageView appImageView = (ImageView) customView.findViewById(R.id.appImageView);

        appTvName.setText(getSingleItem);
        appImageView.setImageResource(R.drawable.ic_lightbulb);

        return customView;
    }
}
