package com.pab.tixid.models

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val phone: String
)

