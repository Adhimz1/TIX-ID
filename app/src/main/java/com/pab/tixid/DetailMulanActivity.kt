package com.pab.tixid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailMulanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_mulan)

        val ivBack = findViewById<ImageView>(R.id.iv_back)
        val ivTrailerThumbnail = findViewById<ImageView>(R.id.iv_trailer_thumbnail)
        val cvTrailer = findViewById<androidx.cardview.widget.CardView>(R.id.cv_trailer)

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
    }
}
