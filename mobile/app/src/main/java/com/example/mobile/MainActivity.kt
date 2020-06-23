package com.example.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile.BD.ManipuladorBD
import com.example.mobile.api.ApiDataLivros
import com.example.mobile.api.Endpoint
import com.example.mobile.api.NetworkUtils
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

        val manipuladorBd : ManipuladorBD =
            ManipuladorBD(this)

        callback.enqueue(object : Callback<List<ApiDataLivros>> {
            override fun onFailure(call: Call<List<ApiDataLivros>>, t: Throwable) {
                textView.text = manipuladorBd.imprimeAutoresELivros()
            }

            override fun onResponse(call: Call<List<ApiDataLivros>>, response: Response<List<ApiDataLivros>>) {
                if (response.isSuccessful) {
                    manipuladorBd.armazenaAutoresNoBancoDeDados(response)
                    manipuladorBd.armazenaLivrosNoBancoDeDados(response)
                    textView.text = manipuladorBd.imprimeAutoresELivros()
                }
            }
        })

    }
}