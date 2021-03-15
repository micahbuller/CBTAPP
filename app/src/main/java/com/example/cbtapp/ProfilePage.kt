package com.example.cbtapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile_page.*
import kotlinx.android.synthetic.main.activity_profile_page.userLogInEmail


private lateinit var auth: FirebaseAuth

class ProfilePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        // getting the current user.
        var user = Firebase.auth.currentUser

        profileEmail.setHint(user?.email)

    }
    fun sendPasswordResetEmail(view: View) {

        Firebase.auth.sendPasswordResetEmail(user?.email.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Contragts", "Email sent.")
                    Toast.makeText(baseContext, "Please check your email.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun updateProfileEmail(view: View) {

        if (!Patterns.EMAIL_ADDRESS.matcher(userLogInEmail.text.toString()).matches()){
            userLogInEmail.error = "Please enter valid username not ${userLogInEmail.text.toString()}"
            userLogInEmail.requestFocus()
            return
        }
        if (userLogInEmail.text.toString().isEmpty()){
            userLogInEmail.error = "Please enter username"
            userLogInEmail.requestFocus()
            return
        }

        user!!.updateEmail(userLogInEmail.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Congrats", "User email address updated.")
                }
            }
    }
}