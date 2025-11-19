package com.pab.tixid

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.pab.tixid.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)

        // Load profile image
        loadProfileImage()

        // Get user name from SharedPreferences
        val userName = sharedPreferences.getString("user_name", "User") ?: "User"

        // Change color of user name
        val fullText = "welcome $userName 👋"
        val spannable = SpannableString(fullText)
        val start = fullText.indexOf(userName)
        val end = start + userName.length
        spannable.setSpan(ForegroundColorSpan(Color.parseColor("#5D21D1")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvWelcome.text = spannable

        // Customize SearchView hint text
        customizeSearchView(binding.searchView)

        // Sedang Tayang RecyclerView
        val moviesSedangTayang = listOf(
            Movie("Avengers: Endgame", R.drawable.poster_avengers),
            Movie("Spider-Man: Far From Home", R.drawable.poster_spiderman),
            Movie("WALL-E", R.drawable.poster_walle)
        )

        binding.rvSedangTayang.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSedangTayang.adapter = MovieAdapter(moviesSedangTayang) { movie ->
            if (movie.title == "Mulan") {
                val intent = Intent(this, DetailMulanActivity::class.java)
                startActivity(intent)
            }
        }

        // Segera Hadir RecyclerView
        val moviesSegeraHadir = listOf(
            Movie("Harry Potter", R.drawable.poster_potter),
            Movie("Star Wars", R.drawable.poster_starwars),
            Movie("Mulan", R.drawable.poster_mulan)
        )

        binding.rvSegeraHadir.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSegeraHadir.adapter = MovieAdapter(moviesSegeraHadir) { movie ->
            when (movie.title) {
                "Mulan" -> {
                    val intent = Intent(this, DetailMulanActivity::class.java)
                    startActivity(intent)
                }
                "Harry Potter" -> {
                    val intent = Intent(this, DetailPotterActivity::class.java)
                    startActivity(intent)
                }
                "Star Wars" -> {
                    val intent = Intent(this, DetailStarWarsActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        binding.tvLihatSemuaTayang.setOnClickListener {
            val intent = Intent(this, SedangTayangActivity::class.java)
            startActivity(intent)
        }

        binding.tvLihatSemuaHadir.setOnClickListener {
            val intent = Intent(this, SegeraHadirActivity::class.java)
            startActivity(intent)
        }

        // Set selected item in BottomNavigationView
        binding.bottomNav.selectedItemId = R.id.nav_home

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_bioskop -> {
                    val intent = Intent(this, BioskopActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_akun -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_home -> true // Already on home, do nothing
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadProfileImage()
    }

    private fun loadProfileImage() {
        val savedImageUri = sharedPreferences.getString("profile_image_uri", null)
        savedImageUri?.let {
            try {
                val uri = Uri.parse(it)
                // Add this line to take persistable URI permission
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                binding.ivProfile.setImageURI(uri)
            } catch (e: SecurityException) {
                e.printStackTrace()
                // Handle the case where permission is lost (e.g., show a default image)
                binding.ivProfile.setImageResource(R.drawable.ic_akun)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun customizeSearchView(searchView: SearchView) {
        val searchTextView = searchView.findViewById<android.widget.TextView>(
            searchView.context.resources.getIdentifier("android:id/search_src_text", null, null)
        )
        searchTextView?.setHintTextColor(Color.parseColor("#80FFFFFF")) // 50% opacity white
        searchTextView?.textSize = 14f
    }
}
