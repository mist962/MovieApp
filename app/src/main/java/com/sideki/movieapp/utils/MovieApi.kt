package com.sideki.movieapp.utils

import com.sideki.movieapp.models.MovieModel
import com.sideki.movieapp.response.MovieSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    //Search for movies
    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") key: String,
        @Query("query") query: String,
        @Query("page") page: String
    ): Call<MovieSearchResponse>

    //Search for ID
    //https://api.themoviedb.org/3/movie/550?api_key=d27280da3eef410846fedb75cfa2e796
    @GET("movie/{movie_id}")
    fun getMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") key: String
    ): Call<MovieModel>

}