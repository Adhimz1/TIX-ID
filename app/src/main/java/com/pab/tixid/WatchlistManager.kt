package com.pab.tixid

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object WatchlistManager {
    private const val PREF_NAME = "watchlist_prefs"
    private const val KEY_WATCHLIST = "watchlist_movies"

    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun addToWatchlist(movie: WatchlistMovie) {
        val watchlist = getWatchlist().toMutableList()
        if (!watchlist.any { it.title == movie.title }) {
            watchlist.add(movie)
            saveWatchlist(watchlist)
        }
    }

    fun removeFromWatchlist(title: String) {
        val watchlist = getWatchlist().toMutableList()
        watchlist.removeAll { it.title == title }
        saveWatchlist(watchlist)
    }

    fun isInWatchlist(title: String): Boolean {
        return getWatchlist().any { it.title == title }
    }

    fun getWatchlist(): List<WatchlistMovie> {
        val json = sharedPreferences.getString(KEY_WATCHLIST, null) ?: return emptyList()
        val type = object : TypeToken<List<WatchlistMovie>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun saveWatchlist(watchlist: List<WatchlistMovie>) {
        val json = gson.toJson(watchlist)
        sharedPreferences.edit().putString(KEY_WATCHLIST, json).apply()
    }
}

