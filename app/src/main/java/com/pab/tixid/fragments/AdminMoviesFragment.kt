package com.pab.tixid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pab.tixid.AdminDashboardActivity
import com.pab.tixid.R
import com.pab.tixid.adapters.AdminMovieAdapter
import com.pab.tixid.api.RetrofitClient
import com.pab.tixid.models.Movie
import com.pab.tixid.models.MovieRequest
import com.pab.tixid.utils.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AdminMoviesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmpty: TextView
    private lateinit var adapter: AdminMovieAdapter
    private lateinit var userPreferences: UserPreferences
    private var status: String = "now_showing"

    companion object {
        private const val ARG_STATUS = "status"

        fun newInstance(status: String): AdminMoviesFragment {
            val fragment = AdminMoviesFragment()
            val args = Bundle()
            args.putString(ARG_STATUS, status)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            status = it.getString(ARG_STATUS, "now_showing")
        }
        userPreferences = UserPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_movies)
        progressBar = view.findViewById(R.id.progress_bar)
        tvEmpty = view.findViewById(R.id.tv_empty)

        setupRecyclerView()
        loadMovies()
    }

    private fun setupRecyclerView() {
        adapter = AdminMovieAdapter(emptyList()) { movie ->
            // Handle edit click - will be implemented in AdminDashboardActivity
            (activity as? AdminDashboardActivity)?.editMovie(movie)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    fun loadMovies() {
        progressBar.visibility = View.VISIBLE
        tvEmpty.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val user = userPreferences.userFlow.first()
                if (user == null) {
                    Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val request = MovieRequest(
                    admin_email = user.email,
                    status = status
                )

                val response = RetrofitClient.apiService.getAdminMovies(request)

                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.success == true) {
                        val movies = apiResponse.data?.get("movies") ?: emptyList()
                        if (movies.isEmpty()) {
                            tvEmpty.visibility = View.VISIBLE
                        } else {
                            adapter.updateMovies(movies)
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            apiResponse?.message ?: "Failed to load movies",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                progressBar.visibility = View.GONE
                Toast.makeText(
                    requireContext(),
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

