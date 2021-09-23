package com.app.upcomingmovies.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MovieVideosResponse(val results: List<MovieVideo>)

@Entity(tableName = "movie_video")
data class MovieVideo(@PrimaryKey @SerializedName("key") val key: String, @ColumnInfo(name = "movieId") var id: String) {
    fun getVideoPath() = key
}