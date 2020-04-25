package com.dbsrm.covid19tracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.otpfragment.*
import java.util.concurrent.TimeUnit

class Otp: Fragment() {


    lateinit var mAuth: FirebaseAuth
    lateinit var mcallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    var verificationid = " "
    var resendToken = " "

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.otpfragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        getotp_btn.setOnClickListener {
            Log.d("this1","Clicked")
            verify()
        }
    }



    fun verificationcallbacks()
    {
        mcallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                VerifyOtp().signInWithPhoneCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {

                Log.d("this1","Verification Failed because $e")
                if (e is FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(context,"Please check your phone number",Toast.LENGTH_SHORT).show()
                    return
                }

                else if(e is FirebaseTooManyRequestsException) {
                    Toast.makeText(context,"Error! Try again after some time", Toast.LENGTH_SHORT).show()
                    return
                }
            }
            override fun onCodeSent(verificationId: String,token: PhoneAuthProvider.ForceResendingToken){
                super.onCodeSent(verificationId,token)
                verificationid = verificationId.toString()
                Log.d("this1","Code Sent")
                resendToken = token.toString()
                val bundle = Bundle()
                bundle.putString("verificationId",verificationid)
                bundle.putString("token",resendToken)
                VerifyOtp().arguments
                fragmentManager?.beginTransaction()?.replace(R.id.container1,VerifyOtp())?.commit()
                 //verify_otp_btn.isEnabled = true
            }
        }
    }


     private fun verify(){

         val phoneNo = type_phoneno.text.toString()
         if(phoneNo.isEmpty()){
             Toast.makeText(context,"Please fill your phone number",Toast.LENGTH_SHORT).show()
             return
         }
         verificationcallbacks()
         activity?.let {
             PhoneAuthProvider.getInstance().verifyPhoneNumber(
                 phoneNo,
                 60,
                 TimeUnit.SECONDS,
                 it,
                 mcallbacks)
         }
    }

}