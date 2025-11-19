package com.pab.tixid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pab.tixid.databinding.ItemWatchlistBinding

class WatchlistAdapter(
    private var movies: MutableList<WatchlistMovie>,
    private val onRemove: (WatchlistMovie) -> Unit
) : RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        val binding = ItemWatchlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WatchlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    fun updateData(newMovies: List<WatchlistMovie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    inner class WatchlistViewHolder(private val binding: ItemWatchlistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: WatchlistMovie) {
            binding.ivWatchlistPoster.setImageResource(movie.poster)
            binding.tvWatchlistTitle.text = movie.title
            binding.tvWatchlistRating.text = movie.rating
            binding.tvWatchlistDuration.text = movie.duration
            binding.tvWatchlistGenre.text = movie.genre

            binding.ivRemoveWatchlist.setOnClickListener {
                onRemove(movie)
            }
        }
    }
}

