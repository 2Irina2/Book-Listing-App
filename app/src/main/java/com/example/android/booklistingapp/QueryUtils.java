package com.example.android.booklistingapp;

import android.app.LoaderManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by irina on 21.06.2017.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<Book> fetchBookData(String requestUrl){

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHTTPRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making HTTP request");
        }

        List<Book> books = parseBookInfo(jsonResponse);

        return books;
    }

    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem creating the URL object");
        }

        return url;
    }

    private static String makeHTTPRequest(URL url) throws IOException{
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection;
        InputStream inputStream;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e(LOG_TAG, "Response code problem: " + urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request");
        }

        return jsonResponse;
    }

    private static List<Book> parseBookInfo(String bookJSON){
        if(TextUtils.isEmpty(bookJSON)){
            return null;
        }

        List<Book> books = new ArrayList<>();
        try{
            JSONObject root = new JSONObject(bookJSON);

            JSONArray bookArray = root.getJSONArray("items");

            for(int i = 0; i < bookArray.length(); i++){
                JSONObject bookItem = bookArray.getJSONObject(i);
                JSONObject bookInfo = bookItem.getJSONObject("volumeInfo");
                String bookTitle = bookInfo.getString("title");
                String bookSubtitle;
                if(bookInfo.has("subtitle")){
                    bookSubtitle = bookInfo.getString("subtitle");
                }
                else{
                    bookSubtitle = "";
                }
                String bookUrl = bookInfo.getString("infoLink");
                JSONObject bookImages = bookInfo.getJSONObject("imageLinks");
                String bookImageUrl = bookImages.getString("smallThumbnail");
                String authorsList;
                if(bookInfo.has("authors")){
                    JSONArray bookAuthors = bookInfo.getJSONArray("authors");
                    authorsList = bookAuthors.getString(0);
                    for(int j = 1; j < bookAuthors.length(); j++){
                        authorsList = authorsList + "\n" + bookAuthors.getString(j);
                    }
                }
                else{
                    authorsList = "Unknown";
                }

                Book book = new Book(bookTitle, bookSubtitle, authorsList, bookUrl, bookImageUrl);
                books.add(book);

            }
        }catch (JSONException e){
            Log.e(LOG_TAG, "Problem parsing JSON");
        }

        return books;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String line = bufferReader.readLine();
            while(line != null){
                output.append(line);
                line = bufferReader.readLine();
            }
        }
        return output.toString();
    }
}
