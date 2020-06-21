package com.example.mobile

import com.google.gson.annotations.SerializedName

data class Livros(
    @SerializedName("livroId")
    var livroId : Int,
    @SerializedName("autorId")
    var autorId : Int,
    @SerializedName("titulo")
    var titulo : String,
    @SerializedName("dataDePublicacao")
    var dataDePublicacao : String,
    @SerializedName("nomeDoAutor")
    var nomeDoAutor : String
)