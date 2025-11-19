package com.pab.tixid

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.pab.tixid.databinding.ActivityJadwalSpidermanBinding

class JadwalSpidermanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJadwalSpidermanBinding
    private var selectedTimeButton: MaterialButton? = null
    private var selectedDateButton: MaterialButton? = null
    private var selectedTime: String? = null
    private var selectedCinema: String? = null
    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJadwalSpidermanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button
        binding.ivBack.setOnClickListener {
            finish()
        }

        // Setup date buttons
        setupDateButton(binding.btnDate1, "Rabu, 19 Nov")
        setupDateButton(binding.btnDate2, "Kamis, 20 Nov")
        setupDateButton(binding.btnDate3, "Jumat, 21 Nov")
        setupDateButton(binding.btnDate4, "Sabtu, 22 Nov")
        setupDateButton(binding.btnDate5, "Minggu, 23 Nov")

        // Set default selected date (first date)
        selectDateButton(binding.btnDate1, "Rabu, 19 Nov")

        // Setup time buttons for CGV
        setupTimeButton(binding.btnCgv1245, "12:45", "CGV XXI CIREBON")
        setupTimeButton(binding.btnCgv1430, "14:30", "CGV XXI CIREBON")
        setupTimeButton(binding.btnCgv1615, "16:15", "CGV XXI CIREBON")
        setupTimeButton(binding.btnCgv1800, "18:00", "CGV XXI CIREBON")
        setupTimeButton(binding.btnCgv1945, "19:45", "CGV XXI CIREBON")
        setupTimeButton(binding.btnCgv2030, "20:30", "CGV XXI CIREBON")

        // Setup time buttons for Transmart
        setupTimeButton(binding.btnTransmart1015, "10:15", "TRANSMART CIREBON CGV")
        setupTimeButton(binding.btnTransmart1310, "13:10", "TRANSMART CIREBON CGV")
        setupTimeButton(binding.btnTransmart1505, "15:05", "TRANSMART CIREBON CGV")
        setupTimeButton(binding.btnTransmart1710, "17:10", "TRANSMART CIREBON CGV")
        setupTimeButton(binding.btnTransmart1945, "19:45", "TRANSMART CIREBON CGV")
        setupTimeButton(binding.btnTransmart2030, "20:30", "TRANSMART CIREBON CGV")

        // Beli Tiket button
        binding.btnBeliTiket.setOnClickListener {
            if (selectedTime == null || selectedCinema == null) {
                // Show warning if no time selected
                Toast.makeText(
                    this,
                    "Silakan pilih jadwal terlebih dahulu!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Proceed with ticket purchase
                Toast.makeText(
                    this,
                    "Anda memilih $selectedCinema\n$selectedDate jam $selectedTime",
                    Toast.LENGTH_LONG
                ).show()
                // Implementasi untuk proses pembelian tiket
                // Bisa ke halaman pemilihan kursi atau pembayaran
            }
        }
    }

    private fun setupDateButton(button: MaterialButton, date: String) {
        button.setOnClickListener {
            selectDateButton(button, date)
        }
    }

    private fun selectDateButton(button: MaterialButton, date: String) {
        // Reset previous selected date button
        selectedDateButton?.let { prevButton ->
            prevButton.backgroundTintList = null
            prevButton.setTextColor(Color.WHITE)
        }

        // Set new selected date button
        selectedDateButton = button
        selectedDate = date

        // Apply purple background to selected date button
        button.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#7700FF"))
        button.setTextColor(Color.WHITE)

        // Note: In real app, you would fetch different schedules for different dates
        // For now, we show the same schedule
        Toast.makeText(this, "Jadwal untuk $date", Toast.LENGTH_SHORT).show()
    }

    private fun setupTimeButton(button: MaterialButton, time: String, cinema: String) {
        button.setOnClickListener {
            // Reset previous selected time button
            selectedTimeButton?.let { prevButton ->
                prevButton.backgroundTintList = null
                prevButton.setTextColor(Color.WHITE)
            }

            // Set new selected time button
            selectedTimeButton = button
            selectedTime = time
            selectedCinema = cinema

            // Apply purple background to selected button
            button.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#7700FF"))
            button.setTextColor(Color.WHITE)
        }
    }
}

