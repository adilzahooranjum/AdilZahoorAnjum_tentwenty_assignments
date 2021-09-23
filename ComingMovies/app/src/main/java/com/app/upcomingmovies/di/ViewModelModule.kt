package com.app.upcomingmovies.di

import com.app.upcomingmovies.viewmodel.MovieDetailViewModel
import com.app.upcomingmovies.viewmodel.MovieListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieListViewModel(get()) }

    viewModel { MovieDetailViewModel(get()) }
}