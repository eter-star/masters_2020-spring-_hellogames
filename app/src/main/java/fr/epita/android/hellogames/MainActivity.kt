 package fr.epita.android.hellogames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

 @GlideModule
 class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val randomList = (0..9).shuffled().take(4)

        var data : List<GameObject>
        var baseURL = "http://androidlessonsapi.herokuapp.com/api/game/"
        var jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        var retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        var service = retrofit.create(WSInterface::class.java)
        val callback: Callback<List<GameObject>> = object : Callback<List<GameObject>> {
            override fun onFailure(call: Call<List<GameObject>>, t: Throwable) {
                Log.d("xxx", "Prob with ws")
            }

            override fun onResponse(
                call: Call<List<GameObject>>,
                response: Response<List<GameObject>>
            ) {
                Log.d("xxx", "WS OK")
                if (response.code() == 200) {
                    data =  response.body()!!
                    var imageUrl : String = data.get(randomList[0]).picture
                    Glide.with(applicationContext).load(imageUrl).into(imageView1)
                    imageView1.setOnClickListener {
                        var explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                        var id : Int = data.get(randomList[0]).id
                        explicitIntent.putExtra("gameId", id)
                        startActivity(explicitIntent)
                    }
                    imageUrl = data.get(randomList[1]).picture
                    Glide.with(applicationContext).load(imageUrl).into(imageView2)
                    imageView2.setOnClickListener {
                        var explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                        var id : Int = data.get(randomList[1]).id
                        explicitIntent.putExtra("gameId", id)
                        startActivity(explicitIntent)
                    }
                    imageUrl = data.get(randomList[2]).picture
                    Glide.with(applicationContext).load(imageUrl).into(imageView3)
                    imageView3.setOnClickListener {
                        var explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                        var id : Int = data.get(randomList[2]).id
                        explicitIntent.putExtra("gameId", id)
                        startActivity(explicitIntent)
                    }
                    imageUrl = data.get(randomList[3]).picture
                    Glide.with(applicationContext).load(imageUrl).into(imageView4)
                    imageView4.setOnClickListener {
                        var explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                        var id : Int = data.get(randomList[3]).id
                        explicitIntent.putExtra("gameId", id)
                        startActivity(explicitIntent)
                    }
                }
            }
        }

        service.getTodoList().enqueue(callback)



    }
}