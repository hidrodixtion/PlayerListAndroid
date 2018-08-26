package com.projectbox.playerlistandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.projectbox.playerlistandroid.adapter.ItemAdapter
import com.projectbox.playerlistandroid.model.TeamPlayer
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondActivity : AppCompatActivity() {

    lateinit var adapter: ItemAdapter
    lateinit var items: List<TeamPlayer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        initData()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun initUI() {
        adapter = ItemAdapter(emptyList())

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("team")
    }

    private fun initData() {
        val team = intent.getStringExtra("team").toLowerCase()

        ServiceHelper.service.getPlayer(team).enqueue(object: Callback<List<TeamPlayer>> {
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
}
