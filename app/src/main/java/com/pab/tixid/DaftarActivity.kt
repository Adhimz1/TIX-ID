package com.pab.tixid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.pab.tixid.api.RetrofitClient
import com.pab.tixid.models.RegisterRequest
import kotlinx.coroutines.launch

class DaftarActivity : AppCompatActivity() {

    private lateinit var etNama: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etKonfirmasiPassword: TextInputEditText
    private lateinit var btnDaftar: Button
    private lateinit var btnKembali: ImageView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)

        // Initialize views
        etNama = findViewById(R.id.et_nama)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etKonfirmasiPassword = findViewById(R.id.et_konfirmasi_password)
        btnDaftar = findViewById(R.id.btn_daftar)
        btnKembali = findViewById(R.id.btn_kembali)

        // Add ProgressBar programmatically or add to layout
        progressBar = ProgressBar(this)
        progressBar.visibility = View.GONE

        // Set click listeners
        btnDaftar.setOnClickListener {
            registerUser()
        }

        btnKembali.setOnClickListener {
            finish()
        }
    }

    private fun registerUser() {
        val nama = etNama.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val konfirmasiPassword = etKonfirmasiPassword.text.toString()

        // Validasi input
        if (nama.isEmpty()) {
            etNama.error = "Nama tidak boleh kosong"
            etNama.requestFocus()
            return
        }

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

        if (password.length < 6) {
            etPassword.error = "Password minimal 6 karakter"
            etPassword.requestFocus()
            return
        }

        if (konfirmasiPassword.isEmpty()) {
            etKonfirmasiPassword.error = "Konfirmasi password tidak boleh kosong"
            etKonfirmasiPassword.requestFocus()
            return
        }

        if (password != konfirmasiPassword) {
            etKonfirmasiPassword.error = "Password tidak sama"
            etKonfirmasiPassword.requestFocus()
            return
        }

        // Show loading
        btnDaftar.isEnabled = false
        btnDaftar.text = "Mendaftar..."

        // Call API
        lifecycleScope.launch {
            try {
                android.util.Log.d("DaftarActivity", "Sending register request - Name: $nama, Email: $email")

                val response = RetrofitClient.apiService.register(
                    RegisterRequest(
                        name = nama,
                        email = email,
                        password = password,
                        phone = "" // You can add phone field in the form if needed
                    )
                )

                btnDaftar.isEnabled = true
                btnDaftar.text = "Daftar"

                android.util.Log.d("DaftarActivity", "Response code: ${response.code()}")
                android.util.Log.d("DaftarActivity", "Response body: ${response.body()}")
                android.util.Log.d("DaftarActivity", "Response error: ${response.errorBody()?.string()}")

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.success == true) {
                        Toast.makeText(
                            this@DaftarActivity,
                            "Pendaftaran berhasil! Silakan login",
                            Toast.LENGTH_SHORT
                        ).show()

                        android.util.Log.d("DaftarActivity", "Registration successful, navigating to login")

                        // Navigate to Login
                        val intent = Intent(this@DaftarActivity, MasukActivity::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                        finish()
                    } else {
                        val errorMsg = apiResponse?.message ?: "Registrasi gagal"
                        android.util.Log.e("DaftarActivity", "Registration failed: $errorMsg")
                        Toast.makeText(
                            this@DaftarActivity,
                            errorMsg,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    android.util.Log.e("DaftarActivity", "API Error: ${response.code()} - $errorBody")
                    Toast.makeText(
                        this@DaftarActivity,
                        "Error: ${response.code()} - ${response.message()}\n$errorBody",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                btnDaftar.isEnabled = true
                btnDaftar.text = "Daftar"
                android.util.Log.e("DaftarActivity", "Exception during registration", e)
                Toast.makeText(
                    this@DaftarActivity,
                    "Error koneksi: ${e.message}\nPastikan server API sudah berjalan",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

