package com.Try.MyApps

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.Try.MyApps.databinding.ActivitySplashScreennBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreennBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screenn)
        sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val iv_note = findViewById<ImageView>(R.id.iv_note)


        iv_note.alpha =0f
        iv_note.animate().setDuration(1500).alpha(1f).withEndAction {
            val i = Intent(this, MotionActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            checkSharedPreferences()
            finish()
        }

    }
    private fun checkSharedPreferences() {
        val userEmail = sharedPreferences.contains("user_email")

        val intent = if (userEmail) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, MotionActivity::class.java)
        }
        startActivity(intent)
        finish()

    }
}

