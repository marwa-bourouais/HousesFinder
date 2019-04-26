package com.example.housesfinder

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_annonce.view.*
import java.lang.Double

class MainActivity : AppCompatActivity() {
    var adapter:AnonceAdapter?=null
    //la liste partagé des annoces :
    companion object {
        var listAnnoce = ArrayList<Anonce>()
       lateinit  var  mainSeller:Seller
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_add -> {

                startActivity(Intent(this, AddDetailsActivity::class.java))

            }
            R.id.navigation_profile -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    fun initList(){
        listAnnoce.add(Anonce(6000.6,"Alger",1000000.05,"Villa", arrayListOf(
            Uri.parse(R.drawable.home1.toString())) ,
            Seller("sihem","Bouhenniche","055189142","fs@esi.dz")
        ))
        listAnnoce.add(Anonce(6000.6,"Jijel",1000000.05,"Villa", arrayListOf(
            Uri.parse(R.drawable.home2.toString())) ,
            Seller("sihem","Bouhenniche","055189142","fs@esi.dz")))

        listAnnoce.add(Anonce(6000.6,"Oran",1000000.05,"Villa", arrayListOf(
            Uri.parse(R.drawable.home3.toString())) ,
            Seller("sihem","Bouhenniche","055189142","fs@esi.dz")))

        listAnnoce.add(Anonce(6000.6,"Alger",1000000.05,"Appartement", arrayListOf(
            Uri.parse(R.drawable.home4.toString()) ),
            Seller("sihem","Bouhenniche","055189142","fs@esi.dz")))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initList()
        adapter = AnonceAdapter(MainActivity.listAnnoce, this)
        annocesList.adapter = adapter
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }



    inner class AnonceAdapter: BaseAdapter {
        var context : Context?=null
        var listAnnonceLocal = ArrayList<Anonce>()
        constructor(listCard : ArrayList<Anonce>,context : Context){
            this.listAnnonceLocal = listCard
            this.context = context
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val item = listAnnonceLocal.get(position)
            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layoutItem = inflator.inflate(R.layout.card_annonce,null)
            layoutItem.cardTitle.text = item.type!!
            Log.i("path",item.image!!.get(0).toString())
            if(isNumeric(item.image!!.get(0).toString()))
                layoutItem.cardImage.setImageResource(item.image!!.get(0).toString().toInt())
            else
                layoutItem.cardImage.setImageURI(item.image!!.get(0))

            layoutItem.cardWilaya.text = item.wilaya!!
            layoutItem.cardPrice.text = item.price.toString()!! + "DA"
            layoutItem.CardArea.text = item.area.toString()!! + "m²"

            layoutItem.seeDetails.setOnClickListener(View.OnClickListener {
                //move to details
                val intent = Intent(context,AnnonceDetails::class.java)

                intent.putExtra("item",position)
                context!!.startActivity(intent)
            })
            return layoutItem
        }



        override fun getItem(position: Int): Any {
            return listAnnonceLocal.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listAnnonceLocal.size//To change body of created functions use File | Settings | File Templates.
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




}
