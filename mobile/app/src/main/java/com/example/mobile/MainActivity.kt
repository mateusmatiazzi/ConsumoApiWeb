package com.example.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
                    var nomeDosAutores = mutableListOf<String>()
                    realm.beginTransaction()

                    //armazena o nome dos autores
                    var autor : Autor
                    for (i in response.body()!!){
                        if(!nomeDosAutores.contains(i.nomeDoAutor)){

                            autor = realm.createObject<Autor>(i.autorId)
                            autor.nomeDoAutor = i.nomeDoAutor

                            nomeDosAutores.add(i.nomeDoAutor)
                        }
                    }
                    realm.commitTransaction()

                    //armazena o nome dos livros
                    realm.beginTransaction()
                    var livro : Livro
                    for (i in response.body()!!){
                        livro = realm.createObject<Livro>(i.livroId)
                        livro.titulo = i.titulo
                        livro.dataDePublicacao = i.dataDePublicacao

                        autor = realm.where<Autor>().equalTo("nomeDoAutor", i.nomeDoAutor).findFirst()!!
                        autor.livrosPublicados.add(livro)
                    }
                    realm.commitTransaction()

                    val quantidadeDeAutoresArmazenados = realm.where<Autor>().findAll()

                    textView.text = quantidadeDeAutoresArmazenados.toString()

                }
                else{
                    textView.text = "Nenhum livro encontrado"
                }
            }
        })

    }

}