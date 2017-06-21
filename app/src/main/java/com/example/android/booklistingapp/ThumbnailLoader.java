package com.example.android.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.android.booklistingapp.R.id.preview;

/**
 * Created by irina on 21.06.2017.
 */

public class ThumbnailLoader extends AsyncTaskLoader<ImageView> {

    private static final String LOG_TAG = ThumbnailLoader.class.getName();

    private String mUrl;
    private ImageView mImageView;

    public ThumbnailLoader(Context context, String url, ImageView imageView){
        super(context);
        mUrl = url;
        mImageView = imageView;
    }

    @Override
    protected void onStartLoading(){
        onForceLoad();
    }

    @Override
    public ImageView loadInBackground() {
        if(mUrl == null){
            return null;
        }

        try{
            URL url = new URL(mUrl);
            try {
                InputStream content = (InputStream) url.getContent();
                Drawable d = Drawable.createFromStream(content , "src");
                mImageView.setImageDrawable(d);
            }catch (IOException e){
                Log.e(LOG_TAG, "IO Exception");
            }
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Malformed URL Exception");
        }

        return mImageView;
    }
}
