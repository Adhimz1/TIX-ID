package com.pab.tixid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton

class DetailLionKingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lion_king)

        val ivBack = findViewById<ImageView>(R.id.iv_back)
        val ivTrailerThumbnail = findViewById<ImageView>(R.id.iv_trailer_thumbnail)
        val cvTrailer = findViewById<androidx.cardview.widget.CardView>(R.id.cv_trailer)
        val btnWatchlist = findViewById<MaterialButton>(R.id.btn_watchlist)

        // YouTube video ID: 7TavVZMewpY
        val videoId = "7TavVZMewpY"
        val thumbnailUrl = "https://img.youtube.com/vi/$videoId/maxresdefault.jpg"

        Glide.with(this)
            .load(thumbnailUrl)
            .placeholder(R.drawable.poster_thelionking)
            .into(ivTrailerThumbnail)

        cvTrailer.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))
            startActivity(intent)
        }

        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Setup Watchlist
        WatchlistHelper.setupWatchlistButton(
            this,
            btnWatchlist,
            "The Lion King",
            WatchlistMovie(
                title = "The Lion King",
                poster = R.drawable.poster_thelionking,
                duration = "118 Menit",
                genre = "Animasi, Drama, Petualangan",
                rating = "SU"
            )
        )
    }
}

