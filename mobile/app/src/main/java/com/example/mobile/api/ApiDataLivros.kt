package com.example.mobile.api

import com.google.gson.annotations.SerializedName

data class ApiDataLivros(
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