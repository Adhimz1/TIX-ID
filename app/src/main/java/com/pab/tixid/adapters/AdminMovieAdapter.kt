package com.pab.tixid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pab.tixid.R
import com.pab.tixid.models.Movie

class AdminMovieAdapter(
    private var movies: List<Movie>,
    private val onEditClick: (Movie) -> Unit
) : RecyclerView.Adapter<AdminMovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPoster: ImageView = itemView.findViewById(R.id.iv_poster)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvGenre: TextView = itemView.findViewById(R.id.tv_genre)
        val tvRating: TextView = itemView.findViewById(R.id.tv_rating)
        val tvDuration: TextView = itemView.findViewById(R.id.tv_duration)
        val tvReleaseDate: TextView = itemView.findViewById(R.id.tv_release_date)
        val tvSynopsis: TextView = itemView.findViewById(R.id.tv_synopsis)
        val btnEdit: ImageView = itemView.findViewById(R.id.btn_edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.tvTitle.text = movie.title
        holder.tvGenre.text = movie.genre ?: "N/A"
        holder.tvRating.text = String.format("%.1f", movie.rating)
        holder.tvDuration.text = movie.duration ?: "N/A"
        holder.tvReleaseDate.text = movie.release_date ?: "N/A"
        holder.tvSynopsis.text = movie.synopsis

        // Load poster image (you can use Glide or Picasso later)
        // For now, we'll use a placeholder

        holder.btnEdit.setOnClickListener {
            onEditClick(movie)
        }

        holder.itemView.setOnClickListener {
            onEditClick(movie)
        }
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}

