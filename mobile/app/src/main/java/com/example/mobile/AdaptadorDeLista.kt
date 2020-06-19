package com.example.mobile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.widget.AppCompatTextView
import java.util.ArrayList

class AdaptadorDeLista (val context: Context, var list: ArrayList<Livro>) : BaseAdapter() {
    override fun getView(posicao: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_linha, parent, false)

        val livroId = view.findViewById(R.id.livro_livroId) as AppCompatTextView
        val autorId = view.findViewById(R.id.livro_autorId) as AppCompatTextView
        val livroTitulo = view.findViewById(R.id.livro_titulo) as AppCompatTextView
        val livroDataDePublicacao = view.findViewById(R.id.livro_dataDePublicacao) as AppCompatTextView
        val livroNomeDoAutor = view.findViewById(R.id.livro_nomeDoAutor) as AppCompatTextView

        return view
    }

    override fun getItem(posicao: Int): Any {
        return list[posicao]
    }

    override fun getItemId(posicao: Int): Long {
        return posicao.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}