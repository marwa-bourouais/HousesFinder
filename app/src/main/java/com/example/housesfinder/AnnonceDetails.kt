package com.example.housesfinder

import android.Manifest
import android.app.Dialog
import android.net.Uri
import android.widget.LinearLayout
import android.content.Intent
import android.content.pm.PackageManager

import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_annonce_details.*
import java.lang.Double

class AnnonceDetails : AppCompatActivity() {
    lateinit var bundle:Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annonce_details)

        bundle = intent.extras
        val annonce : Anonce = MainActivity.listAnnoce.get(bundle.getInt("item"))
        typeDetails.text = annonce.type.toString()
        priceDetails.text = annonce.price.toString()
        wilayaDetails.text = annonce.wilaya.toString()
        areaDetails.text = annonce.area.toString()
        fnameDetails.text = annonce.seller!!.firstName.toString()
        lnameDetails.text = annonce.seller!!.lastName.toString()
        phoneDetails.text = annonce.seller!!.phoneNumber.toString()
        emailDetails.text = annonce.seller!!.email.toString()
        callSeller.setOnClickListener(View.OnClickListener {
            Toast.makeText(this,"Calling",Toast.LENGTH_SHORT).show()
            callPhone()
        })
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
    fun displayImage(image: Uri, type: Int) {
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
        val dimens = 350
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


    //for calling
    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42)
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }

    fun callPhone(){
        val number =  MainActivity.listAnnoce.get(bundle.getInt("item")).seller!!.phoneNumber.toString()
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number))
        startActivity(intent)
    }
}
