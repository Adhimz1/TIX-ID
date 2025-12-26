package com.pab.tixid.api

import com.pab.tixid.models.Admin
import com.pab.tixid.models.AdminRequest
import com.pab.tixid.models.ApiResponse
import com.pab.tixid.models.LoginRequest
import com.pab.tixid.models.Movie
import com.pab.tixid.models.MovieRequest
import com.pab.tixid.models.RegisterRequest
import com.pab.tixid.models.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<User>>

    @POST("register.php")
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse<Any>>

    // Movie endpoints for users
    @GET("movies.php")
    suspend fun getMovies(@Query("status") status: String? = null): Response<ApiResponse<Map<String, List<Movie>>>>

    // Admin movie management endpoints
    @POST("admin_movies.php")
    suspend fun addMovie(@Body request: MovieRequest): Response<ApiResponse<Any>>

    @HTTP(method = "PUT", path = "admin_movies.php", hasBody = true)
    suspend fun updateMovie(@Body request: MovieRequest): Response<ApiResponse<Any>>

    @HTTP(method = "DELETE", path = "admin_movies.php", hasBody = true)
    suspend fun deleteMovie(@Body request: MovieRequest): Response<ApiResponse<Any>>

    @POST("admin_movies.php")
    suspend fun getAdminMovies(@Body request: MovieRequest): Response<ApiResponse<Map<String, List<Movie>>>>

    // Admin management endpoints
    @POST("manage_admins.php")
    suspend fun manageAdmins(@Body request: AdminRequest): Response<ApiResponse<Map<String, List<Admin>>>>
}

