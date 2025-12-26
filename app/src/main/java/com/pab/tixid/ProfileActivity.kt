package com.pab.tixid

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.pab.tixid.databinding.ActivityProfileBinding
import com.pab.tixid.utils.UserPreferences
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userPreferences: UserPreferences
    private var currentPhotoUri: Uri? = null

    // Gallery picker launcher
    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            handleImageSelection(it)
        }
    }

    // Camera launcher
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            currentPhotoUri?.let {
                handleImageSelection(it)
            }
        }
    }

    // Permission launcher
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
        if (cameraGranted) {
            openCamera()
        } else {
            Toast.makeText(this, "Izin kamera diperlukan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences and UserPreferences
        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)
        userPreferences = UserPreferences(this)

        // Set selected item in BottomNavigationView
        binding.bottomNav.selectedItemId = R.id.nav_akun

        // Load user data
        loadUserData()

        // Load saved profile image
        loadProfileImage()

        setupClickListeners()
        setupBottomNavigation()
    }

    private fun loadUserData() {
        // Load from UserPreferences (DataStore)
        lifecycleScope.launch {
            userPreferences.userFlow.collect { user ->
                if (user != null) {
                    binding.tvUserName.text = user.name
                    binding.tvUserEmail.text = user.email
                } else {
                    // Fallback to old SharedPreferences if exists
                    val userName = sharedPreferences.getString("user_name", "User") ?: "User"
                    val userEmail = sharedPreferences.getString("user_email", "email@example.com") ?: "email@example.com"
                    binding.tvUserName.text = userName
                    binding.tvUserEmail.text = userEmail
                }
            }
        }
    }

    private fun loadProfileImage() {
        val savedImageUri = sharedPreferences.getString("profile_image_uri", null)
        savedImageUri?.let {
            try {
                binding.ivProfilePicture.setImageURI(Uri.parse(it))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun saveProfileImageUri(uriString: String) {
        sharedPreferences.edit().apply {
            putString("profile_image_uri", uriString)
            apply()
        }
    }

    private fun setupClickListeners() {
        // Ganti Profil - Show dialog
        binding.llGantiProfil.setOnClickListener {
            showProfileOptionsDialog()
        }

        // Edit Profile Icon - Also shows dialog
        binding.ivEditProfile.setOnClickListener {
            showProfileOptionsDialog()
        }

        // Notifikasi
        binding.llNotifikasi.setOnClickListener {
            Toast.makeText(this, "Notifikasi clicked", Toast.LENGTH_SHORT).show()
        }

        // Film Saya
        binding.llFilmSaya.setOnClickListener {
            val intent = Intent(this, FilmSayaActivity::class.java)
            startActivity(intent)
        }

        // Keluar (Logout)
        binding.btnKeluar.setOnClickListener {
            logout()
        }
    }

    private fun showProfileOptionsDialog() {
        val options = arrayOf("Buka Kamera", "Pilih dari Galeri", "Hapus Foto Profil")

        AlertDialog.Builder(this)
            .setTitle("Ganti Foto Profil")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> checkCameraPermissionAndOpen()
                    1 -> openGallery()
                    2 -> deleteProfilePhoto()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun checkCameraPermissionAndOpen() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            else -> {
                permissionLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA)
                )
            }
        }
    }

    private fun openCamera() {
        try {
            val photoFile = createImageFile()
            currentPhotoUri = FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                photoFile
            )
            cameraLauncher.launch(currentPhotoUri)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Gagal membuka kamera: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "PROFILE_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun handleImageSelection(uri: Uri) {
        try {
            // Try to take persistable permission
            try {
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) {
                // If we can't get persistent permission, that's okay
                e.printStackTrace()
            }

            // Save URI to SharedPreferences
            saveProfileImageUri(uri.toString())

            // Update image view
            binding.ivProfilePicture.setImageURI(uri)

            Toast.makeText(this, "Foto profil berhasil diubah", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Gagal memuat gambar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteProfilePhoto() {
        AlertDialog.Builder(this)
            .setTitle("Hapus Foto Profil")
            .setMessage("Apakah Anda yakin ingin menghapus foto profil?")
            .setPositiveButton("Hapus") { _, _ ->
                // Remove from SharedPreferences
                sharedPreferences.edit().remove("profile_image_uri").apply()

                // Reset to default
                binding.ivProfilePicture.setImageResource(R.drawable.white_circle)

                Toast.makeText(this, "Foto profil dihapus", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun logout() {
        AlertDialog.Builder(this)
            .setTitle("Keluar")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                lifecycleScope.launch {
                    // Clear user session using UserPreferences
                    userPreferences.clearUser()

                    // Also clear old SharedPreferences for backward compatibility
                    sharedPreferences.edit().clear().apply()

                    Toast.makeText(this@ProfileActivity, "Berhasil keluar", Toast.LENGTH_SHORT).show()

                    // Navigate to welcome screen
                    val intent = Intent(this@ProfileActivity, SelamatDatang::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun setupBottomNavigation() {
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_bioskop -> {
                    val intent = Intent(this, BioskopActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_tiket -> {
                    Toast.makeText(this, "Tiket coming soon", Toast.LENGTH_SHORT).show()
                    false
                }
                R.id.nav_akun -> true
                else -> false
            }
        }
    }
}

