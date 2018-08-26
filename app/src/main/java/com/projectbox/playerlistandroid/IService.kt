package com.projectbox.playerlistandroid

import com.projectbox.playerlistandroid.model.TeamPlayer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by adinugroho
 */
interface IService {
    @GET("team.json")
    fun getTeam(): Call<List<TeamPlayer>>

    @GET("{team}/player.json")
    fun getPlayer(@Path("team") team: String): Call<List<TeamPlayer>>
}