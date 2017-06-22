package com.example.android.booklistingapp;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.key;
import static android.R.attr.permission;
import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    private BookAdapter adapter;

    private static final String LOG_TAG = MainActivity.class.getName();

    public static String requestUrl;

    TextView emptyStateView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        ListView listview = (ListView) findViewById(R.id.list);
        emptyStateView = (TextView) findViewById(R.id.empty_text);
        listview.setEmptyView(emptyStateView);

        adapter = new BookAdapter(this, new ArrayList<Book>());
        listview.setAdapter(adapter);

        final Button searchButton = (Button) findViewById(R.id.search);

        final LoaderManager loaderManager = getLoaderManager();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasInternetAccess()) {
                    progressBar.setVisibility(View.VISIBLE);
                    emptyStateView.setVisibility(View.GONE);
                    EditText searchField = (EditText) findViewById(R.id.text_field);
                    String searchTerm = searchField.getText().toString().trim();
                    searchTerm = searchTerm.replace(" ", "+");
                    Log.e(LOG_TAG, "Search term is: " + searchTerm);
                    requestUrl = "https://www.googleapis.com/books/v1/volumes?q=" + searchTerm;
                    loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                }
                else{
                    adapter.clear();
                    emptyStateView.setText(R.string.no_internet);
                }
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book currentBook = adapter.getItem(position);
                String url = currentBook.getUrl();
                Uri uri = Uri.parse(url);
                Intent sendToWebsite = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(sendToWebsite);
            }
        });

    }

    private static final int BOOK_LOADER_ID = 1;


    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, requestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        adapter.clear();
        progressBar.setVisibility(View.GONE);
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        }
        else{
            emptyStateView.setText(R.string.no_book_found);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        adapter.clear();
    }

    public boolean hasInternetAccess() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
