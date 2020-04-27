package com.dbsrm.covid19tracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {


    override fun onDestroy() {
        super.onDestroy()
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        splashImage.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_in))
        Handler().postDelayed({
            splashImage.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_out))
            Handler().postDelayed({
                splashImage.visibility = View.GONE
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            },500)
        },1200)
    }
}

