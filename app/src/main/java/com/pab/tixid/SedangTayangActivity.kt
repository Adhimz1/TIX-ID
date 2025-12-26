package com.pab.tixid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.pab.tixid.adapters.MovieApiAdapter
import com.pab.tixid.api.RetrofitClient
import com.pab.tixid.databinding.ActivitySedangTayangBinding
import kotlinx.coroutines.launch

class SedangTayangActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySedangTayangBinding
    private lateinit var adapter: MovieApiAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmpty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySedangTayangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Initialize views
        progressBar = ProgressBar(this).apply {
            layoutParams = androidx.constraintlayout.widget.ConstraintLayout.LayoutParams(
                androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.WRAP_CONTENT,
                androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
        }

        tvEmpty = TextView(this).apply {
            text = "Belum ada film"
            textSize = 16f
            setTextColor(resources.getColor(android.R.color.white, null))
        }

        // Setup RecyclerView
        adapter = MovieApiAdapter(emptyList()) { movie ->
            // Handle click
            Toast.makeText(this, "Clicked: ${movie.title}", Toast.LENGTH_SHORT).show()
        }
        binding.rvMoviesGrid.layoutManager = GridLayoutManager(this, 2)
        binding.rvMoviesGrid.adapter = adapter

        // Load movies
        loadMovies()
    }

    private fun loadMovies() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getMovies("now_showing")

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.success == true) {
                        // Handle both possible response structures
                        val movies = when (val data = apiResponse.data) {
                            is Map<*, *> -> {
                                // If data is a map, get "movies" key
                                @Suppress("UNCHECKED_CAST")
                                (data["movies"] as? List<*>)?.filterIsInstance<com.pab.tixid.models.Movie>() ?: emptyList()
                            }
                            is List<*> -> {
                                // If data is already a list
                                data.filterIsInstance<com.pab.tixid.models.Movie>()
                            }
                            else -> emptyList()
                        }

                        adapter.updateMovies(movies)

                        if (movies.isEmpty()) {
                            tvEmpty.visibility = View.VISIBLE
                            Toast.makeText(
                                this@SedangTayangActivity,
                                "Belum ada film sedang tayang",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            tvEmpty.visibility = View.GONE
                        }
                    } else {
                        Toast.makeText(
                            this@SedangTayangActivity,
                            apiResponse?.message ?: "Gagal memuat film",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    val errorMsg = "Error ${response.code()}: ${response.message()}"
                    Toast.makeText(
                        this@SedangTayangActivity,
                        errorMsg,
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@SedangTayangActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadMovies() // Refresh when returning
    }
}
