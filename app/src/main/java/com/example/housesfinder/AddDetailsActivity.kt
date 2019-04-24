package com.example.housesfinder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_annonce_details.*

class AddDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_annonce_details)
        // next button
        button_suivant_details.setOnClickListener {
            val intent =Intent(this, AddPhotosActivity::class.java)
            startActivity(intent)  }
        // previous  button
        button_previous_details.setOnClickListener {
            onBackPressed() }
} }
