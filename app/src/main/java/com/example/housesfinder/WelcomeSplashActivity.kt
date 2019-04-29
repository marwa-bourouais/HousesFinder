package com.example.housesfinder


import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

class WelcomeSplashActivity : AppCompatActivity() {
    lateinit var applogo : ImageView
    lateinit var apptv : TextView
    lateinit var welcometv : TextView
    lateinit var containerview : ConstraintLayout
    private var content: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_splash)

        applogo = findViewById(R.id.logoview)
        containerview = findViewById(R.id.container)
        welcometv = findViewById(R.id.welcometv)
        apptv = findViewById(R.id.apptv)
        content = findViewById<FrameLayout>(R.id.containerForm)

        var myanim : Animation = AnimationUtils.loadAnimation(this,R.anim.welcome_transition)
        applogo.startAnimation(myanim)
        containerview.startAnimation(myanim)
        welcometv.startAnimation(myanim)
        apptv.startAnimation(myanim)
        myanim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                var ta = ObjectAnimator.ofFloat(welcometv,View.ALPHA,1f,0f)
                var ta2 = ObjectAnimator.ofFloat(apptv,View.ALPHA,1f,0f)
                ta.setDuration(20)
                ta2.setDuration(20)
                ta.start()
                ta2.start()
                val tx1 = ObjectAnimator.ofFloat(applogo, View.TRANSLATION_Y, 0f, -350f)
                tx1.setDuration(200)
                tx1.start()
                val fragment = RegisterFragment()
                addFragment(fragment)
            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.containerForm, fragment, fragment.javaClass.getSimpleName())
            .addToBackStack(fragment.javaClass.getSimpleName())
            .commit()
    }
}
