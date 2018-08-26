package com.projectbox.playerlistandroid

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by adinugroho
 */
object ServiceHelper {
    lateinit var service: IService

    init {
        initService()
    }

    private fun initService() {
        service = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://hidrodixtion.github.io/example-json-api/")
                .build().create(IService::class.java)
    }
}