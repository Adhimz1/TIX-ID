package com.pab.tixid

import com.google.android.material.button.MaterialButton

object WatchlistHelper {

    fun setupWatchlistButton(
        activity: android.app.Activity,
        button: MaterialButton,
        movieTitle: String,
        movieData: WatchlistMovie
    ) {
        WatchlistManager.init(activity)

        fun updateButton() {
            val isInWatchlist = WatchlistManager.isInWatchlist(movieTitle)

            if (isInWatchlist) {
                button.setIconTintResource(android.R.color.holo_red_dark)
                button.text = "Di Watchlist"
            } else {
                button.setIconTintResource(android.R.color.white)
                button.text = "Watchlist"
            }
        }

        updateButton()

        button.setOnClickListener {
            if (WatchlistManager.isInWatchlist(movieTitle)) {
                WatchlistManager.removeFromWatchlist(movieTitle)
            } else {
                WatchlistManager.addToWatchlist(movieData)
            }
            updateButton()
        }
    }
}

