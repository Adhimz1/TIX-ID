package com.pab.tixid

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class DaftarActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var etNama: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etKonfirmasiPassword: TextInputEditText
    private lateinit var btnDaftar: Button
    private lateinit var btnKembali: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)

        // Initialize views
        etNama = findViewById(R.id.et_nama)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etKonfirmasiPassword = findViewById(R.id.et_konfirmasi_password)
        btnDaftar = findViewById(R.id.btn_daftar)
        btnKembali = findViewById(R.id.btn_kembali)

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

        // Simpan data user ke SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("user_name", nama)
        editor.putString("user_email", email)
        editor.putString("user_password", password)
        editor.putBoolean("is_logged_in", true)
        editor.apply()

        // Debug log untuk memastikan data tersimpan
        android.util.Log.d("DaftarActivity", "Registered - Name: $nama, Email: $email, Password: $password")

        Toast.makeText(this, "Pendaftaran berhasil! Selamat datang, $nama", Toast.LENGTH_SHORT).show()

        // Langsung ke HomeActivity setelah berhasil daftar
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}