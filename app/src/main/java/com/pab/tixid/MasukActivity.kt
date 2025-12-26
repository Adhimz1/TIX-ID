package com.pab.tixid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.pab.tixid.api.RetrofitClient
import com.pab.tixid.models.LoginRequest
import com.pab.tixid.utils.UserPreferences
import kotlinx.coroutines.launch

class MasukActivity : AppCompatActivity() {

    private lateinit var userPreferences: UserPreferences
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnMasuk: Button
    private lateinit var btnKembali: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masuk)

        // Initialize UserPreferences
        userPreferences = UserPreferences(this)

        // Check if already logged in
        lifecycleScope.launch {
            userPreferences.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    navigateToHome()
                }
            }
        }

        // Initialize views
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnMasuk = findViewById(R.id.btn_masuk)
        btnKembali = findViewById(R.id.btn_kembali)

        // Pre-fill email if coming from registration
        val emailFromRegister = intent.getStringExtra("email")
        if (!emailFromRegister.isNullOrEmpty()) {
            etEmail.setText(emailFromRegister)
        }

        btnMasuk.setOnClickListener {
            loginUser()
        }

        btnKembali.setOnClickListener {
            finish()
        }
    }

    private fun loginUser() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        // Validasi input
        if (email.isEmpty()) {
            etEmail.error = "Email tidak boleh kosong"
            etEmail.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Format email tidak valid"
            etEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {
            etPassword.error = "Password tidak boleh kosong"
            etPassword.requestFocus()
            return
        }

        // Show loading
        btnMasuk.isEnabled = false
        btnMasuk.text = "Masuk..."

        // Call API
        lifecycleScope.launch {
            try {
                android.util.Log.d("MasukActivity", "Attempting login - Email: $email")

                val response = RetrofitClient.apiService.login(
                    LoginRequest(email, password)
                )

                btnMasuk.isEnabled = true
                btnMasuk.text = "Masuk"

                android.util.Log.d("MasukActivity", "Response code: ${response.code()}")
                android.util.Log.d("MasukActivity", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    android.util.Log.d("MasukActivity", "API Response: success=${apiResponse?.success}, data=${apiResponse?.data}")

                    if (apiResponse?.success == true && apiResponse.data != null) {
                        // Save user session
                        userPreferences.saveUser(apiResponse.data)

                        Toast.makeText(
                            this@MasukActivity,
                            "Selamat datang, ${apiResponse.data.name}!",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Check if user is admin
                        if (apiResponse.data.role == "admin") {
                            navigateToAdmin()
                        } else {
                            navigateToHome()
                        }
                    } else {
                        val errorMsg = apiResponse?.message ?: "Email atau password salah"
                        android.util.Log.e("MasukActivity", "Login failed: $errorMsg")
                        Toast.makeText(
                            this@MasukActivity,
                            errorMsg,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    android.util.Log.e("MasukActivity", "API Error: ${response.code()} - $errorBody")
                    Toast.makeText(
                        this@MasukActivity,
                        "Error: ${response.code()} - ${response.message()}\n$errorBody",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                btnMasuk.isEnabled = true
                btnMasuk.text = "Masuk"
                android.util.Log.e("MasukActivity", "Exception during login", e)
                Toast.makeText(
                    this@MasukActivity,
                    "Error koneksi: ${e.message}\nPastikan server API sudah berjalan",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun navigateToAdmin() {
        val intent = Intent(this, AdminDashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

