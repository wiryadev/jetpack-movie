package isfaaghyth.app.movies.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import isfaaghyth.app.abstraction.util.ext.load
import isfaaghyth.app.data.entity.Movie
import isfaaghyth.app.movies.databinding.ItemMovieBinding

class MovieAdapter(private val movies: List<Movie>): RecyclerView.Adapter<MovieAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder.inflate(parent)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(movies[position])
    }

    class Holder(binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        private val title = binding.txtMovieName
        private val cardItem = binding.cardMovie
        private val poster = binding.imgPoster
        private val year = binding.txtYear

        companion object {
            fun inflate(parent: ViewGroup): Holder {
                return Holder(
                    ItemMovieBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(movie: Movie) {
            title.text = movie.title
            year.text = movie.releaseDate
            poster.load(movie.bannerUrl())
            cardItem.setOnClickListener {
                val context = it.context
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("jetmovie://detail/movie/${movie.id}")))
            }
        }
    }

}