package com.dbsrm.covid19tracker

import android.Manifest.permission.CALL_PHONE
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home.*
import java.util.jar.Manifest
import kotlin.concurrent.fixedRateTimer

class Home: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home,container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadinganim2.visibility = View.INVISIBLE

        donatePMCaresFund_btn.setOnClickListener {
            loadinganim2.visibility = View.VISIBLE
            val r = Runnable{
                val donatefunds = DonatePMCaresFund()
                fragmentManager?.beginTransaction()?.replace(R.id.container, donatefunds)?.commit()
            }
            Handler().postDelayed(r,2000)
        }

        callnow_btn.setOnClickListener {
            val phoneIntent = Intent(Intent.ACTION_DIAL)
            phoneIntent.setData(Uri.parse("tel:+91-11-23978046"))
            startActivity(phoneIntent)
        }

        take_test_button.setOnClickListener {
            val selfAssess = SelfAssess()
            fragmentManager?.beginTransaction()?.replace(R.id.container,selfAssess)?.commit()
        }
    }
}