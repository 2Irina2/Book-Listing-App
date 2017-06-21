package com.example.android.booklistingapp;

import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;

import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by irina on 21.06.2017.
 */

public class BookAdapter extends ArrayAdapter<Book> implements LoaderCallbacks<ImageView> {

    private static final String LOG_TAG = BookAdapter.class.getSimpleName();

    private String thumbnailUrl;
    private ImageView preview;

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);

        preview = (ImageView) listItemView.findViewById(R.id.preview);
        thumbnailUrl = currentBook.getImageUrl();

        Log.e(LOG_TAG, "IMAGE URL SET = " + thumbnailUrl);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentBook.getTitle());

        TextView subtitle = (TextView) listItemView.findViewById(R.id.subtitle);
        subtitle.setText(currentBook.getSubtitle());

        TextView authors = (TextView) listItemView.findViewById(R.id.authors);
        authors.setText(currentBook.getAuthors());

        return listItemView;

    }

    private static final int EARTHQUAKE_LOADER_ID = 1;

    @Override
    public Loader<ImageView> onCreateLoader(int id, Bundle args) {
        return new ThumbnailLoader(getContext(), thumbnailUrl, preview);
    }

    @Override
    public void onLoadFinished(Loader<ImageView> loader, ImageView data) {
        if(data != null){
            preview = data;
        }
    }

    @Override
    public void onLoaderReset(Loader<ImageView> loader) {
        return;
    }
}
