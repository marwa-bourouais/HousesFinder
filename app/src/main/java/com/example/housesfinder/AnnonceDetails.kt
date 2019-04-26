package com.example.housesfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_annonce_details.*

class AnnonceDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annonce_details)

        var bundle = intent.extras
        val annonce : Anonce = MainActivity.listAnnoce.get(bundle.getInt("item"))
        typeDetails.text = annonce.type.toString()
        priceDetails.text = annonce.price.toString()
        wilayaDetails.text = annonce.wilaya.toString()
        areaDetails.text = annonce.area.toString()
        fnameDetails.text = annonce.seller!!.firstName.toString()
        lnameDetails.text = annonce.seller!!.lastName.toString()
        phoneDetails.text = annonce.seller!!.phoneNumber.toString()
        emailDetails.text = annonce.seller!!.email.toString()
    }

}
