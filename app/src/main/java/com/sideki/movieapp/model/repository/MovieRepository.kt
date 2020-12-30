package com.sideki.movieapp.model.repository

import com.sideki.movieapp.api.Servicey
import com.sideki.movieapp.model.data.Movies
import com.sideki.movieapp.utils.API_KEY
import retrofit2.Response

class MovieRepository {

    private var mPagePopularMovie: String = "1"

    private var mQuerySearch: String = ""
    private var mPageSearch: String = "1"

    suspend fun getPopularMovies(key: String, page: String): Response<Movies> {
        mPagePopularMovie = "1"
        return Servicey.movieApi.getPopularMovies(key, page)
    }

    suspend fun getPopularMoviesNextPage(): Response<Movies> {
        val pageInt = Integer.parseInt(mPagePopularMovie) + 1
        mPagePopularMovie = pageInt.toString()
        return Servicey.movieApi.getPopularMovies(API_KEY, mPagePopularMovie)
    }

    suspend fun searchMovies(key: String, query: String, page: String): Response<Movies> {
        mQuerySearch = query
        mPageSearch = page
        return Servicey.movieApi.searchMovies(key, query, page)
    }

    suspend fun searchMoviesNextPage(query: String): Response<Movies> {
        val pageInt = Integer.parseInt(mPageSearch) + 1
        mPageSearch = pageInt.toString()
        return Servicey.movieApi.searchMovies(API_KEY, query, pageInt.toString())
    }
}