package com.sideki.movieapp.request

import com.sideki.movieapp.utils.BASE_URL
import com.sideki.movieapp.utils.MovieApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Servicey {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val movieApi by lazy {
        retrofit.create(MovieApi::class.java)
    }
}