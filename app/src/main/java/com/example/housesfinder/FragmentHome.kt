package com.example.housesfinder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.card_annonce.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.Double

class FragmentHome : Fragment() {
    var adapter:AnonceAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = AnonceAdapter(MainActivity.listAnnoce, this!!.activity!!)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_home, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        annoncesList.adapter = adapter
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