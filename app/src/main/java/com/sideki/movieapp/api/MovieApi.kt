package com.sideki.movieapp.api

import com.sideki.movieapp.model.data.MovieModel
import com.sideki.movieapp.model.data.MoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") key: String
    ): Response<MovieModel>

    //https://api.themoviedb.org/3/search/movie?api_key=d27280da3eef410846fedb75cfa2e796&query=Fast&page=1
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") key: String,
        @Query("query") query: String,
        @Query("page") page: String
    ): Response<MoviesModel>

}