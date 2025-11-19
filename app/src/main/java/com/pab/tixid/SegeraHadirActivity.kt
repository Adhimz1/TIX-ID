package com.pab.tixid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.pab.tixid.databinding.ActivitySegeraHadirBinding

class SegeraHadirActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySegeraHadirBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySegeraHadirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val movies = listOf(
            Movie("Mulan", R.drawable.poster_mulan),
            Movie("THE LION KING", R.drawable.poster_thelionking),
            Movie("ZOOTOPIA", R.drawable.poster_zootopia),
            Movie("THE LAST OF US", R.drawable.poster_thelastofus)
        )

        val adapter = MovieGridAdapter(movies, false) { movie ->
            when (movie.title) {
                "Mulan" -> {
                    val intent = Intent(this, DetailMulanActivity::class.java)
                    startActivity(intent)
                }
                "THE LION KING" -> {
                    val intent = Intent(this, DetailLionKingActivity::class.java)
                    startActivity(intent)
                }
                "ZOOTOPIA" -> {
                    val intent = Intent(this, DetailZootopiaActivity::class.java)
                    startActivity(intent)
                }
                "THE LAST OF US" -> {
                    val intent = Intent(this, DetailLastOfUsActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        binding.rvMoviesGrid.layoutManager = GridLayoutManager(this, 2)
        binding.rvMoviesGrid.adapter = adapter
    }
}
