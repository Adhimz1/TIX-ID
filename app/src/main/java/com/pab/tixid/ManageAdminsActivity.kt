package com.pab.tixid

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.pab.tixid.adapters.AdminAdapter
import com.pab.tixid.api.RetrofitClient
import com.pab.tixid.models.Admin
import com.pab.tixid.models.AdminRequest
import com.pab.tixid.utils.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ManageAdminsActivity : AppCompatActivity() {

    private lateinit var userPreferences: UserPreferences
    private lateinit var rvAdmins: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmpty: TextView
    private lateinit var btnBack: ImageView
    private lateinit var btnAddAdmin: ImageView
    private lateinit var adapter: AdminAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_admins)

        userPreferences = UserPreferences(this)

        // Initialize views
        rvAdmins = findViewById(R.id.rv_admins)
        progressBar = findViewById(R.id.progress_bar)
        tvEmpty = findViewById(R.id.tv_empty)
        btnBack = findViewById(R.id.btn_back)
        btnAddAdmin = findViewById(R.id.btn_add_admin)

        // Setup RecyclerView
        adapter = AdminAdapter(
            onEdit = { admin -> showEditAdminDialog(admin) },
            onDelete = { admin -> showDeleteConfirmDialog(admin) }
        )
        rvAdmins.layoutManager = LinearLayoutManager(this)
        rvAdmins.adapter = adapter

        // Setup listeners
        btnBack.setOnClickListener { finish() }
        btnAddAdmin.setOnClickListener { showAddAdminDialog() }

        // Load admins
        loadAdmins()
    }

    private fun loadAdmins() {
        progressBar.visibility = View.VISIBLE
        tvEmpty.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val user = userPreferences.userFlow.first()
                if (user == null) {
                    Toast.makeText(this@ManageAdminsActivity, "User not found", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val request = AdminRequest(
                    admin_email = user.email,
                    action = "list"
                )

                val response = RetrofitClient.apiService.manageAdmins(request)
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.success == true) {
                        val admins = apiResponse.data?.get("admins") ?: emptyList()
                        adapter.updateAdmins(admins)

                        if (admins.isEmpty()) {
                            tvEmpty.visibility = View.VISIBLE
                        }
                    } else {
                        Toast.makeText(
                            this@ManageAdminsActivity,
                            apiResponse?.message ?: "Gagal memuat admin",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@ManageAdminsActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                progressBar.visibility = View.GONE
                Toast.makeText(
                    this@ManageAdminsActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showAddAdminDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_admin, null)
        val etName = dialogView.findViewById<TextInputEditText>(R.id.et_name)
        val etEmail = dialogView.findViewById<TextInputEditText>(R.id.et_email)
        val etPhone = dialogView.findViewById<TextInputEditText>(R.id.et_phone)
        val etPassword = dialogView.findViewById<TextInputEditText>(R.id.et_password)

        AlertDialog.Builder(this)
            .setTitle("Tambah Admin Baru")
            .setView(dialogView)
            .setPositiveButton("Tambah") { _, _ ->
                val name = etName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val phone = etPhone.text.toString().trim()
                val password = etPassword.text.toString().trim()

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Nama, email, dan password harus diisi", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                addAdmin(name, email, phone, password)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showEditAdminDialog(admin: Admin) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_admin, null)
        val etName = dialogView.findViewById<TextInputEditText>(R.id.et_name)
        val etEmail = dialogView.findViewById<TextInputEditText>(R.id.et_email)
        val etPhone = dialogView.findViewById<TextInputEditText>(R.id.et_phone)
        val etPassword = dialogView.findViewById<TextInputEditText>(R.id.et_password)

        // Pre-fill data
        etName.setText(admin.name)
        etEmail.setText(admin.email)
        etPhone.setText(admin.phone)

        AlertDialog.Builder(this)
            .setTitle("Edit Admin")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val name = etName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val phone = etPhone.text.toString().trim()
                val password = etPassword.text.toString().trim()

                updateAdmin(admin.id, name, email, phone, password)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showDeleteConfirmDialog(admin: Admin) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Admin")
            .setMessage("Apakah Anda yakin ingin menghapus admin ${admin.name}?")
            .setPositiveButton("Hapus") { _, _ ->
                deleteAdmin(admin.id)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun addAdmin(name: String, email: String, phone: String, password: String) {
        lifecycleScope.launch {
            try {
                val user = userPreferences.userFlow.first()
                if (user == null) {
                    Toast.makeText(this@ManageAdminsActivity, "User not found", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val request = AdminRequest(
                    admin_email = user.email,
                    action = "add",
                    name = name,
                    email = email,
                    phone = phone.ifEmpty { null },
                    password = password
                )

                val response = RetrofitClient.apiService.manageAdmins(request)

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.success == true) {
                        Toast.makeText(this@ManageAdminsActivity, "Admin berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                        loadAdmins()
                    } else {
                        Toast.makeText(
                            this@ManageAdminsActivity,
                            apiResponse?.message ?: "Gagal menambahkan admin",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@ManageAdminsActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ManageAdminsActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateAdmin(id: Int, name: String, email: String, phone: String, password: String?) {
        lifecycleScope.launch {
            try {
                val user = userPreferences.userFlow.first()
                if (user == null) {
                    Toast.makeText(this@ManageAdminsActivity, "User not found", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val request = AdminRequest(
                    admin_email = user.email,
                    action = "update",
                    id = id,
                    name = name,
                    email = email,
                    phone = phone.ifEmpty { null },
                    password = password?.ifEmpty { null }
                )

                val response = RetrofitClient.apiService.manageAdmins(request)

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.success == true) {
                        Toast.makeText(this@ManageAdminsActivity, "Admin berhasil diupdate", Toast.LENGTH_SHORT).show()
                        loadAdmins()
                    } else {
                        Toast.makeText(
                            this@ManageAdminsActivity,
                            apiResponse?.message ?: "Gagal mengupdate admin",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@ManageAdminsActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ManageAdminsActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun deleteAdmin(id: Int) {
        lifecycleScope.launch {
            try {
                val user = userPreferences.userFlow.first()
                if (user == null) {
                    Toast.makeText(this@ManageAdminsActivity, "User not found", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val request = AdminRequest(
                    admin_email = user.email,
                    action = "delete",
                    id = id
                )

                val response = RetrofitClient.apiService.manageAdmins(request)

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.success == true) {
                        Toast.makeText(this@ManageAdminsActivity, "Admin berhasil dihapus", Toast.LENGTH_SHORT).show()
                        loadAdmins()
                    } else {
                        Toast.makeText(
                            this@ManageAdminsActivity,
                            apiResponse?.message ?: "Gagal menghapus admin",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@ManageAdminsActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ManageAdminsActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

