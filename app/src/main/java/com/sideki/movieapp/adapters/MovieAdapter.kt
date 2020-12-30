package com.sideki.movieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sideki.movieapp.R
import com.sideki.movieapp.model.data.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList = mutableSetOf<Movie>()

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val curItem = movieList.elementAt(position)

        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500" + curItem.posterPath)
            .into(holder.itemView.img_movie)
        holder.itemView.txt_movie_title.text = curItem.title
        if (curItem.releaseDate == " ") {
            holder.itemView.txt_movie_relisedate.text = "No Data"
        } else {
            holder.itemView.txt_movie_relisedate.text = curItem.releaseDate
        }

        holder.itemView.txt_movie_original_language.text = curItem.originalLanguage

        holder.itemView.rating_bar.rating = (curItem.voteAverage) / 2

        holder.itemView.item_movie.setOnClickListener {

        }
    }

    override fun getItemCount() = movieList.size

    fun setPopularMovies(_movieList: List<Movie>) {
        movieList.clear()
        movieList.addAll(_movieList)
        notifyDataSetChanged()
    }

    fun setPopularMoviesNextPage(_movieList: List<Movie>) {
        movieList.addAll(_movieList)
        notifyDataSetChanged()
    }

    fun setSearchMovies(_movieList: List<Movie>) {
        movieList.clear()
        movieList.addAll(_movieList)
        notifyDataSetChanged()
    }

    fun setSearchMoviesNextPage(_movieList: List<Movie>) {
        movieList.addAll(_movieList)
        notifyDataSetChanged()
    }
}