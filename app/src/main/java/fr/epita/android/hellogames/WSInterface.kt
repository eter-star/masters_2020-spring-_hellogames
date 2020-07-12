package fr.epita.android.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryName

interface WSInterface {

    @GET("list")
    fun getTodoList() : Call<List<GameObject>>

    @GET("list")
    fun getTodosById(@Query("id") id : Int) : Call<List<GameObject>>

    @GET("details")
    fun getDetailsById(@Query("game_id") game_id: Int): Call<ToDoObject>
}