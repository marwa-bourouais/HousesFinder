package com.example.housesfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_seller_details.*

class AddSellerDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_seller_details)
        // next button
        button_valider_seller.setOnClickListener {
              }
        // previous  button
        button_previous_seller.setOnClickListener {
            onBackPressed() }

    }
}
