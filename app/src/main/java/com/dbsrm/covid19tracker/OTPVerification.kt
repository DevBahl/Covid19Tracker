package com.dbsrm.covid19tracker

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class OTPVerification : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

            supportFragmentManager
                .beginTransaction()
                .add(R.id.container1,GetStarted(),"Otp")
                .commit()

    }
}
