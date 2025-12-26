package com.pab.tixid.models

data class Movie(
    val id: Int,
    val title: String,
    val poster_url: String,
    val synopsis: String,
    val youtube_url: String?,
    val rating: Double,
    val duration: String?,
    val genre: String?,
    val status: String, // "now_showing" or "coming_soon"
    val release_date: String?,
    val created_at: String?,
    val updated_at: String?
)

