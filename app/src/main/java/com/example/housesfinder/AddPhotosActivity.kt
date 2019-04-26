package com.example.housesfinder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_annonce_photos.*
import java.lang.Double.parseDouble


class AddPhotosActivity : AppCompatActivity() {
    private  val REQUEST_PICK_PHOTO = 1
    private var photos_number=0
    private val photos_max_number=6
    private  var  uriList :ArrayList<Uri> =ArrayList<Uri>()
    private var infoList:ArrayList<String> =ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_annonce_photos)
        infoList=intent.getStringArrayListExtra("info")



        // next  button
        button_valider.setOnClickListener {

            val anonce = Anonce (parseDouble(infoList[0]),infoList[2],parseDouble(infoList[1]),infoList[3],uriList,MainActivity.mainSeller)
            MainActivity.listAnnoce.add(anonce)
            startActivity(Intent(this, MainActivity::class.java))


        }



        // previous  button
        button_previous_photos.setOnClickListener {
            onBackPressed() }

        //add image button :
        button_add_image.setOnClickListener {

            if ( photos_number==photos_max_number)
            {
                Toast.makeText(this,"Vous pouvez ajouter au maximum ${photos_max_number} photos",Toast.LENGTH_SHORT).show()
            }
            else{

            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_PHOTO)}

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_PICK_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    // something is wrong
                }
                photos_number++
                val clipData = data!!.clipData
                if (clipData != null) { // handle multiple photo
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri

                        //importPhoto(uri)
                    }
                } else { // handle single photo
                    val uri = data?.data
                    if (photos_number<4)
                    { displayImage(uri,1)}
                   else { displayImage(uri,2)}



                    uriList.add(uri)
                }
            }
        }
    }

    fun displayImage(image: Uri,layout:Int)
    {
        val first_row = findViewById(R.id.layout_images_first) as LinearLayout
        val second_row = findViewById(R.id.layout_images_second) as LinearLayout
        val imgUsers = ImageView(applicationContext)

        // SET THE IMAGEVIEW DIMENSIONS
                val dimens = 100
                val density = resources.displayMetrics.density
                val finalDimens = (dimens * density).toInt()

                val imgvwDimens = LinearLayout.LayoutParams(finalDimens, finalDimens)
                imgUsers.setLayoutParams(imgvwDimens)

        // SET SCALETYPE
                imgUsers.setScaleType(ScaleType.CENTER_CROP)

        // SET THE MARGIN
                val dimensMargin = 4
                val densityMargin = resources.displayMetrics.density
                val finalDimensMargin = (dimensMargin * densityMargin).toInt()

                val imgvwMargin = LinearLayout.LayoutParams(finalDimens, finalDimens)
                imgvwMargin.setMargins(finalDimensMargin, finalDimensMargin, finalDimensMargin, finalDimensMargin)

        // SET YOUR IMAGER SOURCE TO YOUR NEW IMAGEVIEW HERE
            imgUsers.setImageURI(image)
        // ADD THE NEW IMAGEVIEW WITH THE PROFILE PICTURE LOADED TO THE LINEARLAYOUT
        if (layout==1){ first_row.addView(imgUsers, imgvwMargin)}
        else {second_row.addView(imgUsers, imgvwMargin)}
            }
}
