package com.sideki.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sideki.movieapp.model.data.Movie
import com.sideki.movieapp.model.data.Movies
import com.sideki.movieapp.model.repository.MovieRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val mMovies: MutableLiveData<Response<Movie>> = MutableLiveData()
    val mSearchMovies: MutableLiveData<Response<Movies>> = MutableLiveData()

    fun getMovies(movie_id: Int, key: String) {
        viewModelScope.launch {
            mMovies.value = repository.getMovie(movie_id, key)
        }
    }

    fun searchMovies(key: String, query: String, page: String) {
        viewModelScope.launch {
            mSearchMovies.value = repository.searchMovies(key, query, page)
        }
    }
}