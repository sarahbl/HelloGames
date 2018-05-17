package fr.epita.hellogames

data class GameDetails(val name : String, val type : String,
                       val players : Int, val year : Int,
                       val description_en: String, val url : String)