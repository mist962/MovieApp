package com.sideki.movieapp.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id: Int,
    var title: String,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("release_date")
    var releaseDate: String,
    var overview: String,
    @SerializedName("vote_average")
    var voteAverage: Float, //?//?//?//?//?//?//?//?//?//?Возможная ошибка - было Double
    var runtime: Int
) : Parcelable