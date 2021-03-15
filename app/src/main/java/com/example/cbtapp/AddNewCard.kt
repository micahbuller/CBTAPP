package com.example.cbtapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_new_card.*
import kotlinx.android.synthetic.main.activity_add_new_catrgory_card.*
import kotlinx.android.synthetic.main.activity_post_signup_page.*

private lateinit var auth: FirebaseAuth

class AddNewCard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_card)


    }

    fun addNewCardToDatabase(view: View) {

        val db = FirebaseFirestore.getInstance()

        var intent = intent
        val categoryCardname = intent.getStringExtra("categoryCardName")

        // Adding the Lie and Truth to the beginning of the cards.
        val lie = "Lie: " + cardLie.text.toString()
        val truth = "Truth: " + cardTruth.text.toString()

        val dbInfo = hashMapOf<String, Any>(
            "cardLie" to lie,
            "cardTruth" to truth
        )


        db.collection("users").document("${user?.email}").collection("categoryCardNames")
            .document(categoryCardname.toString()).collection("cardNames")
            .add(dbInfo)
            .addOnSuccessListener {
                Toast.makeText(this, "Record Added Successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Record not saved to databse", Toast.LENGTH_SHORT).show()
            }

        finish()
    }

}