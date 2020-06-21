package com.example.mobile

import retrofit2.Call
import retrofit2.http.GET

interface Endpoint {

    @GET("/desafio-mobile/api/livros/retornaLivros.php")
    fun getLivros() : Call<List<Livros>>
}