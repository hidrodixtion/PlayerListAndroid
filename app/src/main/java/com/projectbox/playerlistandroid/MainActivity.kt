package com.projectbox.playerlistandroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.projectbox.playerlistandroid.adapter.ItemAdapter
import com.projectbox.playerlistandroid.event.ClickEvent
import com.projectbox.playerlistandroid.model.TeamPlayer
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var adapter: ItemAdapter
    lateinit var items: List<TeamPlayer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        initData()
    }

    private fun initUI() {
        adapter = ItemAdapter(emptyList())

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter

        supportActionBar?.title = "Teams"
    }

    private fun initData() {
        ServiceHelper.service.getTeam().enqueue(object: Callback<List<TeamPlayer>> {
            override fun onFailure(call: Call<List<TeamPlayer>>, t: Throwable) {
                Log.e(MainActivity::class.java.name, t.localizedMessage)
            }

            override fun onResponse(call: Call<List<TeamPlayer>>, response: Response<List<TeamPlayer>>) {
                response.body()?.let {
                    items = it
                    adapter.update(it)
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe
    fun onItemClicked(e: ClickEvent) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("team", items[e.position].name)
        startActivity(intent)
    }
}
