package com.Try.MyApps

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.Try.MyApps.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use view binding to inflate the layout and set the content view
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        supportActionBar?.hide()
        val registerhref = findViewById<TextView>(R.id.RegisterText)
        registerhref.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        // Initialize the database reference by getting an instance of FirebaseDatabase and a child reference of "users"
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")
        firebaseAuth = FirebaseAuth.getInstance()

        // Add a click listener for the login button that will call the loginUser method with the email and password from the EditTexts
        binding.loginbutton.setOnClickListener {
            val email = binding.inputemail.text.toString()
            val password = binding.inputPass.text.toString()

            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

            if (!email.matches(emailPattern)) {
                Toast.makeText(this@SignInActivity, "Email tidak valid", Toast.LENGTH_SHORT).show()
                // Return from the function
                return@setOnClickListener
            }
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this@SignInActivity, "invalid", Toast.LENGTH_SHORT).show()
            }
        }
    }
        private fun loginUser(email: String, password: String) {
            // Masuk dengan email dan password di Firebase Authentication
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        saveEmailToSharedPreferences(email)
                        // Jika berhasil, tampilkan pesan Toast
                        Toast.makeText(
                            this@SignInActivity,
                            "Login berhasil",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                        // Membuat intent untuk membuka MainActivity
                        val intent = Intent(this@SignInActivity, MainActivity::class.java)
                        // Mengirimkan data user ke MainActivity
                        intent.putExtra("email", email)
                        intent.putExtra("password", password)
                        // Memulai aktivitas baru
                        startActivity(intent)
                    } else {
                        // Jika gagal, tampilkan pesan error
                        Toast.makeText(
                            this@SignInActivity,
                            "Gagal login: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }
        }
    private fun saveEmailToSharedPreferences(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("user_email", email)
        editor.apply()
    }
    }

