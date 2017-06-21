package com.example.android.booklistingapp;

import org.json.JSONArray;

/**
 * Created by irina on 21.06.2017.
 */

public class Book {
    private String mTitle;
    private String mSubtitle;
    private String mAuthors;
    private String mUrl;
    private String mImageUrl;

    public Book(String title, String subtitle, String authors, String url, String imageUrl){
        mTitle = title;
        mSubtitle = subtitle;
        mAuthors = authors;
        mUrl = url;
        mImageUrl = imageUrl;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getSubtitle(){
        return mSubtitle;
    }

    public String getAuthors(){
        return mAuthors;
    }

    public String getUrl(){
        return mUrl;
    }

    public String getImageUrl(){
        return mImageUrl;
    }
}
