package com.sideki.movieapp.model.repository

import com.sideki.movieapp.model.data.MovieModel
import com.sideki.movieapp.model.data.MoviesModel
import com.sideki.movieapp.api.Servicey
import retrofit2.Response

class MovieRepository {

    suspend fun getMovie(movie_id: Int, key: String): Response<MovieModel> {
        return Servicey.movieApi.getMovie(movie_id, key)
    }

    suspend fun searchMovies(key: String, query: String, page: String): Response<MoviesModel>{
        return Servicey.movieApi.searchMovies(key, query, page)
    }
}