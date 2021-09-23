package com.app.upcomingmovies.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.upcomingmovies.response.MovieImage
import com.app.upcomingmovies.response.MovieVideo

@Dao
interface MovieVideosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieVideos(images: List<MovieVideo>)

    @Query("SELECT * FROM movie_video WHERE movieId=:id")
    suspend fun getMovieVideos(id: String): List<MovieVideo>
}