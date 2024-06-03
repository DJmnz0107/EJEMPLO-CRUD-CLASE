package modelo

import java.util.UUID

data class dataClassMusica(
    val uuid: String,
    val nombreCancion:String,
    val duracion:Int,
    var autor:String

)
