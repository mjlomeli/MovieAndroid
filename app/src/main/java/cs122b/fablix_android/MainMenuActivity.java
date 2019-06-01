package cs122b.fablix_android;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainMenuActivity extends AppCompatActivity {

    private static final String TAG = "MainMenuActivity";

    private EditText edittext_search;
    private HashMap<String, Movie> mMovies = new HashMap<>();
    private ArrayList<String> images = new ArrayList<>();
    private RecyclerViewAdapter mAdapter;
    private RecyclerView recycler_view;
    private MoviesTask task;
    private Context context;


    private TextView title;
    private TextView year;
    private TextView stars;
    private TextView genres;
    private TextView director;
    private RatingBar rating;
    private CircleImageView image;


    private int pg_number = 1;
    private int item_count = 2;
    // variables for pagination
    private boolean isLoading = true;
    private int pastVisibleItems,visibleItemCount,totalItemCount,previous_total = 0;
    private int view_threshold = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate: started.");
        super.onCreate(savedInstanceState);

        title = findViewById(R.id.List_Title);
        year = findViewById(R.id.List_Year);
        rating = findViewById(R.id.List_Rating);
        stars = findViewById(R.id.List_Stars);
        genres = findViewById(R.id.List_Genres);
        director = findViewById(R.id.List_Director);
        image = findViewById(R.id.circleImage);

        context = this;
        recycler_view = findViewById(R.id.recycler_view);
        edittext_search = findViewById(R.id.EditText_Search);
        setContentView(R.layout.activity_mainmenu);
        try {
            task = new MoviesTask();
            task.execute(FetchURL.DEFAULT_MOVIE_SERVLET,"get_movies");
        } catch (Exception e) { e.printStackTrace(); }
        Log.d(TAG, "onCreate: ended.");
    }

    private void initImageBitmaps() throws ExecutionException, InterruptedException {

        Log.e(TAG, "initImageBitmaps: " + mMovies.size());
        for (Movie m : mMovies.values()) {
            task = new MoviesTask();
            Log.d(TAG, "initImageBit: " + m.mid);
            Log.d(TAG, "initImageBit: " + FetchURL.IMAGE_SERVLET + m.mid);
            task.execute(FetchURL.IMAGE_SERVLET + m.mid, "get_images");
        }
    }

    public void onClick_Search(View view) throws ExecutionException, InterruptedException {
        edittext_search = findViewById(R.id.EditText_Search);
        String search = edittext_search.getText().toString();
        String url = FetchURL.cookSearch(search, "1");
        task = new MoviesTask();
        task.execute(url,"get_movies");
        Log.e(TAG, "onCLick_Search: " + mMovies.size());

    }

    private void initRecyclerView()
    {
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        recycler_view = findViewById(R.id.recycler_view);
        mAdapter = new RecyclerViewAdapter(this, new ArrayList<Movie>(mMovies.values()));
        recycler_view.setAdapter(mAdapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }

    private String connect(String url) throws Exception {
        Log.d(TAG, "connect: " + url);
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        InputStream response = conn.getInputStream();
        try (Scanner scanner = new Scanner(response)) {
            return scanner.useDelimiter("\\A").next();
        } catch (Exception e) { return null; }
    }

    private class MoviesTask extends AsyncTask<String, Void, String>
    {
        private static final String TAG = "MoviesTask";
        protected String sec;
        protected String id;
        @Override
        protected String doInBackground(String... str) {
            try {
                Log.e(TAG, "doInBackground: " + str[0]);
                Log.e(TAG, "doInBackground: " + str[1]);
                if (str.length > 1)
                    sec = str[1];
                NukeSSLCerts.nuke();
                return connect(str[0]);
            } catch (Exception e) { return null; }
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: started");
            super.onPostExecute(s);

            switch (sec)
            {
                case "get_movies":
                    try {
                        Log.e(TAG, "onPostExecute: " + s);

                        JSONArray resArray = new JSONArray(s);
                        int len = resArray.length();
                        mMovies = new HashMap<>();
                        for (int i = 0; i < len; i++) {
                            JSONObject obj = resArray.getJSONObject(i);
                            Movie m = new Movie(obj.getString("mid"), obj.getString("Title"), obj.getInt("Rating"),
                                    obj.getString("Director"), obj.getInt("Year"), obj.getString("Genre"),
                                    obj.getString("Actors"));
                            mMovies.put(m.mid, m);
                        }
                        initImageBitmaps();
                    } catch (Exception e) {  }
                    break;
                case "get_images":
                    try {
                        Log.d(TAG, "onPostExecute: " + s);
                            JSONObject obj = (new JSONArray(s)).getJSONObject(0);
                            if (obj.has("url")) {
                                mMovies.get(obj.getString("id")).setURL(obj.getString("url"));
                            }
                            initRecyclerView();
                    } catch (Exception e) {  }
                    break;
                 default:
                    break;
            }
            if (mMovies.size() == 0)
            {
                    Log.d(TAG, "onPostExecute: if size == 0 started");
                    Toast.makeText(MainMenuActivity.this, "NO More!", Toast.LENGTH_LONG).show();
            }
            else
            {

            }

        }
    }

}
