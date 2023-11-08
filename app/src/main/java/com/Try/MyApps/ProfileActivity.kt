package com.Try.MyApps

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.Try.MyApps.database.User
import com.Try.MyApps.model.GitHubUser
import com.Try.MyApps.network.GithubService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileActivity : AppCompatActivity() {

    private lateinit var imgProfile: ImageView
    private lateinit var txtName: TextView
    private lateinit var txtGithub: TextView
    private lateinit var txtFollowers: TextView
    private lateinit var txtFollowing: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val email = user?.email

        imgProfile = findViewById(R.id.imgProfile)
        txtName = findViewById(R.id.txtName)
        txtGithub = findViewById(R.id.txtGithub)
        txtFollowers = findViewById(R.id.txtFollowers)
        txtFollowing = findViewById(R.id.txtFollowing)

        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists() && snapshot.value != null) {
                    val retrievedUser = snapshot.children.first().getValue(User::class.java)
                    retrievedUser?.let { displayUserData(it) }
                } else {
                    Toast.makeText(this@ProfileActivity, "User data not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayUserData(user: User) {
        val userGithub = user.github
        userGithub?.let {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(GithubService::class.java)

            val call = service.getUserDetails(userGithub)
            call.enqueue(object : Callback<GitHubUser> {
                override fun onResponse(call: Call<GitHubUser>, response: Response<GitHubUser>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        Picasso.get()
                            .load(user?.avatar_url)
                            .into(imgProfile)
                        txtName.text = user?.name
                        txtGithub.text = user?.login
                        txtFollowers.text = user?.followers.toString()
                        txtFollowing.text = user?.following.toString()
                    } else {
                        Toast.makeText(this@ProfileActivity, "Failed to retrieve GitHub user data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GitHubUser>, t: Throwable) {
                    Toast.makeText(this@ProfileActivity, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
