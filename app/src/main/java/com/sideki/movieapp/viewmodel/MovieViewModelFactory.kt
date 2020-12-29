package com.sideki.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sideki.movieapp.model.repository.MovieRepository

class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(repository) as T
    }
}