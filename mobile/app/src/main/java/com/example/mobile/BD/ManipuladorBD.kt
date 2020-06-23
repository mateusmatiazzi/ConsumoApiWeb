package com.example.mobile.BD

import android.content.Context
import com.example.mobile.api.ApiDataLivros
import com.example.mobile.modelo.Autor
import com.example.mobile.modelo.Livro
import io.realm.Realm
import io.realm.kotlin.where
import retrofit2.Response

class ManipuladorBD(context: Context) {

    var realm : Realm

    init {
        Realm.init(context)
        realm = Realm.getDefaultInstance()
    }

    fun armazenaAutoresNoBancoDeDados(response: Response<List<ApiDataLivros>>) {
        realm.beginTransaction()
        for (autorNaApi in response.body()!!) {
                realm.insertOrUpdate(
                    Autor(
                        autorId = autorNaApi.autorId,
                        nomeDoAutor = autorNaApi.nomeDoAutor
                    )
                )
        }
        realm.commitTransaction()
    }

    fun armazenaLivrosNoBancoDeDados(response: Response<List<ApiDataLivros>>) {
        realm.beginTransaction()
        var autorDoLivroASerAdicionado: Autor

        for (livroNaApi in response.body()!!) {
            autorDoLivroASerAdicionado = realm.where<Autor>().equalTo("nomeDoAutor", livroNaApi.nomeDoAutor).findFirst()!!
            autorDoLivroASerAdicionado.livrosPublicados.add(
                Livro(
                    livroId = livroNaApi.livroId,
                    titulo = livroNaApi.titulo,
                    dataDePublicacao = livroNaApi.dataDePublicacao
                )
            )
        }
        realm.commitTransaction()
    }

    fun imprimeAutoresELivros() : String {
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

}