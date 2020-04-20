package com.dbsrm.covid19tracker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.data_row1.view.*
import kotlinx.android.synthetic.main.main_data_card.view.*
import okhttp3.*
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchJson()

    }

    class AddData(val feed: Feed) : Item<GroupieViewHolder>() {

        override fun getLayout(): Int {
            return R.layout.main_data_card
        }

        override fun bind(viewHolder: GroupieViewHolder, position: Int) {

            val details = feed.statewise[0]
            Log.d("data1", details.state)
            viewHolder.itemView.total_cases.text = details.confirmed
            viewHolder.itemView.active_cases.text = details.active
            viewHolder.itemView.recovered_cases.text = details.recovered
            viewHolder.itemView.deceased_cases.text = details.deaths
        }

    }

    val adapter = GroupAdapter<GroupieViewHolder>()

    /*  class Totaldata(): Item<GroupieViewHolder>(){

        override fun getLayout(): Int {
            return R.layout.data_row1
        }

        override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        }

    }*/
    private fun fetchJson() {
        val url = "https://api.covid19india.org/data.json"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to fetch")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)
                val gson = GsonBuilder().create()
                val feed = gson.fromJson(body, Feed::class.java)

                runOnUiThread {
                    recyclerViewMain.adapter = adapter
                    //  adapter.add(Totaldata())
                    adapter.add(AddData(feed))
                }
            }
        })
    }
}


class Feed(val statewise: List<Details>)

class Details(val active:String, val confirmed: String, val deaths: String, val lastupdatedtime: String, val recovered: String, val state: String)

