package com.example.mobile

import android.content.Context
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import retrofit2.Response

class ManipuladorBD(context: Context) {

    lateinit var realm : Realm

    init {
        Realm.init(context)
        realm = Realm.getDefaultInstance()
    }

    public fun armazenaAutoresNoBancoDeDados(response: Response<List<ApiDataLivros>>) {
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

    public fun armazenaLivrosNoBancoDeDados(response: Response<List<ApiDataLivros>>) {
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

    public fun imprimeAutoresELivros() : String {
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

    public fun bancoDeDadosVazio() : Boolean {
        return realm.isEmpty
    }

}