package com.example.mynews;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataArrayAdapter extends ArrayAdapter<DataModel> {

    public DataArrayAdapter(Activity context, ArrayList<DataModel> list) {
        super(context,0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //First we need to check if the existing view is being used
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_dat, parent, false);
        }
        //We need to get the current object in the adapter position
        DataModel currentData = getItem(position);

        //Find the textView that displays the title in the list_item layout
        TextView title = (TextView) listItemView.findViewById(R.id.d);
        //Set the title String to the textView
        title.setText(currentData.getTitle());

        //Find the textView tht displays the date in the list_item layout
        TextView url = (TextView) listItemView.findViewById(R.id.date);
        //Set the date String to the textView
        url.setText(currentData.getUrl());

        return listItemView;
    }

}
