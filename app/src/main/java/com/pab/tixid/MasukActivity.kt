package com.pab.tixid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MasukActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masuk)

        val btnMasuk = findViewById<Button>(R.id.btn_masuk)
        val btnKembali = findViewById<ImageView>(R.id.btn_kembali)

        btnMasuk.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        btnKembali.setOnClickListener {
            finish()
        }
    }
}