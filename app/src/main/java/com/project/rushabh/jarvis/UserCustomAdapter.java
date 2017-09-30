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

        String getSingleItem=getItem(position);
        TextView userTvId=(TextView) customView.findViewById(R.id.userTvId);

        userTvId.setText(getSingleItem);

        return customView;
    }

}
