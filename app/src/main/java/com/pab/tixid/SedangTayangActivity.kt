package com.pab.tixid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
            Movie("WALL-E", R.drawable.poster_walle),
            Movie("THE GOOD DINOSAUR", R.drawable.poster_thegooddinosaur),
            Movie("STRANGER THINGS", R.drawable.poster_strangerthings),
            Movie("DEADPOOL", R.drawable.poster_deadpool)
        )

        val adapter = MovieGridAdapter(movies, isSedangTayang = true)
        binding.rvMoviesGrid.layoutManager = GridLayoutManager(this, 2)
        binding.rvMoviesGrid.adapter = adapter
    }
}
