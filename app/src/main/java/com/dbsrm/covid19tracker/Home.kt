package com.dbsrm.covid19tracker

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import hotchemi.stringpicker.StringPicker
import hotchemi.stringpicker.StringPickerDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.recycler_home.*
import kotlin.concurrent.fixedRateTimer

class Home: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home,container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        donatePMCaresFund_btn.setOnClickListener {
            val donatefunds = DonatePMCaresFund()
            fragmentManager?.beginTransaction()?.replace(R.id.container, donatefunds)?.commit()
        }

      /*  val dialog = StringPickerDialog()
        val bundle = Bundle()
        val values = arrayOf("a", "b", "c", "d", "e", "f")
        bundle.putStringArray(getString(R.string.string_picker_dialog_values), values)
        dialog.arguments = bundle
        dialog.show(fragmentManager!!, ContentValues.TAG)

        StringPickerDialog.OnClickListener{
            fun onClick() {
                stateChosen.setText(it)
            }
        }*/
    }
}