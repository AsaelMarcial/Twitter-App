package com.example.twitter.ui.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitter.*
import kotlinx.android.synthetic.main.fragment_inicio.*
import models.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class InicioFragment : Fragment(R.layout.fragment_inicio) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = requireArguments().getString("token")
        println("Token desde Home: " + token)

        loadTweets(token)


        btnAddTweet.setOnClickListener { view ->
            showNoticeDialog()
        }
    }

    /*private fun initRecyclerView(){
        tweet_recycler.apply {
            layoutManager = LinearLayoutManager(activity)
            val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            adapter = PostAdapter()
        }
    }*/

    fun loadTweets(token: String?) {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/getPostSeguidos/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)
        val response = service.getPostsSeguidos("Bearer " + token)

        response.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    val list = response.body()!!
                    Log.d("Response", "size : ${list.size}")
                    println("response:" + response.body())
                    tweet_recycler.apply {
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

    fun showNoticeDialog() {
        val dialog = NoticeDialogFragment()
        dialog.show((activity as FragmentActivity).supportFragmentManager, "NoticeDialogFragment")
    }


}


