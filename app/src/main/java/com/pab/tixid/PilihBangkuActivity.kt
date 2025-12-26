package com.pab.tixid

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.pab.tixid.databinding.ActivityPilihBangkuBinding
import java.text.NumberFormat
import java.util.Locale

class PilihBangkuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPilihBangkuBinding

    private val selectedSeats = mutableListOf<String>()
    private var ticketPrice: Int = 40000
    private var cinemaName: String = ""
    private var selectedDate: String = ""
    private var selectedTime: String = ""
    private var movieTitle: String = ""
    private var studio: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilihBangkuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent
        cinemaName = intent.getStringExtra("CINEMA_NAME") ?: "CGV XXI CIREBON"
        selectedDate = intent.getStringExtra("SELECTED_DATE") ?: "19 Nov"
        selectedTime = intent.getStringExtra("SELECTED_TIME") ?: "12:45"
        ticketPrice = intent.getIntExtra("TICKET_PRICE", 40000)
        movieTitle = intent.getStringExtra("MOVIE_TITLE") ?: "SPIDERMAN FAR FROM HOME"
        studio = intent.getStringExtra("STUDIO") ?: "STUDIO 1"

        // Set header info
        binding.tvCinemaName.text = cinemaName
        binding.tvDateTime.text = "$selectedDate | $selectedTime"

        // Setup back button
        binding.ivBack.setOnClickListener {
            finish()
        }

        // Setup seat click listeners
        setupSeatClickListeners()

        // Setup order summary button
        binding.btnOrderSummary.setOnClickListener {
            if (selectedSeats.isNotEmpty()) {
                navigateToPembayaran()
            }
        }
    }

    private fun setupSeatClickListeners() {
        val seatContainer = binding.llSeatContainer

        // Iterate through all rows in the seat container
        for (i in 0 until seatContainer.childCount) {
            val row = seatContainer.getChildAt(i)
            if (row is LinearLayout) {
                // Iterate through all seats in the row
                for (j in 0 until row.childCount) {
                    val seat = row.getChildAt(j)
                    if (seat is TextView && seat !is Space) {
                        val seatName = seat.text.toString()
                        seat.setOnClickListener {
                            toggleSeatSelection(seat, seatName)
                        }
                    }
                }
            }
        }
    }

    private fun toggleSeatSelection(seatView: TextView, seatName: String) {
        if (selectedSeats.contains(seatName)) {
            // Deselect seat
            selectedSeats.remove(seatName)
            seatView.setBackgroundResource(R.drawable.seat_available_border)
        } else {
            // Select seat
            selectedSeats.add(seatName)
            seatView.setBackgroundResource(R.drawable.seat_selected)
        }

        // Update UI
        updateTotalPrice()
        updateSelectedSeatsDisplay()
        updateOrderButtonState()
    }

    private fun updateTotalPrice() {
        val totalPrice = selectedSeats.size * ticketPrice
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        formatter.maximumFractionDigits = 0
        binding.tvTotalPrice.text = formatter.format(totalPrice)
    }

    private fun updateSelectedSeatsDisplay() {
        if (selectedSeats.isEmpty()) {
            binding.tvSelectedSeats.text = "Kursi belum dipilih"
            binding.tvSelectedSeats.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        } else {
            // Sort seats alphabetically
            val sortedSeats = selectedSeats.sorted()
            binding.tvSelectedSeats.text = sortedSeats.joinToString(", ")
            binding.tvSelectedSeats.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        }
    }

    private fun updateOrderButtonState() {
        if (selectedSeats.isNotEmpty()) {
            // Change button to purple (pilihanmu color) when seats are selected
            binding.btnOrderSummary.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#7700FF"))
            binding.btnOrderSummary.strokeColor = ColorStateList.valueOf(Color.parseColor("#7700FF"))
        } else {
            // Reset button to default (black with purple stroke) when no seats selected
            binding.btnOrderSummary.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#000000"))
            binding.btnOrderSummary.strokeColor = ColorStateList.valueOf(Color.parseColor("#7700FF"))
        }
    }

    private fun navigateToPembayaran() {
        val intent = Intent(this, PembayaranActivity::class.java).apply {
            putExtra("MOVIE_TITLE", movieTitle)
            putExtra("CINEMA_NAME", cinemaName)
            putExtra("STUDIO", studio)
            putExtra("SELECTED_DATE", selectedDate)
            putExtra("SELECTED_TIME", selectedTime)
            putExtra("SELECTED_SEATS", selectedSeats.sorted().joinToString(","))
            putExtra("TICKET_COUNT", selectedSeats.size)
            putExtra("TICKET_PRICE", ticketPrice)
        }
        startActivity(intent)
    }
}