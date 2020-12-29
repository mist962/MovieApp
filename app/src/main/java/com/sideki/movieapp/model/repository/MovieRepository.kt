package com.sideki.movieapp.model.repository

import com.sideki.movieapp.model.data.Movie
import com.sideki.movieapp.model.data.Movies
import com.sideki.movieapp.api.Servicey
import retrofit2.Response

class MovieRepository {

    suspend fun getMovie(movie_id: Int, key: String): Response<Movie> {
        return Servicey.movieApi.getMovie(movie_id, key)
    }

    suspend fun searchMovies(key: String, query: String, page: String): Response<Movies>{
        return Servicey.movieApi.searchMovies(key, query, page)
    }
}