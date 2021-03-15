package com.example.cbtapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class HowItWorksIntro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_it_works_intro)
    }

    fun toMainActivity(view: View) {
        startActivity(Intent(this,HomePage::class.java))
        finish()
    }
}