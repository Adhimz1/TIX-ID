package com.pab.tixid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.pab.tixid.api.RetrofitClient
import com.pab.tixid.models.RegisterRequest
import kotlinx.coroutines.launch

class Membuat_Akun : AppCompatActivity() {

    private lateinit var etNama: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etKonfirmasiPassword: TextInputEditText
    private lateinit var btnDaftar: Button
    private lateinit var btnKembali: ImageView

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
                Log.d("Membuat_Akun", "Sending register request - Name: $nama, Email: $email")

                val response = RetrofitClient.apiService.register(
                    RegisterRequest(
                        name = nama,
                        email = email,
                        password = password,
                        phone = "" // Optional
                    )
                )

                btnDaftar.isEnabled = true
                btnDaftar.text = "Daftar"

                Log.d("Membuat_Akun", "Response code: ${response.code()}")
                Log.d("Membuat_Akun", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.success == true) {
                        Toast.makeText(
                            this@Membuat_Akun,
                            "Pendaftaran berhasil! Silakan login",
                            Toast.LENGTH_SHORT
                        ).show()

                        Log.d("Membuat_Akun", "Registration successful, navigating to login")

                        // Navigate to Login
                        val intent = Intent(this@Membuat_Akun, MasukActivity::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                        finish()
                    } else {
                        val errorMsg = apiResponse?.message ?: "Registrasi gagal"
                        Log.e("Membuat_Akun", "Registration failed: $errorMsg")
                        Toast.makeText(
                            this@Membuat_Akun,
                            errorMsg,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("Membuat_Akun", "API Error: ${response.code()} - $errorBody")
                    Toast.makeText(
                        this@Membuat_Akun,
                        "Error: ${response.code()}\n$errorBody",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                btnDaftar.isEnabled = true
                btnDaftar.text = "Daftar"
                Log.e("Membuat_Akun", "Exception during registration", e)
                Toast.makeText(
                    this@Membuat_Akun,
                    "Error koneksi: ${e.message}\nPastikan server API sudah berjalan dan IP sudah benar",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

