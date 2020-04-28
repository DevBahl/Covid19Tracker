package com.dbsrm.covid19tracker

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader.createBundle
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.otpfragment.*
import java.util.concurrent.TimeUnit

class Otp: Fragment() {


    private lateinit var mcallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mAuth: FirebaseAuth
    var verificationid = ""
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    var connectivity: ConnectivityManager? = null
    var info: NetworkInfo? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.otpfragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView27.visibility = View.INVISIBLE
        pinEntryEditText.visibility = View.INVISIBLE
        verify_otp.visibility = View.INVISIBLE
        resend_otp.visibility = View.INVISIBLE
        edit_phoneno.visibility = View.INVISIBLE
        loadanim.visibility = View.INVISIBLE

        mAuth = FirebaseAuth.getInstance()
        getotp_btn.setOnClickListener {
            val phoneNo = type_phoneno.text.toString()
            if(phoneNo.isEmpty() or(phoneNo.length<13)){
                Toast.makeText(context,"Please check your phone number",Toast.LENGTH_SHORT).show()
            }
            else if(isConnected()==false){
                Toast.makeText(context,"Please Connect To The Internet! You are disconnected",Toast.LENGTH_LONG).show()
            }
            else{
                verify()
                textView23.visibility = View.INVISIBLE
                type_phoneno.visibility = View.INVISIBLE
                getotp_btn.visibility = View.INVISIBLE
                textView27.visibility = View.VISIBLE
                pinEntryEditText.visibility = View.VISIBLE
                verify_otp.visibility = View.VISIBLE
                resend_otp.visibility = View.VISIBLE
                edit_phoneno.visibility = View.VISIBLE
            }
        }
        verify_otp.setOnClickListener {
            if(isConnected()==false){
            Toast.makeText(context,"Please Connect To The Internet! You are disconnected",Toast.LENGTH_LONG).show()
        }else{
                authenticate()
            }
        }

        edit_phoneno.setOnClickListener {
            textView23.visibility = View.VISIBLE
            type_phoneno.visibility = View.VISIBLE
            getotp_btn.visibility = View.VISIBLE
            textView27.visibility = View.INVISIBLE
            pinEntryEditText.visibility = View.INVISIBLE
            verify_otp.visibility = View.INVISIBLE
            resend_otp.visibility = View.INVISIBLE
            edit_phoneno.visibility = View.INVISIBLE
        }

        resend_otp.setOnClickListener {
            if(isConnected()==false){
                Toast.makeText(context,"Please Connect To The Internet! You are disconnected",Toast.LENGTH_LONG).show()
            }else{
                resendCode()
            }
        }
    }

    fun isConnected(): Boolean{
         connectivity = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(connectivity!=null){
            info = connectivity?.activeNetworkInfo
            if (info!= null){
                if (info?.state == NetworkInfo.State.CONNECTED){
                    return true
                }
            }
        }
        return false
    }

    fun verify(){

        val phoneNo = type_phoneno.text.toString()
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

    fun resendCode(){

        val phoneNo = type_phoneno.text.toString()
        verificationcallbacks()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNo,
            60,
            TimeUnit.SECONDS,
            activity!!,
            mcallbacks,
            resendToken)
    }

    fun verificationcallbacks()
    {
        mcallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneCredential(credential)
                Log.d("codeText","Functon Called")
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
            override fun onCodeSent(verificationId: String,token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationId,token)
                verificationid = verificationId
                resendToken = token
                Log.d("this1","Code Sent")
                Log.d("codeText",verificationId)
                //verify_otp_btn.isEnabled = true
            }
        }
    }

    fun authenticate(){

        val code = pinEntryEditText.text.toString()
        Log.d("codeText",code)
        if(code.isEmpty()){
            Toast.makeText(context,"Please Fill the OTP",Toast.LENGTH_SHORT).show()
            return
        }
        val credential = PhoneAuthProvider.getCredential(verificationid,code)
        signInWithPhoneCredential(credential)
    }

    fun signInWithPhoneCredential(credential: PhoneAuthCredential){
        loadanim.visibility = View.VISIBLE
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if(task.isSuccessful){
                    Log.d("this1","Successful")
                    val intent = Intent(context,MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or (Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else{
                    loadanim.visibility = View.INVISIBLE
                    if (task.exception is FirebaseAuthInvalidCredentialsException){
                        loadanim.visibility = View.INVISIBLE
                        Toast.makeText(context,"Invalid OTP Entered", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}