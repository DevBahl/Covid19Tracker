package com.dbsrm.covid19tracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.data_row1.view.*
import kotlinx.android.synthetic.main.main_data_card.view.*
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), ChipNavigationBar.OnItemSelectedListener {


    var bottomNavigationView: ChipNavigationBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.chipNavigationBar)
        bottomNavigationView!!.setOnItemSelectedListener(this)
        bottomNavigationView!!.setItemSelected(R.id.home)
    }

    var homeFragment = Home()
    var trackerFragment = Tracker()
    var selfassessFragment = SelfAssess()
    var safetyFragment = Safety()
    var aboutFragment = About()

    override fun onItemSelected(id: Int) {
        when (id) {
            R.id.home -> {
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, homeFragment).commit()
            }
            R.id.tracker -> {
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, trackerFragment).commit()
            }
            R.id.self_assess -> {
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, selfassessFragment).commit()
            }
            R.id.safety -> {
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, safetyFragment).commit()
            }
            R.id.about -> {
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, aboutFragment).commit()
            }
        }
    }
}
