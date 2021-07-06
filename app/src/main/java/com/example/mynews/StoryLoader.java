package com.example.mynews;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

import static com.example.mynews.Utils.LOG_TAG;

public class StoryLoader extends AsyncTaskLoader<ArrayList<Story>> {

    private String urlString = null;

    public StoryLoader(Context cont, String url){
        super(cont);
        urlString = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public ArrayList<Story> loadInBackground() {

        //Do not perform a network request if there are no urls
        if(urlString == null){
            return null;
        }

        //Perform a network request to retrieve the data
        ArrayList<Story> stories = Utils.fetchStoryData(urlString);



        //If there is no retreived data then return null
        if(stories == null){
            return null;
        }

        return stories;
    }
}
