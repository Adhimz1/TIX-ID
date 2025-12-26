package com.pab.tixid.models

import com.google.gson.annotations.SerializedName

data class Admin(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String?,
    @SerializedName("created_at") val created_at: String
)

data class AdminRequest(
    @SerializedName("admin_email") val admin_email: String,
    @SerializedName("action") val action: String,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("password") val password: String? = null
)

