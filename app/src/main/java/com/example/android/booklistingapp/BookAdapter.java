package com.example.android.booklistingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

import static com.example.android.booklistingapp.R.id.preview;

/**
 * Created by irina on 21.06.2017.
 */

public class BookAdapter extends ArrayAdapter<Book>{

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
        Picasso.with(getContext()).load(currentBook.getImageUrl()).into(preview);

        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentBook.getTitle());

        TextView subtitle = (TextView) listItemView.findViewById(R.id.subtitle);
        subtitle.setText(currentBook.getSubtitle());

        TextView authors = (TextView) listItemView.findViewById(R.id.authors);
        authors.setText(currentBook.getAuthors());

        return listItemView;

    }
}