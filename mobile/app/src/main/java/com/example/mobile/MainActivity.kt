package com.example.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val ipDoServidor = "http://192.168.0.100"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getData()
    }

    fun getData() {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance(ipDoServidor)

        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getLivros()

        Realm.init(this)
        val realm = Realm.getDefaultInstance()

        callback.enqueue(object : Callback<List<ApiDataLivros>> {
            override fun onFailure(call: Call<List<ApiDataLivros>>, t: Throwable) {

                val quantidadeDeAutoresArmazenados = realm.where<Autor>().findAll()

                textView.text = quantidadeDeAutoresArmazenados.toString()
            }

            override fun onResponse(call: Call<List<ApiDataLivros>>, response: Response<List<ApiDataLivros>>) {
                if (response.isSuccessful) {
                    if(realm.isEmpty){
                        armazenaAutoresNoBancoDeDados(realm, response)
                        armazenaLivrosNoBancoDeDados(realm, response)
                    }
                    else{
                        val quantidadeDeAutoresArmazenados = realm.where<Autor>().findAll()
                        textView.text = quantidadeDeAutoresArmazenados.toString()
                    }

                }
            }
        })

    }

    private fun armazenaAutoresNoBancoDeDados(realm: Realm, response: Response<List<ApiDataLivros>>) {
        val nomeDosAutores = mutableListOf<String>()
        realm.beginTransaction()

        //armazena o nome dos autores
        var autorASerAdicionado: Autor
        for (autorNaApi in response.body()!!) {
            if (!nomeDosAutores.contains(autorNaApi.nomeDoAutor)) {

                autorASerAdicionado = realm.createObject<Autor>(autorNaApi.autorId)
                autorASerAdicionado.nomeDoAutor = autorNaApi.nomeDoAutor

                nomeDosAutores.add(autorNaApi.nomeDoAutor)
            }
        }
        realm.commitTransaction()
    }

    private fun armazenaLivrosNoBancoDeDados(realm: Realm, response: Response<List<ApiDataLivros>>) {
        //armazena o nome dos livros
        realm.beginTransaction()

        var livroASerAdicionado: Livro
        var autorDoLivroASerAdicionado: Autor

        for (livroNaApi in response.body()!!) {
            livroASerAdicionado = realm.createObject<Livro>(livroNaApi.livroId)
            livroASerAdicionado.titulo = livroNaApi.titulo
            livroASerAdicionado.dataDePublicacao = livroNaApi.dataDePublicacao

            autorDoLivroASerAdicionado = realm.where<Autor>().equalTo("nomeDoAutor", livroNaApi.nomeDoAutor).findFirst()!!
            autorDoLivroASerAdicionado.livrosPublicados.add(livroASerAdicionado)
        }
        realm.commitTransaction()
    }

}