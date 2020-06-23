package com.example.mobile.api

import retrofit2.Call
import retrofit2.http.GET

interface Endpoint {

    @GET("/desafio-mobile/api/livros/retornaLivros.php")
    fun getLivros() : Call<List<ApiDataLivros>>
}