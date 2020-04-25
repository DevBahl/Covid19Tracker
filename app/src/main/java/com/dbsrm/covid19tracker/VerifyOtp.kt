package com.dbsrm.covid19tracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.otpfragment.*
import kotlinx.android.synthetic.main.verifyotp.*
import java.util.concurrent.TimeUnit

class VerifyOtp: Fragment() {

    private lateinit var mAuth: FirebaseAuth
    val bundle = this.arguments
    private val data = bundle?.getString("verificationId").toString()
    private val datatoken = bundle?.getString("token").toString()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.verifyotp,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        verify_otp.setOnClickListener {
            authenticate()
        }
        edit_phoneno.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.container1,Otp())?.commit()
        }

        resend_otp.setOnClickListener {
           // resendCode()
        }
    }

    private fun authenticate(){
        val code = pinEntryEditText.text.toString()
        val credential = PhoneAuthProvider.getCredential(data,code)
        signInWithPhoneCredential(credential)
    }

    fun signInWithPhoneCredential(credential: PhoneAuthCredential){
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if(task.isSuccessful){
                    Log.d("this1","Successful")
                    val intent = Intent(context,MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or (Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else{
                      Toast.makeText(context,"Failed Verification because $task", Toast.LENGTH_SHORT).show()
                    if (task.exception is FirebaseAuthInvalidCredentialsException){
                        //    Toast.makeText(this,"Wrong OTP Entered", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

  /*    private fun resendCode(){

        val phoneNo = type_phoneno.text.toString()
        Otp().verificationcallbacks()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNo,
            60,
            TimeUnit.SECONDS,
            this,
            Otp().mcallbacks,
            datatoken)
    }*/
}