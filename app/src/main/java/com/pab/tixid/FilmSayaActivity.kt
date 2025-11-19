package com.pab.tixid

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.pab.tixid.databinding.ActivityFilmSayaBinding

class FilmSayaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilmSayaBinding
    private lateinit var adapter: WatchlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmSayaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WatchlistManager.init(this)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupRecyclerView()
        loadWatchlist()
    }

    override fun onResume() {
        super.onResume()
        loadWatchlist()
    }

    private fun setupRecyclerView() {
        adapter = WatchlistAdapter(mutableListOf()) { movie ->
            // Remove from watchlist
            WatchlistManager.removeFromWatchlist(movie.title)
            loadWatchlist()
        }

        binding.rvWatchlist.layoutManager = GridLayoutManager(this, 2)
        binding.rvWatchlist.adapter = adapter
    }

    private fun loadWatchlist() {
        val watchlist = WatchlistManager.getWatchlist()

        if (watchlist.isEmpty()) {
            binding.llEmptyState.visibility = View.VISIBLE
            binding.rvWatchlist.visibility = View.GONE
        } else {
            binding.llEmptyState.visibility = View.GONE
            binding.rvWatchlist.visibility = View.VISIBLE
            adapter.updateData(watchlist)
        }
    }
}

