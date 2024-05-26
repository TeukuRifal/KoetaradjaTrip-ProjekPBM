package com.pbm.koetaradjatrip


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pbm.koetaradjatrip.halaman.AboutFragment
import com.pbm.koetaradjatrip.halaman.FavoritFragment
import com.pbm.koetaradjatrip.halaman.HomeFragment
import com.pbm.koetaradjatrip.halaman.MapsFragment
import com.pbm.koetaradjatrip.halaman.SearchFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Atur bottom navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_page -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.search_page -> {
                    // Tampilkan SearchFragment dan kirimkan UserViewModel sebagai parameter
                    replaceFragment(SearchFragment())
                    true
                }
                R.id.favorite_page -> {
                    replaceFragment(FavoritFragment())
                    true
                }
                R.id.activity_map -> {
                    replaceFragment(MapsFragment())
                    true
                }
                R.id.about -> {
                    replaceFragment(AboutFragment())
                    true
                }
                else -> false
            }
        }

        // Set default Fragment
        replaceFragment(HomeFragment())

    }

    // Fungsi untuk menampilkan Fragment di dalam container
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


}
