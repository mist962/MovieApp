package com.sideki.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sideki.movieapp.model.data.Movies
import com.sideki.movieapp.model.repository.MovieRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val mPopularMovies: MutableLiveData<Response<Movies>> = MutableLiveData()
    val mPopularMoviesNextPage: MutableLiveData<Response<Movies>> = MutableLiveData()
    val mSearchMovies: MutableLiveData<Response<Movies>> = MutableLiveData()
    val mSearchMoviesNextPage: MutableLiveData<Response<Movies>> = MutableLiveData()

    fun getPopularMovies(key: String, page: String) {
        viewModelScope.launch {
            mPopularMovies.value = repository.getPopularMovies(key, page)
        }
    }

    fun getPopularMoviesNextPage() {
        viewModelScope.launch {
            mPopularMoviesNextPage.value = repository.getPopularMoviesNextPage()
        }
    }

    fun searchMovies(key: String, query: String, page: String) {
        viewModelScope.launch {
            mSearchMovies.value = repository.searchMovies(key, query, page)
        }
    }

    fun searchMoviesNextPage(query: String) {
        viewModelScope.launch {
            mSearchMoviesNextPage.value = repository.searchMoviesNextPage(query)
        }
    }

}