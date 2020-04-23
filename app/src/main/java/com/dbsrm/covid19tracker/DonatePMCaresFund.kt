package com.dbsrm.covid19tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.donatepmcaresfundwebview.*
import kotlinx.android.synthetic.main.home.*

class DonatePMCaresFund: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v:View = inflater.inflate(R.layout.donatepmcaresfundwebview,container,false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webV.settings.javaScriptEnabled
        webV.webViewClient = WebViewClient()
        webV.loadUrl("https://www.pmindia.gov.in/en/about-pm-cares-fund/")
    }
}