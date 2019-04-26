package com.example.housesfinder

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddSellerInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showDialog()
    }


    private fun showDialog() {
        var dialogs = Dialog(this)
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogs.setCancelable(false)

        dialogs.setContentView(R.layout.activity_add_seller_details)

        val yesBtn = dialogs.findViewById(R.id.btn_ok) as Button
        val fname_input = dialogs.findViewById(R.id.seller_fname) as EditText
        val lname_input = dialogs.findViewById(R.id.seller_lname) as EditText
        val mail_input = dialogs.findViewById(R.id.seller_mail) as EditText
        val phone_input = dialogs.findViewById(R.id.seller_phone) as EditText



        yesBtn.setOnClickListener {
            if( (!(fname_input.text.isBlank())) && !(lname_input.text.isBlank()) && (!(phone_input.text.isBlank())) && (!(mail_input.text.isBlank())))
            {
                dialogs.hide()
                MainActivity.mainSeller =Seller(fname_input.text.toString(),lname_input.text.toString(),phone_input.text.toString(),mail_input.text.toString())
                startActivity(Intent(this, MainActivity::class.java))

            }
            else {
                Toast.makeText(this,"Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()}


        }
        /* noBtn.setOnClickListener { dialogs.dismiss() }*/
        dialogs.show()

    }
}


