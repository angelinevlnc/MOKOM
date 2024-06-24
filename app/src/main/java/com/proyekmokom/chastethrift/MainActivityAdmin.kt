package com.proyekmokom.chastethrift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivityAdmin : AppCompatActivity() {

    lateinit var container: FragmentContainerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)

        val idUser = intent.getIntExtra("idUser", 3) // 0 if extra "idUser" is not found
        Toast.makeText(this, idUser.toString(), Toast.LENGTH_SHORT).show()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_admin) as NavHostFragment
        val navController = navHostFragment.navController
//        var action = AdminReportFragmentDirections.actionGlobalAdminReportFragment(idUser)
//        navController.navigate(action)

        var action = AdminApprovalListFragmentDirections.actionGlobalAdminApprovalListFragment(idUser)
        navController.navigate(action)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_admin)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.admin_report -> {
                    // Navigasi ke fragment admin report dgn idUser
                    action = AdminReportFragmentDirections.actionGlobalAdminReportFragment(idUser)
                    navController.navigate(action)
                    true
                }
                R.id.admin_profile -> {
                    // Navigasi ke fragment admin profile dgn idUser
                    action = AdminProfileFragmentDirections.actionGlobalAdminProfileFragment(idUser)
                    navController.navigate(action)
                    true
                }
                R.id.admin_list -> {
                    navController.navigate(AdminApprovalListFragmentDirections.actionGlobalAdminApprovalListFragment(idUser))
                    true
                }

                else -> false
            }
        }
    }
}