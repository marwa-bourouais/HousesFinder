package com.example.housesfinder

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import java.lang.Double

class ViewPagerAdapter(private val context: Context, private val images: ArrayList<Uri>, private val density: Float) : PagerAdapter() {
    private var layoutInflater:LayoutInflater?=null
    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 === p1
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(context)
        val v = layoutInflater!!.inflate(R.layout.custom_slide_image,container,false) as ViewGroup
        var image = v.findViewById<ImageView>(R.id.slideImage)
        if(!isNumeric(images[position].toString()))
            image.setImageURI(images[position])
        else
            image.setImageResource(images[position].toString().toInt())
        image.setOnClickListener(View.OnClickListener {

            showDialog(images[position])
        })
        container.addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View

        vp.removeView(v)
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

    private fun showDialog(image:Uri) {
        var dialogs = Dialog(context)
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogs.setCancelable(true)
        val view = layoutInflater!!.inflate(R.layout.dialog_custom_layout,null)
        val photoViewd = view.findViewById<ImageView>(R.id.zoomedPhoto)

        // SET THE IMAGEVIEW DIMENSIONS
        val dimens = 350
        val finalDimens = (dimens * density).toInt()

        val imgvwDimens = LinearLayout.LayoutParams(finalDimens, finalDimens)
        photoViewd.setLayoutParams(imgvwDimens)

        // SET SCALETYPE
        photoViewd.setScaleType(ImageView.ScaleType.CENTER_CROP)

        if(!isNumeric(image.toString()))
            photoViewd.setImageURI(image)
        else
            photoViewd.setImageResource(image.toString().toInt())
        dialogs.setContentView(view)
        /* noBtn.setOnClickListener { dialogs.dismiss() }*/

        dialogs.show()

    }
}