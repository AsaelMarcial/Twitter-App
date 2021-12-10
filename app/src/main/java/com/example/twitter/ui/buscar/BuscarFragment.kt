package com.example.twitter.ui.buscar

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitter.ApiService
import com.example.twitter.BusquedaAdapter
import com.example.twitter.PostAdapter
import com.example.twitter.R
import kotlinx.android.synthetic.main.fragment_buscar.*
import kotlinx.android.synthetic.main.fragment_inicio.*
import layout.BuscarUsuarioResponse
import models.BuscarResponse
import models.MostrarCuentaResponse
import models.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BuscarFragment : Fragment(R.layout.fragment_buscar) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = requireArguments().getString("token")
        println("Token desde buscar: " + token)
        cargarTweets(token);

        btnBuscar.setOnClickListener {
            buscarUsuarios(txtBuscar.text.toString())
            buscarPublicacion(txtBuscar.text.toString())
        }
    }

    private fun cargarTweets(token: String?) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/allPosts/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)
        val response = service.allPosts()
        response.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    val list = response.body()!!
                    Log.d("Response", "size : ${list.size}")
                    println("response:" + response.body())
                    recycler_buscarTexto.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(activity)
                        adapter = PostAdapter(response.body()!!)
                    }

                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                println("response:" + t.message)
            }

        })
    }

    private fun buscarUsuarios(texto: String) {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/{username}/buscarUsuario/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)

        val response = service.buscarUsuario(texto)
        response.enqueue(object : Callback<BuscarUsuarioResponse>{
            override fun onResponse(
                call: Call<BuscarUsuarioResponse>,
                response: Response<BuscarUsuarioResponse>
            ) {
                if(response.isSuccessful){
                    val obj : BuscarUsuarioResponse? = response.body()
                    loadUsuarios(obj?.id.toString())
                }else{
                    println("response:" + response.message())

                }
            }

            override fun onFailure(call: Call<BuscarUsuarioResponse>, t: Throwable) {
                println("response:" + t.message)
                txNombre.hint = "El usuario no existe"

            }

        })
    }

    private fun loadUsuarios(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/{palabra}/buscarPublicacion/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val response = service.mostarCuenta(id.toInt())
        response.enqueue(object : Callback<MostrarCuentaResponse>{
            override fun onResponse(call: Call<MostrarCuentaResponse>, response: Response<MostrarCuentaResponse>) {
                if(response.isSuccessful){
                    val obj : MostrarCuentaResponse? = response.body()
                    txNombre.text = obj?.nombre + " " + obj?.apellidos
                    txInfo.text = obj?.info
                }else{
                    println("response:" + response.message())
                }

            }

            override fun onFailure(call: Call<MostrarCuentaResponse>, t: Throwable) {
                println("response:" + t.message)
            }

        })


    }

    private fun buscarPublicacion(texto: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/{palabra}/buscarPublicacion/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val response = service.buscarPalabra(texto)
        response.enqueue(object : Callback<BuscarResponse>{
            override fun onResponse(call: Call<BuscarResponse>, response: Response<BuscarResponse>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    val list = response.body()!!
                    println("respuesta:" + response.body())
                    recycler_buscarTexto.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(activity)
                        adapter = BusquedaAdapter(response.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<BuscarResponse>, t: Throwable) {
                println("respuesta:" + t.message)
            }

        })


    }



}