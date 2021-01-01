package com.sideki.movieapp.view.fragments

import android.util.Log
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.sideki.movieapp.R
import com.sideki.movieapp.adapters.MovieAdapter
import com.sideki.movieapp.model.repository.MovieRepository
import com.sideki.movieapp.utils.API_KEY
import com.sideki.movieapp.viewmodel.MovieViewModel
import com.sideki.movieapp.viewmodel.MovieViewModelFactory
import kotlinx.android.synthetic.main.fragment_movies_list.*

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    val args by navArgs<MoviesListFragmentArgs>()

    private val repository = MovieRepository()
    private var movieViewModel = MovieViewModel(repository)
    private val movieViewModelFactory = MovieViewModelFactory(repository)
    private val adapter by lazy { MovieAdapter() }

    override fun onResume() {
        super.onResume()
        activity?.setActionBar(toolbar)

        movieViewModel =
            ViewModelProvider(this, movieViewModelFactory).get(MovieViewModel::class.java)

        recyclerview.adapter = adapter

        getPopularMovies()
        searchMovies()
        Log.d("TAG", "Scrolled : ${args.recyclerPosition}")

    }

    private fun getPopularMovies() {
        movieViewModel.getPopularMovies(API_KEY, "1")
        movieViewModel.mPopularMovies.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                Log.d("TAG", "Popular Movie : $response")
                response.body()?.results?.let {
                    adapter.setPopularMovies(it)
                    recyclerview.scrollToPosition(args.recyclerPosition)
                    //Проблема в том что при сильном скроле делается много однотипных запросов, сраницы начинают повторяться
                    recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrollStateChanged(
                            recyclerView: RecyclerView,
                            newState: Int
                        ) {
                            if (!recyclerView.canScrollVertically(1)) {
                                movieViewModel.getPopularMoviesNextPage()
                                movieViewModel.mPopularMoviesNextPage.observe(
                                    viewLifecycleOwner,
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
                    movieViewModel.mSearchMovies.observe(viewLifecycleOwner, { resposne ->
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
                                        viewLifecycleOwner,
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

}