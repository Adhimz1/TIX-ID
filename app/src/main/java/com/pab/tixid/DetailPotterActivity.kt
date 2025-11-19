package com.pab.tixid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailPotterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_potter)

        val ivBack = findViewById<ImageView>(R.id.iv_back)
        val ivTrailerThumbnail = findViewById<ImageView>(R.id.iv_trailer_thumbnail)
        val cvTrailer = findViewById<androidx.cardview.widget.CardView>(R.id.cv_trailer)

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
    }
}

