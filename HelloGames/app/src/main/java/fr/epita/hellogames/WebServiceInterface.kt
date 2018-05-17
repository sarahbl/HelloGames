package fr.epita.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceInterface
{
        @GET("game/list")
        fun listToDos(): Call<List<GameObject>>

        @GET("game/details")
        fun listDetails(@Query("game_id") id : Int): Call<GameDetails>

}