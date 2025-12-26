package com.pab.tixid.models

data class MovieRequest(
    val admin_email: String,
    val id: Int? = null,
    val title: String? = null,
    val poster_url: String? = null,
    val synopsis: String? = null,
    val youtube_url: String? = null,
    val rating: Double? = null,
    val duration: String? = null,
    val genre: String? = null,
    val status: String? = null,
    val release_date: String? = null
)

