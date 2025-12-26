package com.pab.tixid.models

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String?,
    val role: String?, // "user" or "admin"
    val created_at: String?
)

