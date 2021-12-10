package com.example.twitter.ui.perfil

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitter.ApiService
import com.example.twitter.PostAdapter
import com.example.twitter.R
import kotlinx.android.synthetic.main.fragment_inicio.*
import kotlinx.android.synthetic.main.fragment_perfil.*
import models.Post
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
            loadProfile(token)
            loadTweets(token)


        btnSeguir.setOnClickListener {


        }

    }

    private fun loadProfile(token : String?){
        btnSeguir.visibility = View.INVISIBLE
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/misPosts/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val response = service.getUser("Bearer " + token)
        response.enqueue(object : Callback<Usuario>{
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful){
                    var cuenta : Usuario? = response.body()
                    name.text = cuenta?.nombre + " " +cuenta?.apellidos
                    info.text = cuenta?.info
                    seguidores.text = "Usuraio desde " + cuenta?.createdAt?.subSequence(0,10)
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                println("Error" + t.message)
            }

        })

    }

    fun loadTweets(token: String?) {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/misPosts/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)
        val response = service.getMisPost("Bearer " + token)

        response.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    val list = response.body()!!
                    Log.d("Response", "size : ${list.size}")
                    println("response:" + response.body())
                    recycler_perfil.apply {
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

}