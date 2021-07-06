package com.example.mynews;

import android.graphics.Bitmap;

public class Story {

    //The Story object is going to contain
    //The URL of the article
    //THe thumbnail of the article
    //The title of the article
    //The author of the article if it exists

    private String mURL;
    private Bitmap mThumbnail;
    private String mTitle;
    private String mAuthor;
    private String mDate;

    public Story(String url, Bitmap thumb, String title, String date){
        mURL = url;
        mThumbnail = thumb;
        mTitle = title;
        mDate = date;
    }

    //Returns the url of the given Story
    public String getmURL() {
        return mURL;
    }

    //Returns the thumbnail of the given Story
    public Bitmap getmThumbnail() {
        return mThumbnail;
    }

    //Returns the title of the given Story
    public String getmTitle() {
        return mTitle;
    }

    //Returns the date of the given Story
    public String getmDate(){return mDate;}
}
