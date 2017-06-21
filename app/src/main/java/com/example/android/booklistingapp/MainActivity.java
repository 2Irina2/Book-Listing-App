package com.example.android.booklistingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        ListView listview = (ListView) findViewById(R.id.list);

        final ArrayList<Book> words = new ArrayList<Book>();
        words.add(new Book("Title1", "Subtitle1", "Authors1", "Url1", "http://books.google.com/books/content?id=6tLAyQLSzG0C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api" ));
        words.add(new Book("Title2", "Subtitle2", "Authors2", "Url2", "http://books.google.com/books/content?id=6tLAyQLSzG0C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api" ));
        words.add(new Book("Title3", "Subtitle3", "Authors3", "Url3", "http://books.google.com/books/content?id=6tLAyQLSzG0C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api" ));
        words.add(new Book("Title4", "Subtitle4", "Authors4", "Url4", "http://books.google.com/books/content?id=6tLAyQLSzG0C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api" ));
        words.add(new Book("Title5", "Subtitle5", "Authors5", "Url5", "http://books.google.com/books/content?id=6tLAyQLSzG0C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api" ));
        words.add(new Book("Title6", "Subtitle6", "Authors6", "Url6", "http://books.google.com/books/content?id=6tLAyQLSzG0C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api" ));

        BookAdapter adapter = new BookAdapter(this, words);
        //BookAdapter adapter = new BookAdapter(this, new ArrayList<Book>());

        listview.setAdapter(adapter);
    }
}
