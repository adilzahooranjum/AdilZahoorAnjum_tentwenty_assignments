package com.app.upcomingmovies.ui.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.app.upcomingmovies.R
import com.app.upcomingmovies.adapter.ImageSliderAdapter
import com.app.upcomingmovies.response.MovieImage
import com.app.upcomingmovies.response.MovieVideo
import com.app.upcomingmovies.ui.base.BaseFragment
import com.app.upcomingmovies.utils.gone
import com.app.upcomingmovies.utils.invisible
import com.app.upcomingmovies.utils.toast
import com.app.upcomingmovies.utils.visible
import com.app.upcomingmovies.viewmodel.MovieDetailViewModel
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailFragment : BaseFragment() {
    private val viewModel: MovieDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lateinit var videoLink:String
        arguments?.run {
            with(MovieDetailFragmentArgs.fromBundle(this)) {
                viewModel.fetchImages(id)
                viewModel.fetchVideos(id)
                setTitle(titile)
            }
        }
        trailer.setOnClickListener {
                watchYoutubeVideo(videoLink)
        }
        viewModel.error.observe(viewLifecycleOwner, Observer {
            requireContext().toast(it)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
            container.visibility = if (it) View.GONE else View.VISIBLE
        })

        viewModel.movieImages.observe(viewLifecycleOwner, Observer {
            imageSlider.adapter = ImageSliderAdapter(it)
            if (it.size > 1) {
                pageIndicator.visible()
                pageIndicator.attachTo(imageSlider)
            } else {
                pageIndicator.gone()
            }
        })
        viewModel.movieVideos.observe(viewLifecycleOwner, Observer {
            if (it.size > 1) {
                videoLink = it.get(0).key
                trailer.visible()
            } else {
                trailer.invisible()
            }
        })
        viewModel.movie.observe(viewLifecycleOwner, Observer {
            it.run {
                tv_title.text = title
                tv_overview.text = overview
            }
        })
    }
    fun watchYoutubeVideo(id: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$id")
        )
        try {
            startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            startActivity(webIntent)
        }
    }
    override fun getLayout(): Int = R.id.movieDetailFragment

    override fun setTitle(title: String) {
        tvTitle?.text = title
    }
}