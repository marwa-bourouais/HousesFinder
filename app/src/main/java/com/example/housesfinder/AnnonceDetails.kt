package com.example.housesfinder

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_annonce_details.*
import kotlinx.android.synthetic.main.dialog_custom_layout.*
import java.lang.Double

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

        //displaying images

        for (uri in annonce.image!!){
            if(!isNumeric(uri.toString()))
                displayImage(uri,0)
            else
                displayImage(uri,1)
        }
    }
    fun isNumeric(uri:String) :Boolean{
        var numeric = true

        try {
            val num = Double.parseDouble(uri)
        } catch (e: NumberFormatException) {
            numeric = false
        }
        return numeric
    }
    fun displayImage(image: Uri,type: Int) {
        val imgUsers = ImageView(applicationContext)

        // SET THE IMAGEVIEW DIMENSIONS
        val dimens = 100
        val density = resources.displayMetrics.density
        val finalDimens = (dimens * density).toInt()

        val imgvwDimens = LinearLayout.LayoutParams(finalDimens, finalDimens)
        imgUsers.setLayoutParams(imgvwDimens)

        // SET SCALETYPE
        imgUsers.setScaleType(ImageView.ScaleType.CENTER_CROP)

        // SET THE MARGIN
        val dimensMargin = 4
        val densityMargin = resources.displayMetrics.density
        val finalDimensMargin = (dimensMargin * densityMargin).toInt()

        val imgvwMargin = LinearLayout.LayoutParams(finalDimens, finalDimens)
        imgvwMargin.setMargins(finalDimensMargin, finalDimensMargin, finalDimensMargin, finalDimensMargin)

        // SET YOUR IMAGER SOURCE TO YOUR NEW IMAGEVIEW HERE
        if(type == 0)
            imgUsers.setImageURI(image)
        else
            imgUsers.setImageResource(image.toString().toInt())
        imgUsers.setOnClickListener(View.OnClickListener {
            Toast.makeText(applicationContext,"zoom photo",Toast.LENGTH_LONG).show()
            Log.i("Toast","click image")
            showDialog(image,type)
        })
        // ADD THE NEW IMAGEVIEW WITH THE PROFILE PICTURE LOADED TO THE LINEARLAYOUT
        images_container.addView(imgUsers, imgvwMargin)
    }

    private fun showDialog(image:Uri,type:Int) {
        var dialogs = Dialog(this)
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogs.setCancelable(true)
        val view = layoutInflater.inflate(R.layout.dialog_custom_layout,null)
        val photoViewd = view.findViewById<ImageView>(R.id.zoomedPhoto)

        // SET THE IMAGEVIEW DIMENSIONS
        val dimens = 300
        val density = resources.displayMetrics.density
        val finalDimens = (dimens * density).toInt()

        val imgvwDimens = LinearLayout.LayoutParams(finalDimens, finalDimens)
        photoViewd.setLayoutParams(imgvwDimens)

        // SET SCALETYPE
        photoViewd.setScaleType(ImageView.ScaleType.CENTER_CROP)

        if(type == 0)
            photoViewd.setImageURI(image)
        else
            photoViewd.setImageResource(image.toString().toInt())
        dialogs.setContentView(view)
        /* noBtn.setOnClickListener { dialogs.dismiss() }*/

        dialogs.show()

    }
}
