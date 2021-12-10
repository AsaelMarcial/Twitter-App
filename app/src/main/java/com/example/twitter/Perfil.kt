package com.example.twitter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_perfil.*
import kotlinx.android.synthetic.main.fragment_buscar.*
import kotlinx.android.synthetic.main.fragment_perfil.*
import models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Perfil : AppCompatActivity() {

    var id : String? = ""
    var token : String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        val extras = intent.extras
        id = extras?.getString("id")
        token = extras?.getString("token")
        println("id desde principal: "+ id + token)
        loadUser(id)
        loadTweets(token,id)

        btnSeguir.setOnClickListener {
            seguir(token,id)
        }
        btnDejarSeguir.setOnClickListener {
            unfollow(token,id)
        }

    }

    fun loadUser(id : String?){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/{idUsuario}/mostrarCuenta/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val response = service.mostarCuenta(id!!.toInt())
        response.enqueue(object : Callback<MostrarCuentaResponse>{
            override fun onResponse(
                call: Call<MostrarCuentaResponse>,
                response: Response<MostrarCuentaResponse>
            ) {
                if(response.isSuccessful){
                    val obj : MostrarCuentaResponse? = response.body()
                    userName.text = obj?.nombre + " " + obj?.apellidos
                    userInfo.text = obj?.info
                    userSeguidores.text = "Usuraio desde " +obj?.created_at.toString().subSequence(0,10)
                }
            }

            override fun onFailure(call: Call<MostrarCuentaResponse>, t: Throwable) {
                println("Error" + t.message)
            }

        })

    }

    fun loadTweets(token : String?, id: String?){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/{idUsuario}/getPostsPerfilUsuario/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)
        val response = service.getPostsUsuario("Bearer " + token,id!!.toInt())
        response.enqueue(object : Callback<BuscarResponse>{
            override fun onResponse(call: Call<BuscarResponse>, response: Response<BuscarResponse>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    val list = response.body()!!
                    println("respuesta:" + response.body())
                    recycler_user.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@Perfil)
                        adapter = BusquedaAdapter(response.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<BuscarResponse>, t: Throwable) {
                println("respuesta:" + t.message)
            }

        })

    }

    fun seguir(token: String?, id: String?){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/{idSeguido}/seguirUsuario/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)
        val response = service.seguirUsuario("Bearer " + token,id!!.toInt())
        response.enqueue(object : Callback<MessageResponse>{
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if(response.isSuccessful){
                    println("Respuesta: " + response.body())
                    btnSeguir.visibility = View.GONE
                    btnDejarSeguir.visibility = View.VISIBLE
                    Toast.makeText(this@Perfil, "Usuario seguido con exito", Toast.LENGTH_SHORT).show()
                }else{
                    println("Respuesta: " + response.body())
                    Toast.makeText(this@Perfil, "Intentalo mas tarde", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Toast.makeText(this@Perfil, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun unfollow(token: String?, id: String?){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/{idSeguido}/unfollowUsuario/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)
        val response = service.unfollow("Bearer " + token,id!!.toInt())
        response.enqueue(object : Callback<MessageResponse>{
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(this@Perfil, response.body().toString(), Toast.LENGTH_SHORT).show()
                    btnSeguir.visibility = View.VISIBLE
                    btnDejarSeguir.visibility = View.GONE
                }else{
                    Toast.makeText(this@Perfil, response.body().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Toast.makeText(this@Perfil, t.message, Toast.LENGTH_SHORT).show()

            }

        })
    }
}