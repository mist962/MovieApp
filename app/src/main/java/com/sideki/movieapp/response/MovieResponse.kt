package com.sideki.movieapp.response

import com.sideki.movieapp.models.MovieModel

//This class is for single movie request
data class MovieResponse(var results: MovieModel) {
    //1 - Finding the Movie Object

/*    @SerializedName("results")
    @Expose
    private val movie: MovieModel()*/

}