package com.sideki.movieapp.response

import com.google.gson.annotations.SerializedName
import com.sideki.movieapp.models.MovieModel

//This class if for getting multiple movies (Movies list) - popular movies
data class MovieSearchResponse(
    //var page: Int,
    var results: List<MovieModel>,

   // @SerializedName("total_pages")
    //var totalPages: Int,

    @SerializedName("total_results")
    var totalResults: Int
) {
}