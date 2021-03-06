package com.app.upcomingmovies.di

import com.app.upcomingmovies.dao.MoviesDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { MoviesDatabase.getDatabase(androidContext()) }

    single { get<MoviesDatabase>().getMoviesDao() }

    single { get<MoviesDatabase>().getMovieImagesDao() }

    single { get<MoviesDatabase>().getMovieVideosDao() }
}