package com.example.cbtapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()


        btn_signup.setOnClickListener{
            
            signUpUser()

        }

        btn_login.setOnClickListener{

            logInUser()
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            Toast.makeText(baseContext, "You're signed in already",
                Toast.LENGTH_SHORT).show()
        }
    }

    fun reload(currentUser: FirebaseUser){

    }

    fun logInUser(){
        if (userLogInEmail.text.toString().isEmpty()){
            userLogInEmail.error = "Please enter username"
            userLogInEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userLogInEmail.text.toString()).matches()){
            userLogInEmail.error = "Please enter valid username not ${userLogInEmail.text.toString()}"
            userLogInEmail.requestFocus()
            return
        }
        if (userLogInPassword.text.toString().isEmpty()){
            userLogInPassword.error = "Please enter password"
            userLogInPassword.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(userLogInEmail.text.toString(), userLogInPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    startActivity(Intent(this, HomePage::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    // ...
                }

                // ...
            }
    }

    fun signUpUser(){

        if (userLogInEmail.text.toString().isEmpty()){
            userLogInEmail.error = "Please enter username"
            userLogInEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userLogInEmail.text.toString()).matches()){
            userLogInEmail.error = "Please enter valid username not ${userLogInEmail.text.toString()}"
            userLogInEmail.requestFocus()
            return
        }
        if (userLogInPassword.text.toString().isEmpty()){
            userLogInPassword.error = "Please enter password"
            userLogInPassword.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(userLogInEmail.text.toString(), userLogInPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this, HowItWorksIntro::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed. You entered wrong or already have an account.",
                        Toast.LENGTH_SHORT).show()
                }

                // ...
            }
    }

    fun createUserInDatabse(){

        val db = FirebaseFirestore.getInstance()
        val user = Firebase.auth.currentUser

        val dbInfo = hashMapOf("uid" to user?.uid.toString())

        Toast.makeText(baseContext, "Got the UID and it is: ${user?.email}",
            Toast.LENGTH_SHORT).show()


        db.collection("users")
            .add(dbInfo)
            .addOnSuccessListener {
                Toast.makeText(this, "Record Added Successfully", Toast.LENGTH_SHORT)
            }.addOnFailureListener{
                Toast.makeText(this, "Record not saved to databse", Toast.LENGTH_SHORT)
            }
    }




}


