package com.example.twitter.ui.perfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.twitter.ApiService
import com.example.twitter.R
import kotlinx.android.synthetic.main.fragment_perfil.*
import models.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PerfilFragment : Fragment(R.layout.fragment_perfil) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            val token = requireArguments().getString("token")
            println("Token desde perfil: " + token)
    }

    private fun loadProfile(token : String){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/misPosts/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        println(token)
        val service = retrofit.create(ApiService::class.java)
        val response = service.getUser("Bearer " + token)

        response.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    val body: Usuario? = response.body()
                    name.setText(body?.nombre + " " +body?.apellidos)

                }
            }
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                println("Error" + t.message)
            }
        })
    }
}