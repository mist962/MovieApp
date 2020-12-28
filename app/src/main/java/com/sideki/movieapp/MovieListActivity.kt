package com.sideki.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sideki.movieapp.models.MovieModel
import com.sideki.movieapp.request.Servicey
import com.sideki.movieapp.response.MovieSearchResponse
import com.sideki.movieapp.utils.API_KEY
import com.sideki.movieapp.utils.MovieApi
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MovieListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            getRetrofitResponseAccordingToID()
        }
    }


    private fun getRetrofitResponse() {
        val movieApi: MovieApi = Servicey.movieApi

        val responseCall: Call<MovieSearchResponse> = movieApi.searchMovie(
            API_KEY,
            "Jack Reacher",
            "1"
        )
        responseCall.enqueue(object : Callback<MovieSearchResponse> {
            override fun onResponse(
                call: Call<MovieSearchResponse>,
                response: Response<MovieSearchResponse>
            ) {
                if (response.code() == 200) {
                    Log.d("TAG", "${response} ")

                    val listMovie = mutableListOf<MovieModel>()
                    listMovie.addAll(response.body()!!.results)
                    var a = 0

                    listMovie.forEach {
                        a++
                        Log.d("TAG", "$a $it")
                    }
                } else {
                    try {
                        Log.d("TAG", "${response} ")
                        Log.d("TAG", "Error + ${response.errorBody().toString()}")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {

            }

        })
    }

    private fun getRetrofitResponseAccordingToID() {
        val movieApi: MovieApi = Servicey.movieApi

        val responseCall: Call<MovieModel> = movieApi.getMovie(
            550,
            API_KEY
        )

        responseCall.enqueue(object : Callback<MovieModel> {
            override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {

                if (response.code() == 200) {

                    val movie: MovieModel = response.body()?: MovieModel(
                        0, "TEST","TEST","TEST","TEST", 1.0)

                    Log.d("TAG", "$response")
                    Log.d("TAG", "Response: ${movie.title}")
                } else {
                    try {
                        Log.d("TAG", "${response.errorBody()}")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

            }

            override fun onFailure(call: Call<MovieModel>, t: Throwable) {

            }

        })
    }
}

