package com.Try.MyApps

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.Try.MyApps.databinding.ActivityMainBinding
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.Try.MyApps.ui.favorite.FavoriteFragment
import com.Try.MyApps.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val navView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.favorite, R.id.navigation_profile,

                )

        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Add bottom navigation listener
        // Add bottom navigation listener
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Handle home menu item
                    navController.navigate(R.id.navigation_home)
                }
                R.id.favorite -> {
                    // Handle favorite menu item
                    // Replace the current fragment with FavoriteFragment
                    navController.navigate(R.id.favorite)
                }
                R.id.navigation_profile -> {
                    // Handle profile menu item
                    // Replace the current fragment with ProfileFragment
                    navController.navigate(R.id.navigation_profile)
                }
                R.id.navigation_logout -> {
                    // Handle logout menu item
                    // Create and show alert dialog
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle("Logout")
                    builder.setMessage("Are you sure you want to logout?")
                    builder.setPositiveButton("Yes") { dialog, which ->
                        // Handle logout action
                        performLogout()
                    }
                    builder.setNegativeButton("No") { dialog, which ->
                        // Dismiss dialog
                        dialog.dismiss()
                    }
                    val alertDialog = builder.create()
                    alertDialog.show()
                }
            }
            true
        }

    }
    private fun performLogout() {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("user_email")
        editor.apply()

        // Arahkan pengguna ke layar login atau aktivitas awal
        val intent = Intent(this, SignInActivity::class.java) // Ganti LoginActivity dengan kelas aktivitas login Anda
        startActivity(intent)

        // Hentikan aktivitas saat ini
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}
