package com.pab.tixid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.pab.tixid.databinding.ActivitySedangTayangBinding

class SedangTayangActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySedangTayangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySedangTayangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val movies = listOf(
            Movie("Spider-Man: Far From Home", R.drawable.poster_spiderman),
            Movie("WALL-E", R.drawable.poster_walle),
            Movie("THE GOOD DINOSAUR", R.drawable.poster_thegooddinosaur),
            Movie("STRANGER THINGS", R.drawable.poster_strangerthings),
            Movie("DEADPOOL", R.drawable.poster_deadpool),
            Movie("Avengers: Endgame", R.drawable.poster_avengers)
        )

        val adapter = MovieGridAdapter(movies, true) { movie ->
            when (movie.title) {
                "Spider-Man: Far From Home" -> {
                    val intent = android.content.Intent(this, DetailSpidermanActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        binding.rvMoviesGrid.layoutManager = GridLayoutManager(this, 2)
        binding.rvMoviesGrid.adapter = adapter
    }
}
