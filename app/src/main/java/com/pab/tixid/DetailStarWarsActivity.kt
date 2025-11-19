package com.pab.tixid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailStarWarsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_star_wars)

        val ivBack = findViewById<ImageView>(R.id.iv_back)
        val ivTrailerThumbnail = findViewById<ImageView>(R.id.iv_trailer_thumbnail)
        val cvTrailer = findViewById<androidx.cardview.widget.CardView>(R.id.cv_trailer)

        // YouTube video ID: 8Qn_spdM5Zg
        val videoId = "8Qn_spdM5Zg"
        val thumbnailUrl = "https://img.youtube.com/vi/$videoId/maxresdefault.jpg"

        Glide.with(this)
            .load(thumbnailUrl)
            .placeholder(R.drawable.poster_starwars)
            .into(ivTrailerThumbnail)

        cvTrailer.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))
            startActivity(intent)
        }

        ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}

