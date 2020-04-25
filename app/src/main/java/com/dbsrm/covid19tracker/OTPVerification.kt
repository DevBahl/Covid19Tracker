package com.dbsrm.covid19tracker

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class OTPVerification : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)


            supportFragmentManager
                .beginTransaction()
                .add(R.id.container1,Otp(),"Otp")
                .commit()

    }
}
