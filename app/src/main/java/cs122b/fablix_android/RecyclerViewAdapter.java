package cs122b.fablix_android;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Movie> mMovies = new ArrayList<>();
    private Context mContext;

    public void addMovies(ArrayList<Movie> movies)
    {
        for (Movie movie : movies)
            mMovies.add(movie);
        notifyDataSetChanged();
    }

    public RecyclerViewAdapter(Context mContext, ArrayList<Movie> mMovies) {
        this.mMovies = mMovies;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called.");
        Glide.with(mContext).asBitmap().load(mMovies.get(i).url).into(viewHolder.image);
        viewHolder.title.setText(mMovies.get(i).title);
        viewHolder.rating.setRating(((float) mMovies.get(i).rating) / ((float) 2.0));
        viewHolder.year.setText(Integer.toString(mMovies.get(i).year));
        viewHolder.stars.setText(mMovies.get(i).actors);
        viewHolder.genres.setText(mMovies.get(i).genre);
        viewHolder.director.setText(mMovies.get(i).director);
        final Movie m = mMovies.get(i);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onBindViewHolder->setOnClickListener: started.");
                Log.d(TAG, "onClick: clicked on: " + mMovies.get(i).title);
                Toast.makeText(mContext, mMovies.get(i).title, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: Toast Ended.");
                Log.d(TAG, "onClick: intent started.");
                Intent intent = new Intent(mContext, MoviePageActivity.class);
                Log.d(TAG, "onClick: intent creation finished");
                Log.d(TAG, "onClick: intent.putextra(Parcelable) started");
                intent.putExtra("movie", mMovies.get(i));
                Log.d(TAG, "onClick: intent.putextra(Parcelable) ended");
                mContext.startActivity(intent);
                Log.d(TAG, "onBindViewHolder->setOnClickListener: ended.");
            }
        });
        Log.d(TAG, "onBindViewHolder: ended.");
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView image;
        private TextView title;
        private TextView year;
        private TextView stars;
        private TextView genres;
        private TextView director;
        private RatingBar rating;

        RelativeLayout parentLayout;
        public ViewHolder(View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.circleImage);
            title = itemView.findViewById(R.id.List_Title);
            year = itemView.findViewById(R.id.List_Year);
            rating = itemView.findViewById(R.id.List_Rating);
            stars = itemView.findViewById(R.id.List_Stars);
            genres = itemView.findViewById(R.id.List_Genres);
            director = itemView.findViewById(R.id.List_Director);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }


    }
}
