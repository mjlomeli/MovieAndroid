package cs122b.fablix_android;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Movie implements Serializable {
    private static final String TAG = "Movie";
    String mid;
    String title;
    int    rating;
    String director;
    int year;
    String genre;
    String actors;
    String url;

    HashMap <String, String> movie = new HashMap<>();

    @Override
    public String toString() {
        return "Movie{" +
                "mid='" + mid + '\'' +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", director='" + director + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", actors='" + actors + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public int getRating() {
        return rating;
    }

    public String getDirector() {
        return director;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getActors() {
        return actors;
    }

    public String getURL() { return url;};

    public void setURL(Object obj)
    {
        try
        {
            url = (String) obj;
            if (! url.contains("http") || url == null || url.equalsIgnoreCase("null"))
                url = FetchURL.DEFAULT_IMAGE_JPG;
        }
        catch (Exception e) { url = FetchURL.DEFAULT_IMAGE_JPG; }
    }

    public Movie(String mid, String title, int rating, String director, int year, String genre, String actors) {
        this.mid = mid;
        this.title = title;
        this.rating = rating;
        this.director = director;
        this.year = year;
        this.genre = genre;
        this.actors = actors;
        setURL(null);
    }

    public String getMid() {
        return mid;
    }

}
