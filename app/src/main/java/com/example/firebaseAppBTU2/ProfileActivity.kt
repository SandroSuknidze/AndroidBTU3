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
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class ProfileActivity : AppCompatActivity() {

    private lateinit var imageView : ImageView
    private lateinit var usernameTextView : TextView
    private lateinit var linkEditText : EditText
    private lateinit var userNameEditText : EditText
    private lateinit var updateButton : Button
    private lateinit var signOutButton : Button
    private lateinit var profilePasswordUpdateButton : Button
    private lateinit var editTextPhone : EditText
    private lateinit var editTextTextPersonName : EditText
    private lateinit var editTextTextPersonName3 : EditText
    private lateinit var textView : TextView
    private lateinit var textView2 : TextView
    private lateinit var textView3 : TextView

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("User")

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
            val name = editTextTextPersonName.text.toString()
            val surname = editTextTextPersonName3.text.toString()
            val id = editTextPhone.text.toString()

            val userInfo = User(
                auth.currentUser?.email,
                name,
                surname,
                id,
                auth.currentUser!!.uid,
                link,
                username
            )

            db.child(auth.currentUser!!.uid).setValue(userInfo)

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
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName)
        editTextTextPersonName3 = findViewById(R.id.editTextTextPersonName3)
        textView = findViewById(R.id.textView)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)

        db.child(auth.uid!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userInfo = snapshot.getValue(User::class.java) ?: return

                if(((userInfo.id)?.length!=13)||((userInfo.id)?.toDoubleOrNull()==null)){
                    Toast.makeText(this@ProfileActivity, "incorrect id", Toast.LENGTH_SHORT).show()
                }else {
                    usernameTextView.text = userInfo.email
                    textView.text = userInfo.id
                    textView2.text = userInfo.name
                    textView3.text = userInfo.surname
                    Glide.with(this@ProfileActivity).load(userInfo.link).into(imageView)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}