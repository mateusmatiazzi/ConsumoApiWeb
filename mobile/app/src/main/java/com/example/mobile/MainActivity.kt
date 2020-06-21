package com.example.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getData()
    }

    fun getData() {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance("http://192.168.100.105")

        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getLivros()

        callback.enqueue(object : Callback<List<Livros>> {
            override fun onFailure(call: Call<List<Livros>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Livros>>, response: Response<List<Livros>>) {
                textView.text = response.body().toString()
            }
        })

    }

}