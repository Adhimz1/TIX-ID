package com.pab.tixid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.pab.tixid.api.RetrofitClient
import com.pab.tixid.models.MovieRequest
import com.pab.tixid.utils.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File

class AddEditMovieActivity : AppCompatActivity() {

    private lateinit var userPreferences: UserPreferences
    private lateinit var tvTitle: TextView
    private lateinit var btnBack: ImageView
    private lateinit var ivPosterPreview: ImageView
    private lateinit var btnUploadPoster: Button
    private lateinit var etTitle: TextInputEditText
    private lateinit var etPosterUrl: TextInputEditText
    private lateinit var etSynopsis: TextInputEditText
    private lateinit var etYoutubeUrl: TextInputEditText
    private lateinit var etGenre: TextInputEditText
    private lateinit var etDuration: TextInputEditText
    private lateinit var etRating: TextInputEditText
    private lateinit var etReleaseDate: TextInputEditText
    private lateinit var rgStatus: RadioGroup
    private lateinit var rbNowShowing: RadioButton
    private lateinit var rbComingSoon: RadioButton
    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button

    private var movieId: Int? = null
    private var isEditMode = false
    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            ivPosterPreview.visibility = View.VISIBLE
            Glide.with(this)
                .load(it)
                .into(ivPosterPreview)
            
            // Generate URL dari image name
            val fileName = "poster_${System.currentTimeMillis()}.jpg"
            etPosterUrl.setText("http://192.168.1.2/tix_id_api/uploads/$fileName")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_movie)

        userPreferences = UserPreferences(this)

        // Initialize views
        tvTitle = findViewById(R.id.tv_title)
        btnBack = findViewById(R.id.btn_back)
        ivPosterPreview = findViewById(R.id.iv_poster_preview)
        btnUploadPoster = findViewById(R.id.btn_upload_poster)
        etTitle = findViewById(R.id.et_title)
        etPosterUrl = findViewById(R.id.et_poster_url)
        etSynopsis = findViewById(R.id.et_synopsis)
        etYoutubeUrl = findViewById(R.id.et_youtube_url)
        etGenre = findViewById(R.id.et_genre)
        etDuration = findViewById(R.id.et_duration)
        etRating = findViewById(R.id.et_rating)
        etReleaseDate = findViewById(R.id.et_release_date)
        rgStatus = findViewById(R.id.rg_status)
        rbNowShowing = findViewById(R.id.rb_now_showing)
        rbComingSoon = findViewById(R.id.rb_coming_soon)
        btnSave = findViewById(R.id.btn_save)
        btnDelete = findViewById(R.id.btn_delete)

        // Check if edit mode
        movieId = intent.getIntExtra("movie_id", -1).takeIf { it != -1 }
        isEditMode = movieId != null

        if (isEditMode) {
            tvTitle.text = "Edit Film"
            btnDelete.visibility = View.VISIBLE
            loadMovieData()
        } else {
            tvTitle.text = "Tambah Film"
            btnDelete.visibility = View.GONE
        }

        // Setup listeners
        btnBack.setOnClickListener {
            finish()
        }

        btnUploadPoster.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        btnSave.setOnClickListener {
            saveMovie()
        }

        btnDelete.setOnClickListener {
            showDeleteDialog()
        }
    }

    private fun loadMovieData() {
        etTitle.setText(intent.getStringExtra("movie_title"))
        etPosterUrl.setText(intent.getStringExtra("movie_poster_url"))
        etSynopsis.setText(intent.getStringExtra("movie_synopsis"))
        etYoutubeUrl.setText(intent.getStringExtra("movie_youtube_url"))
        etGenre.setText(intent.getStringExtra("movie_genre"))
        etDuration.setText(intent.getStringExtra("movie_duration"))
        etRating.setText(intent.getDoubleExtra("movie_rating", 0.0).toString())
        etReleaseDate.setText(intent.getStringExtra("movie_release_date"))

        val status = intent.getStringExtra("movie_status")
        if (status == "coming_soon") {
            rbComingSoon.isChecked = true
        } else {
            rbNowShowing.isChecked = true
        }
    }

    private fun saveMovie() {
        // Validate input
        val title = etTitle.text.toString().trim()
        val posterUrl = etPosterUrl.text.toString().trim()
        val synopsis = etSynopsis.text.toString().trim()
        val youtubeUrl = etYoutubeUrl.text.toString().trim()
        val genre = etGenre.text.toString().trim()
        val duration = etDuration.text.toString().trim()
        val ratingStr = etRating.text.toString().trim()
        val releaseDate = etReleaseDate.text.toString().trim()

        if (title.isEmpty()) {
            etTitle.error = "Judul tidak boleh kosong"
            etTitle.requestFocus()
            return
        }

        if (posterUrl.isEmpty()) {
            etPosterUrl.error = "URL poster tidak boleh kosong"
            etPosterUrl.requestFocus()
            return
        }

        if (synopsis.isEmpty()) {
            etSynopsis.error = "Sinopsis tidak boleh kosong"
            etSynopsis.requestFocus()
            return
        }

        val rating = ratingStr.toDoubleOrNull() ?: 0.0
        val status = if (rbComingSoon.isChecked) "coming_soon" else "now_showing"

        // Show loading
        btnSave.isEnabled = false
        btnSave.text = "Menyimpan..."

        lifecycleScope.launch {
            try {
                val user = userPreferences.userFlow.first()
                if (user == null) {
                    Toast.makeText(this@AddEditMovieActivity, "User not found", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val request = MovieRequest(
                    admin_email = user.email,
                    id = movieId,
                    title = title,
                    poster_url = posterUrl,
                    synopsis = synopsis,
                    youtube_url = youtubeUrl.ifEmpty { null },
                    genre = genre.ifEmpty { null },
                    duration = duration.ifEmpty { null },
                    rating = rating,
                    release_date = releaseDate.ifEmpty { null },
                    status = status
                )

                val response = if (isEditMode) {
                    RetrofitClient.apiService.updateMovie(request)
                } else {
                    RetrofitClient.apiService.addMovie(request)
                }

                btnSave.isEnabled = true
                btnSave.text = "Simpan Film"

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.success == true) {
                        Toast.makeText(
                            this@AddEditMovieActivity,
                            apiResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@AddEditMovieActivity,
                            apiResponse?.message ?: "Gagal menyimpan film",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@AddEditMovieActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                btnSave.isEnabled = true
                btnSave.text = "Simpan Film"
                Toast.makeText(
                    this@AddEditMovieActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(this)
            .setTitle("Hapus Film")
            .setMessage("Apakah Anda yakin ingin menghapus film ini?")
            .setPositiveButton("Hapus") { _, _ ->
                deleteMovie()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun deleteMovie() {
        if (movieId == null) return

        btnDelete.isEnabled = false
        btnDelete.text = "Menghapus..."

        lifecycleScope.launch {
            try {
                val user = userPreferences.userFlow.first()
                if (user == null) {
                    Toast.makeText(this@AddEditMovieActivity, "User not found", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val request = MovieRequest(
                    admin_email = user.email,
                    id = movieId
                )

                val response = RetrofitClient.apiService.deleteMovie(request)

                btnDelete.isEnabled = true
                btnDelete.text = "Hapus Film"

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.success == true) {
                        Toast.makeText(
                            this@AddEditMovieActivity,
                            apiResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@AddEditMovieActivity,
                            apiResponse?.message ?: "Gagal menghapus film",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@AddEditMovieActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                btnDelete.isEnabled = true
                btnDelete.text = "Hapus Film"
                Toast.makeText(
                    this@AddEditMovieActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

