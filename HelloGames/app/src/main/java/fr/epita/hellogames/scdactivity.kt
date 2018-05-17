package fr.epita.hellogames

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_scdactivity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class scdactivity : AppCompatActivity(), View.OnClickListener {

    var data : GameDetails?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scdactivity)

        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"

        val jsonConverter = GsonConverterFactory.create()
        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(jsonConverter)
                .build()

        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val callback = object : Callback<GameDetails> {
            override fun onFailure(call: Call<GameDetails>?, t: Throwable?) {
                // Code here what happens if calling the WebService fails
                Log.d("TAG", "WebService call failed")
            }

            override fun onResponse(call: Call<GameDetails>?,
                                    response: Response<GameDetails>?) {
                // Code here what happens when WebService responds
                if (response != null) {
                    if (response.code() == 200) {
                        val responseData = response.body()
                        if (responseData != null) {
                            val data = responseData // addAll ???
                            Log.d("TAG", "WebService success : " + data)
                            textView.text = data.name
                            textView3.text = data.type
                            textView4.text = data.players.toString()
                            textView5.text = data.year.toString()
                            textView10.text = data.description_en

                        }
                    }
                }
            }
        }
        val originIntent = intent


        service.listDetails(originIntent.getStringExtra("game_id").toInt()).enqueue(callback)
    }

    override fun onClick(v: View?) {

        if (v != null) {

            when (v.id) {
                R.id.button2 -> {
                    val url = data!!.url.toString()
                    val implicitIntent = Intent(Intent.ACTION_VIEW)
                    implicitIntent.data = Uri.parse(url)
                    startActivity(implicitIntent)

                }
                else -> {
                    // In theory, you should never reach this line
                }
            }
        }
    }
}
