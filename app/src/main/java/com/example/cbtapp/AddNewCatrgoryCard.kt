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
import kotlinx.android.synthetic.main.activity_add_new_catrgory_card.*
import kotlinx.android.synthetic.main.activity_post_signup_page.*

private lateinit var auth: FirebaseAuth
val user = Firebase.auth.currentUser

class AddNewCatrgoryCard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_catrgory_card)

    }

    fun addNewCategoryToDatabase(view: View) {

        val db = FirebaseFirestore.getInstance()

        val dbInfo = hashMapOf<String, Any>(
            "categoryCardName" to categoryCardName.text.toString()
        )


        db.collection("users").document("${user?.email}").collection("categoryCardNames").document(categoryCardName.text.toString())
            .set(dbInfo)
            .addOnSuccessListener {
                Toast.makeText(this, "Record Added Successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this, "Record not saved to databse", Toast.LENGTH_SHORT).show()
            }

        startActivity(Intent(this,HomePage::class.java))
        finish()
    }

}