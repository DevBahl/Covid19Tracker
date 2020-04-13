package com.dbsrm.covid19tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.data_row1.view.*
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewMain.adapter = adapter

        fetchJson()

        latestMessagesMap.values.forEach {
            adapter.add(adddata(it))
        }
    }

    class adddata(val details: Details): Item<GroupieViewHolder>(){

        override fun getLayout(): Int {
            return R.layout.data_row1
        }

        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.itemView.dataView1.text = details.state
        }

    }
    val latestMessagesMap = HashMap<String, Details>()
    val adapter = GroupAdapter<GroupieViewHolder>()

    private fun fetchJson() {
        val url = "https://api.covid19india.org/data.json"
        val request = Request.Builder().url(url).build()
        val client =  OkHttpClient.Builder()
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
                val feed = gson.fromJson(body,Feed::class.java)
            }
        })
    }
}

class Feed(val statewise: List<Details>)

class Details(val active:Int,val confirmed: Int, val deaths: Int,val lastupdatedtime: String,val recovered: Int,val state: String)