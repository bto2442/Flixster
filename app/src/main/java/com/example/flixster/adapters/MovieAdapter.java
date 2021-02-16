package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.model.Movie;

import java.util.List;

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
        //View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
       // return new ViewHolder(movieView);
        return viewHolder;
    }

    //Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter","onBindViewHolder"+ position);
       /* switch (holder.getItemViewType()){
            case 0;
                ViewHolder
        }*/
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

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle =itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }        public void bind(Movie movie) {
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
            
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(ivPoster);
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

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);
        }
    }
}
