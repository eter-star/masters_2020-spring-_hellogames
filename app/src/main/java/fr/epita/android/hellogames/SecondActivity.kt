package fr.epita.android.hellogames

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_second.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@GlideModule
class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var gameId = intent.getIntExtra("gameId",1)
        println(gameId)

        var data: ToDoObject
        val baseURL = "http://androidlessonsapi.herokuapp.com/api/game/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        val service = retrofit.create(WSInterface::class.java)

        val callback: Callback<ToDoObject> = object : Callback<ToDoObject> {
            override fun onFailure(call: Call<ToDoObject>, t: Throwable) {
                Log.d("xxx", "Prob with ws")
            }

            override fun onResponse(
                call: Call<ToDoObject>,
                response: Response<ToDoObject>
            ) {
                Log.d("xxx", "WS OK in second activity")
                if (response.code() == 200) {
                    data =  response.body()!!
                    var imageUrl : String = data.picture
                    println(imageUrl)
                    Glide.with(this@SecondActivity).load(imageUrl).into(imageView)
                    descrptionView.text = data.description_en
                    knowMore.setOnClickListener {
                        val url = data.url
                        val implicitIntent : Intent = Intent(Intent.ACTION_VIEW)
                        implicitIntent.data = Uri.parse(url)
                        startActivity(implicitIntent)
                    }
                    nameVarView.text = data.name
                    typeVarView.text = data.type
                    playerVarView.text = data.players.toString()
                    yearVarView.text = data.year.toString()

                  }
            }
        }

        service.getDetailsById(gameId).enqueue(callback)
    }
}