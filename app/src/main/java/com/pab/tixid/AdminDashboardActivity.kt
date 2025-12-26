package com.pab.tixid

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pab.tixid.adapters.AdminViewPagerAdapter
import com.pab.tixid.models.Movie
import com.pab.tixid.utils.UserPreferences
import kotlinx.coroutines.launch

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var userPreferences: UserPreferences
    private lateinit var tvAdminName: TextView
    private lateinit var btnLogout: ImageView
    private lateinit var btnManageAdmins: ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var fabAddMovie: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        userPreferences = UserPreferences(this)

        // Initialize views
        tvAdminName = findViewById(R.id.tv_admin_name)
        btnLogout = findViewById(R.id.btn_logout)
        btnManageAdmins = findViewById(R.id.btn_manage_admins)
        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
        fabAddMovie = findViewById(R.id.fab_add_movie)

        // Load user info
        lifecycleScope.launch {
            userPreferences.userFlow.collect { user ->
                if (user != null) {
                    tvAdminName.text = user.name
                } else {
                    // User logged out, return to login
                    navigateToLogin()
                }
            }
        }

        // Setup ViewPager
        setupViewPager()

        // Setup listeners
        btnLogout.setOnClickListener {
            showLogoutDialog()
        }

        btnManageAdmins.setOnClickListener {
            val intent = Intent(this, ManageAdminsActivity::class.java)
            startActivity(intent)
        }

        fabAddMovie.setOnClickListener {
            val intent = Intent(this, AddEditMovieActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewPager() {
        val adapter = AdminViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Sedang Tayang"
                1 -> "Segera Datang"
                else -> ""
            }
        }.attach()
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                logout()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    private fun logout() {
        lifecycleScope.launch {
            userPreferences.clearUser()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, SelamatDatang::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun editMovie(movie: Movie) {
        val intent = Intent(this, AddEditMovieActivity::class.java)
        intent.putExtra("movie_id", movie.id)
        intent.putExtra("movie_title", movie.title)
        intent.putExtra("movie_poster_url", movie.poster_url)
        intent.putExtra("movie_synopsis", movie.synopsis)
        intent.putExtra("movie_youtube_url", movie.youtube_url)
        intent.putExtra("movie_genre", movie.genre)
        intent.putExtra("movie_duration", movie.duration)
        intent.putExtra("movie_rating", movie.rating)
        intent.putExtra("movie_release_date", movie.release_date)
        intent.putExtra("movie_status", movie.status)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        // Refresh movie lists when returning from add/edit
        refreshMovieLists()
    }

    private fun refreshMovieLists() {
        val adapter = viewPager.adapter as? AdminViewPagerAdapter
        adapter?.refreshFragments()
    }
}

