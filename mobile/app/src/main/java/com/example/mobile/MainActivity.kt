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
                textView.text = imprimeAutoresELivros(realm)
            }

            override fun onResponse(call: Call<List<ApiDataLivros>>, response: Response<List<ApiDataLivros>>) {
                if (response.isSuccessful) {
                    if(realm.isEmpty){
                        armazenaAutoresNoBancoDeDados(realm, response)
                        armazenaLivrosNoBancoDeDados(realm, response)
                    }
                    else{
                        textView.text = imprimeAutoresELivros(realm)
                    }

                }
            }
        })

    }

    private fun imprimeAutoresELivros(realm: Realm) : String {
        val todosAutoresArmazenados = realm.where<Autor>().findAll()
        var listaDeAutoreEPublicacoes : String = ""

        for(autor in todosAutoresArmazenados){
            listaDeAutoreEPublicacoes += " ${autor.nomeDoAutor} publicou: \n"
            for (livroPublicado in autor.livrosPublicados){
                listaDeAutoreEPublicacoes += "${livroPublicado.titulo}, em ${livroPublicado.dataDePublicacao}\n"
            }
            listaDeAutoreEPublicacoes += "\n"
        }
        return listaDeAutoreEPublicacoes
    }

    private fun armazenaAutoresNoBancoDeDados(realm: Realm, response: Response<List<ApiDataLivros>>) {
        val nomeDosAutores = mutableListOf<String>()
        realm.beginTransaction()

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
        realm.beginTransaction()
        var autorDoLivroASerAdicionado: Autor

        for (livroNaApi in response.body()!!) {
            autorDoLivroASerAdicionado = realm.where<Autor>().equalTo("nomeDoAutor", livroNaApi.nomeDoAutor).findFirst()!!
            autorDoLivroASerAdicionado.livrosPublicados.add(
                Livro(livroId = livroNaApi.livroId,
                      titulo = livroNaApi.titulo,
                      dataDePublicacao = livroNaApi.dataDePublicacao))
        }
        realm.commitTransaction()
    }

}