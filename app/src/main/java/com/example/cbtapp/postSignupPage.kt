package com.example.cbtapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_post_signup_page.*

private lateinit var auth: FirebaseAuth

class postSignupPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_signup_page)


    }

    fun submitToDatabase(view: View) {

        val db = FirebaseFirestore.getInstance()
        val user = Firebase.auth.currentUser

        val displayNameVar = editTextFirstName.text.toString() + " " + editTextLastName.text.toString()
/*
        val profileUpdates = userProfileChangeRequest {
            displayName = displayNameVar
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Congrats", "User profile updated.")
                }
            }

        startActivity(Intent(this,HowItWorks::class.java))

*/
    }
    fun whatIsUSerID(view: View) {
        val user = Firebase.auth.currentUser

        Toast.makeText(this, "You are: ${user.toString()}", Toast.LENGTH_SHORT).show()

    }
}