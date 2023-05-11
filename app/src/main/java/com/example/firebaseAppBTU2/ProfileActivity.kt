package com.example.firebaseAppBTU2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    private lateinit var imageView : ImageView
    private lateinit var usernameTextView : TextView
    private lateinit var linkEditText : EditText
    private lateinit var userNameEditText : EditText
    private lateinit var updateButton : Button
    private lateinit var signOutButton : Button
    private lateinit var profilePasswordUpdateButton : Button

    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.database.getReference("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()
        listeners()
    }

    private fun listeners() {
        updateButton.setOnClickListener {
            val link = linkEditText.text.toString()
            val username = userNameEditText.text.toString()

            val userInfo = User(
                auth.currentUser?.email,
                auth.uid,
                link,
                username
            )
            db.child(auth.uid!!).setValue(userInfo)

        }
        signOutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        profilePasswordUpdateButton.setOnClickListener {
            TODO()
        }
    }

    private fun init(){
        imageView = findViewById(R.id.imageView)
        usernameTextView = findViewById(R.id.usernameTextView)
        linkEditText = findViewById(R.id.linkEditText)
        userNameEditText = findViewById(R.id.userNameEditText)
        updateButton = findViewById(R.id.updateButton)
        signOutButton = findViewById(R.id.signOutButton)
        profilePasswordUpdateButton = findViewById(R.id.profilePasswordUpdateButton)

        db.child(auth.uid!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userInfo = snapshot.getValue(User::class.java) ?: return
                usernameTextView.text = userInfo.username
                Glide.with(this@ProfileActivity).load(userInfo.link).into(imageView)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}