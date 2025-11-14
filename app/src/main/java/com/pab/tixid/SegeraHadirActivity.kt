package com.pab.tixid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
            Movie("MULAN", R.drawable.poster_mulan),
            Movie("THE LION KING", R.drawable.poster_thelionking),
            Movie("ZOOTOPIA", R.drawable.poster_zootopia),
            Movie("THE LAST OF US", R.drawable.poster_thelastofus)
        )

        val adapter = MovieGridAdapter(movies, isSedangTayang = false)
        binding.rvMoviesGrid.layoutManager = GridLayoutManager(this, 2)
        binding.rvMoviesGrid.adapter = adapter
    }
}
