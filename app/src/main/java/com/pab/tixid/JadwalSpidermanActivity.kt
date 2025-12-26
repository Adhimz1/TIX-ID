package com.pab.tixid

import android.content.Intent
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
    private var selectedDate: String? = "19 Nov"
    private var selectedCinema: String? = null
    private var selectedStudio: String? = null
    private var ticketPrice: Int = 40000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJadwalSpidermanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button
        binding.ivBack.setOnClickListener {
            finish()
        }

        // Setup date buttons
        setupDateButton(binding.btnDate1, "19 Nov")
        setupDateButton(binding.btnDate2, "20 Nov")
        setupDateButton(binding.btnDate3, "21 Nov")
        setupDateButton(binding.btnDate4, "22 Nov")
        setupDateButton(binding.btnDate5, "23 Nov")

        // Set default selected date
        selectedDateButton = binding.btnDate1

        // Setup time buttons for CGV
        setupTimeButton(binding.btnCgv1245, "12:45", "CSB XXI", 40000, "STUDIO 1")
        setupTimeButton(binding.btnCgv1430, "14:30", "CSB XXI", 40000, "STUDIO 1")
        setupTimeButton(binding.btnCgv1615, "16:15", "CSB XXI", 40000, "STUDIO 1")
        setupTimeButton(binding.btnCgv1800, "18:00", "CSB XXI", 40000, "STUDIO 2")
        setupTimeButton(binding.btnCgv1945, "19:45", "CSB XXI", 40000, "STUDIO 2")
        setupTimeButton(binding.btnCgv2030, "20:30", "CSB XXI", 40000, "STUDIO 2")

        // Setup time buttons for Transmart
        setupTimeButton(binding.btnTransmart1015, "10:15", "TRANSMART CIREBON CGV", 45000, "STUDIO 3")
        setupTimeButton(binding.btnTransmart1310, "13:10", "TRANSMART CIREBON CGV", 45000, "STUDIO 3")
        setupTimeButton(binding.btnTransmart1505, "15:05", "TRANSMART CIREBON CGV", 45000, "STUDIO 4")
        setupTimeButton(binding.btnTransmart1710, "17:10", "TRANSMART CIREBON CGV", 45000, "STUDIO 4")
        setupTimeButton(binding.btnTransmart1945, "19:45", "TRANSMART CIREBON CGV", 45000, "STUDIO 5")
        setupTimeButton(binding.btnTransmart2030, "20:30", "TRANSMART CIREBON CGV", 45000, "STUDIO 5")

        // Beli Tiket button
        binding.btnBeliTiket.setOnClickListener {
            if (selectedTime == null || selectedCinema == null) {
                Toast.makeText(
                    this,
                    "Silakan pilih jadwal terlebih dahulu!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(this, PilihBangkuActivity::class.java).apply {
                    putExtra("MOVIE_TITLE", "SPIDERMAN FAR FROM HOME")
                    putExtra("CINEMA_NAME", selectedCinema)
                    putExtra("STUDIO", selectedStudio ?: "STUDIO 1")
                    putExtra("SELECTED_DATE", selectedDate)
                    putExtra("SELECTED_TIME", selectedTime)
                    putExtra("TICKET_PRICE", ticketPrice)
                }
                startActivity(intent)
            }
        }
    }

    private fun setupDateButton(button: MaterialButton, date: String) {
        button.setOnClickListener {
            // Reset previous selected date button
            selectedDateButton?.let { prevButton ->
                prevButton.backgroundTintList = null
            }

            // Set new selected date button
            selectedDateButton = button
            selectedDate = date

            // Apply purple background to selected button
            button.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#7700FF"))
        }
    }

    private fun setupTimeButton(button: MaterialButton, time: String, cinema: String, price: Int, studio: String) {
        button.setOnClickListener {
            // Reset previous selected button
            selectedTimeButton?.let { prevButton ->
                prevButton.backgroundTintList = null
                prevButton.setTextColor(Color.WHITE)
            }

            // Set new selected button
            selectedTimeButton = button
            selectedTime = time
            selectedCinema = cinema
            selectedStudio = studio
            ticketPrice = price

            // Apply purple background to selected button
            button.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#7700FF"))
            button.setTextColor(Color.WHITE)
        }
    }
}
