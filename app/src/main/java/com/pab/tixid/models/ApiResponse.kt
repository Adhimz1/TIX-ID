package com.pab.tixid.models

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)

