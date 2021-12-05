package isfaaghyth.app.tvshows.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import isfaaghyth.app.abstraction.util.ext.load
import isfaaghyth.app.data.entity.TVShow
import isfaaghyth.app.tvshows.databinding.ItemTvshowBinding

class TVShowAdapter(private val tvs: List<TVShow>) : RecyclerView.Adapter<TVShowAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder.inflate(parent)
    }

    override fun getItemCount(): Int = tvs.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(tvs[position])
    }

    class Holder(binding: ItemTvshowBinding) : RecyclerView.ViewHolder(binding.root) {
        private val title = binding.txtMovieName
        private val cardItem = binding.cardMovie
        private val poster = binding.imgPoster
        private val year = binding.txtYear

        companion object {
            fun inflate(parent: ViewGroup): Holder {
                return Holder(
                    ItemTvshowBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(tvshow: TVShow) {
            title.text = tvshow.title
            year.text = tvshow.releaseDate
            poster.load(tvshow.bannerUrl())
            cardItem.setOnClickListener {
                val context = it.context
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("jetmovie://detail/tv/${tvshow.id}")
                    )
                )
            }
        }
    }

}