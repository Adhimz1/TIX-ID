package com.pab.tixid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton

class DetailMulanActivity : AppCompatActivity() {

    private val movieTitle = "MULAN"
    private var isInWatchlist = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_mulan)

        WatchlistManager.init(this)

        val ivBack = findViewById<ImageView>(R.id.iv_back)
        val ivTrailerThumbnail = findViewById<ImageView>(R.id.iv_trailer_thumbnail)
        val cvTrailer = findViewById<androidx.cardview.widget.CardView>(R.id.cv_trailer)
        val btnWatchlist = findViewById<MaterialButton>(R.id.btn_watchlist)

        // YouTube video ID
        val videoId = "R-eFm--k21c"
        val thumbnailUrl = "https://img.youtube.com/vi/$videoId/maxresdefault.jpg"

        // Load thumbnail using Glide
        Glide.with(this)
            .load(thumbnailUrl)
            .placeholder(R.drawable.poster_mulan)
            .into(ivTrailerThumbnail)

        // Open YouTube video when clicked
        cvTrailer.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))
            startActivity(intent)
        }

        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Check if movie is in watchlist
        updateWatchlistButton(btnWatchlist)

        // Watchlist button click
        btnWatchlist.setOnClickListener {
            if (isInWatchlist) {
                WatchlistManager.removeFromWatchlist(movieTitle)
            } else {
                val movie = WatchlistMovie(
                    title = movieTitle,
                    poster = R.drawable.poster_mulan,
                    duration = "115 Menit",
                    genre = "Aksi, Petualangan, Fantasi",
                    rating = "R13+"
                )
                WatchlistManager.addToWatchlist(movie)
            }
            updateWatchlistButton(btnWatchlist)
        }
    }

    private fun updateWatchlistButton(button: MaterialButton) {
        isInWatchlist = WatchlistManager.isInWatchlist(movieTitle)

        if (isInWatchlist) {
            button.setIconTintResource(android.R.color.holo_red_dark)
            button.text = "Di Watchlist"
        } else {
            button.setIconTintResource(android.R.color.white)
            button.text = "Watchlist"
        }
    }
}
