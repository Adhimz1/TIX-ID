package com.pab.tixid

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.pab.tixid.databinding.ActivityPembayaranBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class PembayaranActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPembayaranBinding
    private var selectedPaymentMethod: String = ""
    private val SERVICE_FEE = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembayaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent
        val movieTitle = intent.getStringExtra("MOVIE_TITLE") ?: "SPIDERMAN FAR FROM HOME"
        val cinemaName = intent.getStringExtra("CINEMA_NAME") ?: "CSB XXI"
        val studio = intent.getStringExtra("STUDIO") ?: "STUDIO 1"
        val selectedDate = intent.getStringExtra("SELECTED_DATE") ?: "01 November"
        val selectedTime = intent.getStringExtra("SELECTED_TIME") ?: "12:45"
        val selectedSeats = intent.getStringExtra("SELECTED_SEATS") ?: "D2,D3,D4"
        val ticketCount = intent.getIntExtra("TICKET_COUNT", 3)
        val ticketPrice = intent.getIntExtra("TICKET_PRICE", 60000)

        setupUI(movieTitle, cinemaName, studio, selectedDate, selectedTime, selectedSeats, ticketCount, ticketPrice)
        setupClickListeners()
    }

    private fun setupUI(
        movieTitle: String,
        cinemaName: String,
        studio: String,
        selectedDate: String,
        selectedTime: String,
        selectedSeats: String,
        ticketCount: Int,
        ticketPrice: Int
    ) {
        // Set movie info
        binding.tvMovieTitle.text = movieTitle
        binding.tvCinemaLocation.text = "$cinemaName, $studio"

        // Format date
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", Locale("id", "ID"))
        val formattedDate = try {
            val inputFormat = SimpleDateFormat("dd MMMM", Locale("id", "ID"))
            val date = inputFormat.parse(selectedDate)
            val calendar = Calendar.getInstance()
            calendar.time = date ?: Date()
            calendar.set(Calendar.YEAR, 2025)
            val timeFormat = SimpleDateFormat("HH:mm", Locale("id", "ID"))
            val time = timeFormat.parse(selectedTime)
            if (time != null) {
                calendar.set(Calendar.HOUR_OF_DAY, time.hours)
                calendar.set(Calendar.MINUTE, time.minutes)
            }
            dateFormat.format(calendar.time)
        } catch (e: Exception) {
            "Senin, 01 November 2025, $selectedTime"
        }

        binding.tvMovieDatetime.text = formattedDate

        // Set transaction details
        binding.tvTicketCountLabel.text = "$ticketCount TIKET"
        binding.tvSeatNumbers.text = selectedSeats.replace(",", ", ")

        // Format currency properly
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        currencyFormat.maximumFractionDigits = 0

        val formattedPrice = currencyFormat.format(ticketPrice).replace("Rp", "Rp. ")
        val formattedServiceFee = currencyFormat.format(SERVICE_FEE).replace("Rp", "Rp. ")

        binding.tvSeatPriceDetail.text = "$formattedPrice x $ticketCount"
        binding.tvServiceFee.text = "$formattedServiceFee x $ticketCount"

        // Calculate and display total
        val totalTicketPrice = ticketPrice * ticketCount
        val totalServiceFee = SERVICE_FEE * ticketCount
        val totalPrice = totalTicketPrice + totalServiceFee
        val formattedTotal = currencyFormat.format(totalPrice).replace("Rp", "Rp. ")
        binding.tvTotalPrice.text = formattedTotal
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        // DANA payment method
        binding.llPaymentDana.setOnClickListener {
            selectPaymentMethod("DANA")
        }

        // GoPay payment method
        binding.llPaymentGopay.setOnClickListener {
            selectPaymentMethod("GOPAY")
        }

        // Payment button
        binding.btnPayment.setOnClickListener {
            if (selectedPaymentMethod.isNotEmpty()) {
                // TODO: Process payment
                // For now, just show a toast or navigate to success screen
            }
        }
    }

    private fun selectPaymentMethod(method: String) {
        selectedPaymentMethod = method

        when (method) {
            "DANA" -> {
                binding.radioDana.setBackgroundResource(R.drawable.radio_button_checked)
                binding.radioGopay.setBackgroundResource(R.drawable.radio_button_unchecked)
            }
            "GOPAY" -> {
                binding.radioDana.setBackgroundResource(R.drawable.radio_button_unchecked)
                binding.radioGopay.setBackgroundResource(R.drawable.radio_button_checked)
            }
        }
    }
}

