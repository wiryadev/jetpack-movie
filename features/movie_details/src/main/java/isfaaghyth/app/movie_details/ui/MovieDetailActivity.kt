package isfaaghyth.app.movie_details.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.airbnb.deeplinkdispatch.DeepLink
import isfaaghyth.app.abstraction.base.BaseActivity
import isfaaghyth.app.abstraction.util.AppLink.MovieDetail.MOVIE_DETAIL
import isfaaghyth.app.abstraction.util.AppLink.MovieDetail.PARAM_MOVIE_ID
import isfaaghyth.app.abstraction.util.AppLink.MovieDetail.PARAM_TYPE
import isfaaghyth.app.abstraction.util.ext.hide
import isfaaghyth.app.abstraction.util.ext.load
import isfaaghyth.app.abstraction.util.ext.show
import isfaaghyth.app.abstraction.util.ext.toast
import isfaaghyth.app.abstraction.util.state.LoaderState
import isfaaghyth.app.data.entity.MovieDetail
import isfaaghyth.app.data.mapper.MovieDetailMapper
import isfaaghyth.app.movie_details.databinding.ActivityMovieDetailBinding
import isfaaghyth.app.movie_details.di.DaggerMovieDetailComponent
import javax.inject.Inject

@DeepLink(MOVIE_DETAIL)
class MovieDetailActivity: BaseActivity<ActivityMovieDetailBinding>() {

    override fun contentView() = ActivityMovieDetailBinding.inflate(layoutInflater)

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MovieDetailViewModel

    override fun initView() {
        viewModel = ViewModelProvider(this, viewModelFactory)[MovieDetailViewModel::class.java]

        initParam()
        initObservable()
    }

    private fun initParam() {
        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
            val parameters = intent.extras?: Bundle()
            val movieId = parameters.getString(PARAM_MOVIE_ID)?: ""

            when(parameters.getString(PARAM_TYPE)) {
                TYPE_MOVIE -> viewModel.getMovieDetail(movieId)
                TYPE_TV -> viewModel.getTVShowDetail(movieId)
                else -> finish()
            }
        }
    }

    private fun initObservable() {
        viewModel.state.observe(this) {
            when (it) {
                is LoaderState.ShowLoading -> {
                    binding.rootView.hide()
                    binding.progressBar.show()
                }
                is LoaderState.HideLoading -> {
                    binding.rootView.show()
                    binding.progressBar.hide()
                }
            }
        }

        viewModel.movieDetail.observe(this) { movie ->
            showDetail(MovieDetailMapper.mapFromMovie(movie))
        }

        viewModel.tvDetail.observe(this) { tv ->
            showDetail(MovieDetailMapper.mapFromTVShow(tv))
        }

        viewModel.error.observe(this) { toast(it) }
    }

    private fun showDetail(detail: MovieDetail) {
        binding.run {
            imgBanner.load(detail.backdropPath)
            imgPoster.load(detail.posterPath)
            txtMovieName.text = detail.title
            txtContent.text = detail.overview
            txtRating.text = detail.voteCount.toString()
            txtVote.text = detail.voteAverage.toString()
        }
    }

    override fun initInjector() {
        DaggerMovieDetailComponent.builder()
            .movieDetailModule(MovieDetailModule())
            .build()
            .inject(this)
    }

    companion object {
        const val TYPE_MOVIE = "movie"
        const val TYPE_TV = "tv"
    }

}