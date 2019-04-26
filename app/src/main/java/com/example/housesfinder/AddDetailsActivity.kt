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
            val infoList = ArrayList<String>()
            infoList.add(superficie.text.toString())
            infoList.add(prix.text.toString())
            infoList.add(wilaya.selectedItem.toString().drop(3))
            infoList.add(type.selectedItem.toString())

            intent.putExtra("info",infoList)
            startActivity(intent)


        }
        // previous  button
        button_previous_details.setOnClickListener {
            onBackPressed() }
} }
