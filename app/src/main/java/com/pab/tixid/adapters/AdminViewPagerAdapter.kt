package com.pab.tixid.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pab.tixid.fragments.AdminMoviesFragment

class AdminViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = mutableListOf<AdminMoviesFragment>()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val status = when (position) {
            0 -> "now_showing"
            1 -> "coming_soon"
            else -> "now_showing"
        }

        val fragment = AdminMoviesFragment.newInstance(status)
        fragments.add(fragment)
        return fragment
    }

    fun refreshFragments() {
        fragments.forEach { it.loadMovies() }
    }
}

