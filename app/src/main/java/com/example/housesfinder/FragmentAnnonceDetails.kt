package com.example.housesfinder

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_annonce_details.*

@SuppressLint("ValidFragment")
class FragmentAnnonceDetails(private val annonce:Anonce) : Fragment(){
    lateinit var viewPager : ViewPager
    lateinit var adapter:ViewPagerAdapter
    lateinit var bundle:Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ViewPagerAdapter(this!!.context!!,annonce!!.image!!,2f)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_annonce_details, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewPager = activity!!.findViewById(R.id.viewPager)
        viewPager.adapter = adapter

        typeDetails.text = annonce.type.toString()
        priceDetails.text = annonce.price.toString() + "DA"
        wilayaDetails.text = annonce.wilaya.toString()
        areaDetails.text = annonce.area.toString() + "m²"
    }
}