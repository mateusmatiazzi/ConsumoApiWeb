package com.example.mobile.modelo

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Autor (
    @PrimaryKey
    var autorId: Int = 0,
    var nomeDoAutor : String = "",

    var livrosPublicados: RealmList<Livro> = RealmList()
) : RealmObject()