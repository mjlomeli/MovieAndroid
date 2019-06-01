package cs122b.fablix_android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class MoviePageActivity extends AppCompatActivity {

    private static final String TAG = "MoviePageActivity";

    private TextView title;
    private TextView year;
    private TextView rating;
    private TextView stars;
    private TextView genres;
    private TextView director;
    private ImageView image;
    private RatingBar ratingBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviepage);
        Log.d(TAG, "onCreate: started.");

        title = findViewById(R.id.Text_Title);
        year = findViewById(R.id.Text_Year);
        rating = findViewById(R.id.Text_Rating);
        stars = findViewById(R.id.Text_Stars);
        genres = findViewById(R.id.Text_Genres);
        director = findViewById(R.id.Text_Director);
        image = findViewById(R.id.ImageView_movie);
        ratingBar = findViewById(R.id.RatingBar_Rating);

        getIncomingIntent();
    }

    private void getIncomingIntent()
    {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");
        if (getIntent().hasExtra("movie") )
        {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            Movie movie = (Movie) getIntent().getSerializableExtra("movie");
            Glide.with(this).asBitmap().load(movie.url).into(image);
            title.setText(movie.title);
            year.setText("Year: " + Integer.toString(movie.year));
            rating.setText("Rating: " + Integer.toString(movie.rating) + "/10");
            stars.setText("Stars: " + movie.actors);
            genres.setText("Genre: " + movie.genre);
            director.setText("Director: " + movie.director);
            ratingBar.setNumStars(5);
            ratingBar.setRating(((float) movie.rating) / ((float) 2.0));

        }
        Log.d(TAG, "getIncomingIntent: ended.");
    }

}
