package com.sideki.movieapp.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sideki.movieapp.R
import com.sideki.movieapp.model.repository.MovieRepository
import com.sideki.movieapp.utils.API_KEY
import com.sideki.movieapp.viewmodel.MovieViewModel
import com.sideki.movieapp.viewmodel.MovieViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MovieListActivity : AppCompatActivity() {

    private val repository = MovieRepository()
    private var movieViewModel = MovieViewModel(repository)
    private val movieViewModelFactory = MovieViewModelFactory(repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieViewModel =
            ViewModelProvider(this, movieViewModelFactory).get(MovieViewModel::class.java)

        button.setOnClickListener {
            searchMovies()
        }
    }

    private fun getMovie() {
        movieViewModel.getMovies(550, API_KEY)
        movieViewModel.mMovies.observe(this, { response ->
            if (response.isSuccessful) {
                Log.d("TAG", "$response")
                response.body()?.let {
                    Log.d("TAG", "Response: ${it.title}")
                }
            }
        })
    }

    private fun searchMovies() {
        movieViewModel.searchMovies(API_KEY, "Fast", "1")
        movieViewModel.mSearchMovies.observe(this, { response ->
            if (response.isSuccessful) {
                Log.d("TAG", "$response")
                response.body()?.results?.let {
                    it.forEach {
                        Log.d("TAG", "Response: ${it.title}")
                    }
                }
            }
        })
    }
}


