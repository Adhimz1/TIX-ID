package com.pab.tixid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SelamatDatang : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selamat_datang)

        val btnMasuk = findViewById<Button>(R.id.btn_masuk_welcome)
        val btnDaftar = findViewById<Button>(R.id.btn_daftar_welcome)

        btnMasuk.setOnClickListener {
            val intent = Intent(this, MasukActivity::class.java)
            startActivity(intent)
        }

        btnDaftar.setOnClickListener {
            val intent = Intent(this, Membuat_Akun::class.java)
            startActivity(intent)
        }
    }
}