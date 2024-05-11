package com.proyekmokom.chastethrift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    lateinit var container: FragmentContainerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.homeFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    // Navigasi ke fragment home
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.menu_profile -> {
                    // Navigasi ke fragment profile
                    navController.navigate(R.id.profileFragment)
                    true
                }
                R.id.menu_cart -> {
                    // Navigasi ke fragment cart
                    navController.navigate(R.id.cartFragment)
                    true
                }
                else -> false
            }
        }
    }
}