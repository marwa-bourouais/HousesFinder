package com.example.housesfinder

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.card_annonce.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.Double
import java.lang.Double.parseDouble
import java.util.*

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

        annoncesList.isTextFilterEnabled=true

        btn_filter.setOnClickListener {
            showDialog()

        }

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (TextUtils.isEmpty(newText)) {
                    annoncesList.clearTextFilter();
                }
                else {
                    annoncesList.setFilterText(newText.toString());
                }

                return true;


            }


    })}

    inner class AnonceAdapter: BaseAdapter , Filterable {


        var context : Context?=null
        var listAnnonceLocal = ArrayList<Anonce>()
        var filteredList = ArrayList<Anonce>()
        var listFilter :AnonceFilter? = null

        constructor(listCard : ArrayList<Anonce>,context : Context){
            this.listAnnonceLocal = listCard
            this.filteredList=listCard
            this.context = context

        }

        override fun getFilter(): Filter? {
            if (listFilter == null) {
                listFilter= AnonceFilter()
            }

            return listFilter

        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val item = filteredList.get(position)
            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layoutItem = inflator.inflate(R.layout.card_annonce,null)
            layoutItem.cardTitle.text = item.type!!
            Log.i("path",item.image!!.get(0).toString())
            if(isNumeric(item.image!!.get(0).toString()))
                layoutItem.cardImage.setImageResource(item.image!!.get(0).toString().toInt())
            else
                layoutItem.cardImage.setImageURI(item.image!!.get(0))

            layoutItem.cardWilaya.text = item.wilaya!!
            layoutItem.cardPrice.text = item.price.toString()!! + " DA"
            layoutItem.CardArea.text = item.area.toString()!! + "m²"
            layoutItem.date.text=item.date

            layoutItem.seeDetails.setOnClickListener(View.OnClickListener {
                //move to details
                val intent = Intent(context,AnnonceDetails::class.java)

                intent.putExtra("item",position)
                context!!.startActivity(intent)
            })
            return layoutItem
        }

        override fun getItem(position: Int): Any {
            if (filteredList!=null)
            { return filteredList.get(position)}
            else
            {
                return listAnnonceLocal.get(position)
            }
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            if (filteredList!=null)
            { return filteredList.size}
            else
            {
                return listAnnonceLocal.size
            }
        }

        fun sortListByWilaya()
        {
            Collections.sort(filteredList, object : Comparator<Anonce> {
                override fun compare(emp1: Anonce, emp2: Anonce): Int {
                    return emp1.wilaya!!.compareTo(emp2.wilaya!!)
                }

            })

            notifyDataSetChanged();
        }
        fun sortListByPrice()
        {
            Collections.sort(filteredList, object : Comparator<Anonce> {
                override fun compare(emp1: Anonce, emp2: Anonce): Int {

                    return Double.compare(emp1.price!!,emp2.price!!)

                }



            })

                    notifyDataSetChanged()
        }
        fun sortListByDate( )
        {
            Collections.sort(filteredList, object : Comparator<Anonce> {
                override fun compare(emp1: Anonce, emp2: Anonce): Int {
                    return emp1.date.compareTo(emp2.date)




                }

            })

            notifyDataSetChanged();
        }



        inner  class AnonceFilter : Filter() {

            @Override
            override fun  performFiltering(constraint:CharSequence):FilterResults {
                var  filterResults:FilterResults =  FilterResults ()
                if (constraint!=null && constraint.length>0) {

                    var  tempList: ArrayList<Anonce>  =  ArrayList<Anonce>();

                    // search content in friend list
                    for (annonce :Anonce in listAnnonceLocal ) {
                        if (annonce.wilaya!!.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            tempList.add(annonce)

                        }
                    }

                    filterResults.count = tempList.size
                    filterResults.values = tempList

                } else {
                    filterResults.count = listAnnonceLocal.size
                    filterResults.values = listAnnonceLocal
                }

                return filterResults;
            }

            /**
             * Notify about filtered list to ui
             * @param constraint text
             * @param results filtered result
             */
            @SuppressWarnings("unchecked")
            @Override
            override fun  publishResults(constraint:CharSequence, results: FilterResults) {
                filteredList = results.values  as ArrayList<Anonce>

                notifyDataSetChanged();
            }
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

    private fun showDialog() {
        var dialogs = Dialog(this.activity)
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogs.setCancelable(false)

        dialogs.setContentView(R.layout.filter_alert)

        val yesBtn = dialogs.findViewById(R.id.btn_ok) as Button
        val filterSpinner = dialogs.findViewById(R.id.filtres) as Spinner





        yesBtn.setOnClickListener {
            if (filterSpinner.selectedItem.toString().equals("Wilaya"))
            {
                adapter!!.sortListByWilaya()
                dialogs.hide()

            }
            if (filterSpinner.selectedItem.toString().equals("Date"))
            {
                adapter!!.sortListByDate()
                dialogs.hide()

            }
            if (filterSpinner.selectedItem.toString().equals("Prix"))
            {   adapter!!.sortListByPrice()
                dialogs.hide()




            }



        }
         dialogs.show()

    }



}
