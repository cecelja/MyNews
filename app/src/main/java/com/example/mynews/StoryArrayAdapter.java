package com.example.mynews;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.mynews.Utils.LOG_TAG;

public class StoryArrayAdapter extends ArrayAdapter<Story> {

    public StoryArrayAdapter(Activity context, ArrayList<Story> list) {
        super(context,0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //First we need to check if the existing view is being used
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        //We need to get the current object in the adapter position
        Story currentStory = getItem(position);

        //Find the imageView in the list_item layout
        ImageView thumbnail = (ImageView) listItemView.findViewById(R.id.thumbnail);
        //Set the thumbnail resource to the imageView
        thumbnail.setImageBitmap(currentStory.getmThumbnail());

        //Find the textView that displays the title in the list_item layout
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        //Set the title String to the textView
        title.setText(currentStory.getmTitle());

        //Find the textView tht displays the date in the list_item layout
        TextView date = (TextView) listItemView.findViewById(R.id.date);
        //Set the date String to the textView
        date.setText(currentStory.getmDate());

        return listItemView;
    }

    /**
     * With this method we format the date from the API such as 2021-06-30T23:55:33Z
     * Into 2021-06-30
     * */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static String formatDate(String date){
        String dateString = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        try{
        Date dateObject = dateFormat.parse(date);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        dateString = formatter.format(dateObject);

        } catch (ParseException e){
            e.printStackTrace();
        }

        return dateString;
    }
}
