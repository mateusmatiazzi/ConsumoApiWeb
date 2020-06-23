package com.example.mobile

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Autor (
    @PrimaryKey
    var autorId: Long = 0,
    var nomeDoAutor : String = "",

    var livrosPublicados: RealmList<Livro> = RealmList()
) : RealmObject()