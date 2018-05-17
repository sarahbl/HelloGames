package fr.epita.hellogames

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(),  View.OnClickListener {

    val data = arrayListOf<GameObject>()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button5.setOnClickListener(this@MainActivity)
        button6.setOnClickListener(this@MainActivity)
        button7.setOnClickListener(this@MainActivity)
        button8.setOnClickListener(this@MainActivity)


        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"

        val jsonConverter = GsonConverterFactory.create()
        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(jsonConverter)
                .build()

        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val callback = object : Callback<List<GameObject>> {
            override fun onFailure(call: Call<List<GameObject>>?, t: Throwable?) {
                // Code here what happens if calling the WebService fails
                Log.d("TAG", "WebService call failed")
            }

            override fun onResponse(call: Call<List<GameObject>>?,
                                    response: Response<List<GameObject>>?) {
                // Code here what happens when WebService responds
                if (response != null) {
                    if (response.code() == 200) {
                        val responseData = response.body()
                        if (responseData != null) {
                            data.addAll(responseData)
                            Log.d("TAG", "WebService success : " + data.size)
                            button5.text = data[1].name
                            button6.text = data[0].name
                            button7.text = data[3].name
                            button8.text = data[2].name

                        }
                    }
                }
            }
        }
        service.listToDos().enqueue(callback)

    }
        override fun onClick(v: View?) {

            if (v != null) {

                when (v.id) {
                    R.id.button5 -> {
                        val explicitIntent = Intent(this, scdactivity::class.java)
                        explicitIntent.putExtra("game_id", data[1].id.toString())
                        startActivity(explicitIntent)
                    }
                    R.id.button6 -> {
                        val explicitIntent = Intent(this, scdactivity::class.java)
                        explicitIntent.putExtra("game_id", data[0].id.toString())
                        startActivity(explicitIntent)
                    }
                    R.id.button7 -> {
                        val explicitIntent = Intent(this, scdactivity::class.java)
                        explicitIntent.putExtra("game_id", data[3].id.toString())
                        startActivity(explicitIntent)
                    }
                    R.id.button8 -> {
                        val explicitIntent = Intent(this, scdactivity::class.java)
                        explicitIntent.putExtra("game_id", data[2].id.toString())
                        startActivity(explicitIntent)
                    }
                    else -> {
                        // In theory, you should never reach this line
                    }

                }
            }
        }

}
