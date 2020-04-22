package com.dbsrm.covid19tracker

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.data_row1.view.*
import kotlinx.android.synthetic.main.main_data_card.*
import kotlinx.android.synthetic.main.main_data_card.active_cases
import kotlinx.android.synthetic.main.main_data_card.deceased_cases
import kotlinx.android.synthetic.main.main_data_card.recovered_cases
import kotlinx.android.synthetic.main.main_data_card.total_cases
import kotlinx.android.synthetic.main.tracker.*
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


class Tracker: Fragment() {

    var v: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.tracker, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewMain.adapter = adapter
        fetchJson()
    }


    val adapter = GroupAdapter<GroupieViewHolder>()

    class State(val text1: String, val text2: String, val text3: String, val text4: String) :
        Item<GroupieViewHolder>() {

        override fun getLayout(): Int {
            return R.layout.data_row1
        }

        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.itemView.states.text = text1
            viewHolder.itemView.confirmedstates.text = text2
            viewHolder.itemView.recoveredstates.text = text3
            viewHolder.itemView.deceasedstates.text = text4
        }
    }


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

                val handler: Handler = object : Handler(Looper.getMainLooper()) {
                    override fun handleMessage(msg: Message?) {
                        //  adapter.add(AddData(feed))
                        val details = feed.statewise[0]
                        total_cases.text = details.confirmed
                        active_cases.text = details.active
                        recovered_cases.text = details.recovered
                        deceased_cases.text = details.deaths
                        for (i in 1 until feed.statewise.size) {
                            val states = feed.statewise[i].state
                            val confirmed = feed.statewise[i].confirmed
                            val recovered = feed.statewise[i].recovered
                            val deceased = feed.statewise[i].deaths
                            adapter.add(State(states, confirmed, recovered, deceased))
                        }
                    }
                }
                handler.sendEmptyMessage(1)
            }
        })
    }
}


class Feed(val statewise: List<Details>)

class Details(val active:String, val confirmed: String, val deaths: String, val lastupdatedtime: String, val recovered: String, val state: String)

