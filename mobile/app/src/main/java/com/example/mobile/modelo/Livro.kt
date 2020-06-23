package com.example.mobile.modelo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Livro (
    @PrimaryKey
    var livroId : Int = 0,
    var titulo : String = "",
    var dataDePublicacao : String = ""

) : RealmObject()