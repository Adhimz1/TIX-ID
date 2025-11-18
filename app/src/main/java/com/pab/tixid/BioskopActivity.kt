package com.pab.tixid

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import com.pab.tixid.databinding.ActivityBioskopBinding

class BioskopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBioskopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBioskopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Customize SearchView hint text
        customizeSearchView(binding.searchView)

        // Set up "Lihat Lokasi" buttons
        setupLihatLokasiButtons()

        // Set selected item in BottomNavigationView
        binding.bottomNav.selectedItemId = R.id.nav_bioskop

        binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
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
                R.id.nav_bioskop -> true // Already on bioskop, do nothing
                else -> false
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

    private fun setupLihatLokasiButtons() {
        // CSB XXI CIREBON - Mall Cirebon Superblok
        binding.btnLihatLokasiCsb.setOnClickListener {
            val csbLocation = "Mall Cirebon Superblok Lt. 2, Jl. Dr. Cipto Mangunkusumo No. 26, Cirebon"
            openGoogleMaps(csbLocation)
        }

        // TRANSMART CIREBON CGV
        binding.btnLihatLokasiCgv.setOnClickListener {
            val cgvLocation = "Transmart Cirebon 1st floor, Jl. Raya Cipto Mangunkusumo No.234, Pekiringan, Kesambi, Cirebon"
            openGoogleMaps(cgvLocation)
        }
    }

    private fun openGoogleMaps(location: String) {
        // Create URI for Google Maps with search query
        val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(location)}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        // Check if Google Maps is installed
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            // If Google Maps is not installed, open in browser
            val browserIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode(location)}"))
            startActivity(browserIntent)
        }
    }
}
