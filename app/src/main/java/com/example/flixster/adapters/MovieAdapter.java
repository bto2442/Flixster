package com.example.flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.flixster.DetailActivity;
import com.example.flixster.Glide.GlideApp;
import com.example.flixster.R;
import com.example.flixster.model.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    Context context;
    List<Movie> movies;


    public MovieAdapter(Context context, List<Movie> movies)
    {
        this.context= context;
        this.movies= movies;
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position)
    {
        //Log.d("getItemViewType","Rating: "+movies.get(position).getVoteAverage());
        if(movies.get(position).getVoteAverage()>5) {
            return 1;
        }else{
            return 0;}
    }

    //Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter","onCreateViewHolder");
        ViewHolder viewHolder;
        LayoutInflater inflater =LayoutInflater.from(context);
        Log.d("MovieAdapter","viewType: "+ viewType);
        if(viewType==0) {
            View v1 = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder(v1);
        }
         else if(viewType==1) {
             View v2 = inflater.inflate(R.layout.item_good_movie, parent, false);
             viewHolder = new PopularViewHolder(v2);
         }else{
                View v = inflater.inflate(R.layout.item_movie, parent, false);
                viewHolder = new ViewHolder(v);
        }
        return viewHolder;
    }

    //Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter","onBindViewHolder"+ position);
        //Get the movie at the passed in position
        Movie movie = movies.get(position);
        //Bind the movie data into the VH
        holder.bind(movie);
    }

    //Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle =itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container=itemView.findViewById(R.id.container);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            //If phone is in landscape
            if(context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                imageUrl=movie.getBackdropPath();
            }
            else{
                imageUrl=movie.getPosterPath();
            }
            //the imageUrl = back drop image
            //else imageUrl= poster image
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop
            GlideApp.with(context)
                    .load(imageUrl)
                    .transform(new FitCenter(), new RoundedCornersTransformation(radius, margin))
                    .placeholder(R.drawable.placeholder)
                    .into(ivPoster);

            //1. Register click listener on the whole row
            //2. Navigates to a new activity on tap
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    Pair<View, String> p1 = Pair.create((View)tvTitle, "transTitle");
                    Pair<View, String> p2 = Pair.create((View)tvOverview, "transOverview");
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context,p1,p2);
                    context.startActivity(i, options.toBundle());
                    // Pass data object in the bundle and populate details activity.
                   // context.startActivity(i);
                }
            });
        }
    }

    public class PopularViewHolder extends ViewHolder{

        ImageView imageView;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public void bind(Movie movie) {
            String imageUrl;
            //If phone is in landscape
            imageUrl=movie.getBackdropPath();
            //the imageUrl = back drop image
            //else imageUrl= poster image

            int radius = 100; // corner radius, higher value = more rounded
            int margin = 20; // crop margin, set to 0 for corners with no crop
            GlideApp.with(context)
                    .load(imageUrl)
                    .transform(new FitCenter(), new RoundedCornersTransformation(radius, margin))
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }
}
