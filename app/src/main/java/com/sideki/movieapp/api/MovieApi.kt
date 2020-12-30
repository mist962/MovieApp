package com.sideki.movieapp.api

import com.sideki.movieapp.model.data.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    //https://api.themoviedb.org/3/movie/popular?api_key=d27280da3eef410846fedb75cfa2e796&page=1
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") key: String,
        @Query("page") page: String
    ): Response<Movies>

    //https://api.themoviedb.org/3/search/movie?api_key=d27280da3eef410846fedb75cfa2e796&query=Fast&page=1
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") key: String,
        @Query("query") query: String,
        @Query("page") page: String
    ): Response<Movies>

}