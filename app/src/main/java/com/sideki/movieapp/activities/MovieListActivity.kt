package com.sideki.movieapp.activities

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.sideki.movieapp.R
import com.sideki.movieapp.adapters.MovieAdapter
import com.sideki.movieapp.model.repository.MovieRepository
import com.sideki.movieapp.utils.API_KEY
import com.sideki.movieapp.viewmodel.MovieViewModel
import com.sideki.movieapp.viewmodel.MovieViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MovieListActivity : AppCompatActivity() {

    private val repository = MovieRepository()
    private var movieViewModel = MovieViewModel(repository)
    private val movieViewModelFactory = MovieViewModelFactory(repository)
    private val adapter by lazy { MovieAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBar(toolbar)

        movieViewModel =
            ViewModelProvider(this, movieViewModelFactory).get(MovieViewModel::class.java)

        recyclerview.adapter = adapter

        getPopularMovies()
        searchMovies()
    }

    private fun getPopularMovies() {
        movieViewModel.getPopularMovies(API_KEY, "1")
        movieViewModel.mPopularMovies.observe(this, { response ->
            if (response.isSuccessful) {
                Log.d("TAG", "Popular Movie : $response")
                response.body()?.results?.let {
                    adapter.setPopularMovies(it)
                    recyclerview.scrollToPosition(0)

                    //Проблема в том что при сильном скроле делается много однотипных запросов, сраницы начинают повторяться
                    recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrollStateChanged(
                            recyclerView: RecyclerView,
                            newState: Int
                        ) {
                            if (!recyclerView.canScrollVertically(1)) {
                                movieViewModel.getPopularMoviesNextPage()
                                movieViewModel.mPopularMoviesNextPage.observe(
                                    this@MovieListActivity,
                                    { response ->
                                        Log.d("TAG", "Popular Movie : $response")
                                        response.body()?.results?.let {
                                            adapter.setPopularMoviesNextPage(it)
                                        }
                                    })
                            }
                        }
                    })
                }
            }
        })
    }

    private fun searchMovies() {
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty() == true) {
                    getPopularMovies()
                } else {
                    movieViewModel.searchMovies(API_KEY, newText.toString(), "1")
                    movieViewModel.mSearchMovies.observe(this@MovieListActivity, { resposne ->
                        Log.d("TAG", "Popular Movie : $resposne")
                        resposne.body()?.results?.let {
                            adapter.setSearchMovies(it)
                            recyclerview.scrollToPosition(0)
                        }

                        //Проблема в том что при сильном скроле делается много однотипных запросов, сраницы начинают повторяться
                        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                            override fun onScrollStateChanged(
                                recyclerView: RecyclerView,
                                newState: Int
                            ) {
                                if (!recyclerView.canScrollVertically(1)) {
                                    movieViewModel.searchMoviesNextPage(newText.toString())
                                    movieViewModel.mSearchMoviesNextPage.observe(
                                        this@MovieListActivity,
                                        { response ->
                                            Log.d("TAG", "Fight Movie : $response")
                                            response.body()?.results?.let {
                                                adapter.setSearchMoviesNextPage(it)
                                            }
                                        })
                                }
                            }
                        })
                    })
                }
                return true
            }
        })
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}



