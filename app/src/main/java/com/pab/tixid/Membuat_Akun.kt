package com.pab.tixid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class Membuat_Akun : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)

        val btnDaftar = findViewById<Button>(R.id.btn_daftar)
        val btnKembali = findViewById<ImageView>(R.id.btn_kembali)

        btnDaftar.setOnClickListener {
            val intent = Intent(this, MasukActivity::class.java)
            startActivity(intent)
        }

        btnKembali.setOnClickListener {
            finish()
        }
    }
}