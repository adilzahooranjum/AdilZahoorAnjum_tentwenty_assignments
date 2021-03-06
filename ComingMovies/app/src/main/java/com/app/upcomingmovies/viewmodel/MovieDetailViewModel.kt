package com.app.upcomingmovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.upcomingmovies.data.Repository
import com.app.upcomingmovies.response.Movie
import com.app.upcomingmovies.response.MovieImage
import com.app.upcomingmovies.response.MovieVideo
import com.app.upcomingmovies.response.Response
import com.app.upcomingmovies.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repository: Repository) : BaseViewModel() {
    private val _movieImages = MutableLiveData<List<MovieImage>>()
    private val _movie = MutableLiveData<Movie>()
    private val _video = MutableLiveData<List<MovieVideo>>()

    val movie: LiveData<Movie>
        get() = _movie

    val movieImages: LiveData<List<MovieImage>>
        get() = _movieImages

    val movieVideos: LiveData<List<MovieVideo>>
        get() = _video
    fun fetchImages(movieId: Long) {
        isLoading.value = true

        viewModelScope.launch {
            when (val response = repository.getImagesByMovieId(movieId)) {
                is Response.Success -> {
                    _movieImages.value = response.movies
                    fetchMovieDetail(movieId)
                }
                is Response.Error -> {
                    error.value = response.message
                    isLoading.value = false
                }
            }

        }
    }
    fun fetchVideos(movieId: Long) {
        isLoading.value = true
        viewModelScope.launch {
            when (val response = repository.getVideosByMovieId(movieId)) {
                is Response.Success -> {
                    _video.value = response.movies
                    //fetchMovieDetail(movieId)
                }
                is Response.Error -> {
                    error.value = "123"+response.message
                    isLoading.value = false
                }
            }
        }
    }
    private suspend fun fetchMovieDetail(movieId: Long) {
        when (val response = repository.getMovieById(movieId)) {
            is Response.Success -> _movie.value = response.movies
            is Response.Error -> error.value = response.message
        }
        isLoading.value = false
    }
}