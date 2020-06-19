package com.example.mobile

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "http://localhost/desafio-mobile/api/livros/retornaLivros.php"

        AsyncTaskHandleJson().execute(url)
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>(){
        override fun doInBackground(vararg url: String?): String {
            var text: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try{
                connection.connect()
                text = connection.inputStream.use{ it.reader().use{reader -> reader.readText()} }
            }finally {
                connection.disconnect()
            }
            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }

    }

    private fun handleJson(jsonString : String?) {
        val jsonArray = JSONArray(jsonString)

        val list = ArrayList<Livro>()

        var x = 0

        while(x < jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(x)

            list.add(Livro(
                jsonObject.getInt("livroId"),
                jsonObject.getInt("autorId"),
                jsonObject.getString("titulo"),
                jsonObject.getString("dataDePublicacao"),
                jsonObject.getString("nomeDoAutor")
            ))
            x++
        }
        val adapter = AdaptadorDeLista(this, list)
        livros_list.adapter = adapter
    }

}