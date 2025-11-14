package com.pab.tixid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pab.tixid.databinding.ItemMovieGridBinding

class MovieGridAdapter(private val movies: List<Movie>, private val isSedangTayang: Boolean) :
    RecyclerView.Adapter<MovieGridAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    inner class MovieViewHolder(private val binding: ItemMovieGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.ivMoviePoster.setImageResource(movie.poster)
            binding.tvMovieTitle.text = movie.title

            if (isSedangTayang) {
                binding.btnBeliTiket.visibility = View.VISIBLE
                binding.tvWatchlist.visibility = View.GONE
            } else {
                binding.btnBeliTiket.visibility = View.GONE
                binding.tvWatchlist.visibility = View.VISIBLE
            }
        }
    }
}
