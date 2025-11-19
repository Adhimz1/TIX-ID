package com.pab.tixid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pab.tixid.databinding.ActivityDetailSpidermanBinding

class DetailSpidermanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSpidermanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSpidermanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button
        binding.ivBack.setOnClickListener {
            finish()
        }

        // Share button
        binding.ivShare.setOnClickListener {
            // Implementasi share functionality jika diperlukan
        }

        // Nonton Yuk button
        binding.btnNontonYuk.setOnClickListener {
            val intent = android.content.Intent(this, JadwalSpidermanActivity::class.java)
            startActivity(intent)
        }
    }
}

