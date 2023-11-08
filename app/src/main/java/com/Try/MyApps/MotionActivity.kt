package com.Try.MyApps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.Try.MyApps.databinding.ActivityMotionBinding

class MotionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMotionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMotionBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        val view = binding.root
        setContentView(view)

        val loginTextView = binding.button
        loginTextView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}