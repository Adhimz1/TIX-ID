package com.pab.tixid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton

class DetailPotterActivity : AppCompatActivity() {

    private val movieTitle = "Harry Potter"
    private var isInWatchlist = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_potter)

        WatchlistManager.init(this)

        val ivBack = findViewById<ImageView>(R.id.iv_back)
        val ivTrailerThumbnail = findViewById<ImageView>(R.id.iv_trailer_thumbnail)
        val cvTrailer = findViewById<androidx.cardview.widget.CardView>(R.id.cv_trailer)
        val btnWatchlist = findViewById<MaterialButton>(R.id.btn_watchlist)

        // YouTube video ID from link: https://youtu.be/0Q3O8rauQx0?si=d_Ee9EihaT5Zfao1
        val videoId = "0Q3O8rauQx0"
        val thumbnailUrl = "https://img.youtube.com/vi/$videoId/maxresdefault.jpg"

        // Load thumbnail using Glide
        Glide.with(this)
            .load(thumbnailUrl)
            .placeholder(R.drawable.poster_potter)
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
                    poster = R.drawable.poster_potter,
                    duration = "152 Menit",
                    genre = "Fantasi, Petualangan, Keluarga",
                    rating = "SU"
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

