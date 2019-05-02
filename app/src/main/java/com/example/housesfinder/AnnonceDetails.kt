package com.example.housesfinder

import android.Manifest
import android.net.Uri
import android.content.Intent
import android.content.pm.PackageManager

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_annonce_details.*
import kotlinx.android.synthetic.main.activity_main.*

class AnnonceDetails : AppCompatActivity() {
    lateinit var bundle:Bundle
    private var content: FrameLayout? = null
    lateinit var annonce : Anonce
    lateinit var viewPager : ViewPager
    lateinit var adapter:ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annonce_details)

        bundle = intent.extras
        annonce = MainActivity.listAnnoce.get(bundle.getInt("item"))

        content = findViewById(R.id.container_details)
        navigation_details.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragment = FragmentAnnonceDetails(annonce)
        addFragment(fragment)
        /*typeDetails.text = annonce.type.toString()
        priceDetails.text = annonce.price.toString() + "DA"
        wilayaDetails.text = annonce.wilaya.toString()
        areaDetails.text = annonce.area.toString() + "m²"
        fnameDetails.text = annonce.seller!!.firstName.toString()
        lnameDetails.text = annonce.seller!!.lastName.toString()
        phoneDetails.text = annonce.seller!!.phoneNumber.toString()
        emailDetails.text = annonce.seller!!.email.toString()
        callSeller.setOnClickListener(View.OnClickListener {
            Toast.makeText(this,"Calling",Toast.LENGTH_SHORT).show()
            callPhone()
        })
        //displaying images
        val density = resources.displayMetrics.density
        adapter = ViewPagerAdapter(this, annonce.image!!,density)
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = adapter*/
    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_annonce -> {
                val fragment = FragmentAnnonceDetails(annonce)
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_seller -> {

                val fragment = FragmentAnnonceSeller(annonce!!.seller!!)
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true

            }

        }
        false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.container_details, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }

}
