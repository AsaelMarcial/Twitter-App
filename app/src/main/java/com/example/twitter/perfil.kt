package com.example.twitter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import models.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class perfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val bundle = intent.extras
        val token = bundle?.getString("token")
        println("Perfil: "+token)
    }

    private fun loadProfile(token: String) {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/getCuenta/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        println(token)
        val service = retrofit.create(ApiService::class.java)
        val response = service.getUser("Bearer " + token)

        response.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    val body: Usuario? = response.body()
                    println(body?.nombre)
                }
            }
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                println("Error" + t.message)
            }
        })
    }
}