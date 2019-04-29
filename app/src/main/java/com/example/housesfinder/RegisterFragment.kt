package com.example.housesfinder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_register, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        submit_btn.setOnClickListener(View.OnClickListener {
            if( (!(fname.text.isBlank())) && !(lname.text.isBlank()) && (!(phone.text.isBlank())) && (!(email.text.isBlank())))
            {
                MainActivity.mainSeller =Seller(fname.text.toString(),lname.text.toString(),phone.text.toString(),email.text.toString())
                startActivity(Intent(activity, MainActivity::class.java))

            }
            else {
                Toast.makeText(activity,"Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        })
    }

}