package com.pab.tixid

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class MasukActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnMasuk: Button
    private lateinit var btnKembali: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masuk)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)

        // Initialize views
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnMasuk = findViewById(R.id.btn_masuk)
        btnKembali = findViewById(R.id.btn_kembali)

        btnMasuk.setOnClickListener {
            loginUser()
        }

        btnKembali.setOnClickListener {
            finish()
        }

        // Debug: Tampilkan data tersimpan saat klik "Lupa Password"
        val tvLupaPassword = findViewById<TextView>(R.id.tv_lupa_password)
        tvLupaPassword.setOnClickListener {
            showSavedData()
        }
    }

    private fun showSavedData() {
        val savedName = sharedPreferences.getString("user_name", "")
        val savedEmail = sharedPreferences.getString("user_email", "")
        val savedPassword = sharedPreferences.getString("user_password", "")

        val message = if (savedEmail.isNullOrEmpty()) {
            "Belum ada akun terdaftar.\nSilakan daftar terlebih dahulu."
        } else {
            "Data Tersimpan:\n\n" +
            "Nama: $savedName\n" +
            "Email: $savedEmail\n" +
            "Password: $savedPassword\n\n" +
            "Gunakan data ini untuk login"
        }

        android.app.AlertDialog.Builder(this)
            .setTitle("Data Akun")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun loginUser() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        // 1. Check for hardcoded credentials first
        if (email == "ahmadadhim01@gmail.com" && password == "Adhim123") {
            // Successful hardcoded login
            sharedPreferences.edit().apply {
                putBoolean("is_logged_in", true)
                putString("user_name", "Adhim")
                putString("user_email", "ahmadadhim01@gmail.com")
                apply()
            }
            Toast.makeText(this, "Selamat datang kembali, Adhim!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return // Stop execution here
        }

        // 2. If hardcoded fails, check for registered user in SharedPreferences
        val savedEmail = sharedPreferences.getString("user_email", "")
        val savedPassword = sharedPreferences.getString("user_password", "")
        val userName = sharedPreferences.getString("user_name", "User")

        if (email == savedEmail && password == savedPassword && !savedEmail.isNullOrEmpty()) {
            // Successful login with registered credentials
            sharedPreferences.edit().putBoolean("is_logged_in", true).apply()
            Toast.makeText(this, "Selamat datang kembali, $userName!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return // Stop execution here
        }

        // 3. If both fail, show a generic error
        Toast.makeText(this, "Email atau password salah. Silakan coba lagi.", Toast.LENGTH_LONG).show()
    }
}