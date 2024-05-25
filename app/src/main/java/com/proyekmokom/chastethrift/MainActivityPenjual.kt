package com.proyekmokom.chastethrift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivityPenjual : AppCompatActivity() {

    lateinit var container: FragmentContainerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_penjual)

        val idUser = intent.getIntExtra("idUser", 0) // 0 if extra "idUser" is not found

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_penjual) as NavHostFragment
        val navController = navHostFragment.navController
        var action = PenjualCatalogFragmentDirections.actionGlobalPenjualCatalogFragment(idUser)
        navController.navigate(action)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_penjual)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.penjual_catalog -> {
                    // Navigasi ke fragment penjual catalog dgn idUser
                    action = PenjualCatalogFragmentDirections.actionGlobalPenjualCatalogFragment(idUser)
                    navController.navigate(action)
                    true
                }
                R.id.penjual_profile -> {
                    // Navigasi ke fragment penjual profile dgn idUser
                    action = PenjualProfileFragmentDirections.actionGlobalPenjualProfileFragment(idUser)
                    navController.navigate(action)
                    true
                }
                R.id.penjual_add -> {
                    // Navigasi ke fragment penjual add dgn idUser
                    action = PenjualAddFragmentDirections.actionGlobalPenjualAddFragment(idUser)
                    navController.navigate(action)
                    navController.navigate(R.id.penjualAddFragment)
                    true
                }
                else -> false
            }
        }
    }
}