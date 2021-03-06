package com.app.upcomingmovies.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.upcomingmovies.response.Movie
import com.app.upcomingmovies.response.MovieImage
import com.app.upcomingmovies.response.MovieVideo

@Database(entities = [Movie::class, MovieImage::class,MovieVideo::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao

    abstract fun getMovieImagesDao(): MovieImagesDao

    abstract fun getMovieVideosDao(): MovieVideosDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getDatabase(context: Context): MoviesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java,
                    "movies_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}