package com.example.twitter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import models.MostrarCuentaResponse
import models.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostAdapter(private val tweetList: List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.tweet_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tweetList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Response", "List Count :${tweetList.size} ")
        return holder.bind(tweetList[position])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/{idUsuario}/mostrarCuenta/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)


        var texto = itemView.findViewById<TextView>(R.id.lblTweet)
        var info = itemView.findViewById<TextView>(R.id.txInfo)
        fun bind(tweet: Post) {
            val response = service.mostarCuenta(tweet.idUsuario)
            response.enqueue(object : Callback<MostrarCuentaResponse>{
                override fun onResponse(call: Call<MostrarCuentaResponse>, response: Response<MostrarCuentaResponse>) {
                    if (response.isSuccessful) {
                        val body : MostrarCuentaResponse? = response.body()
                        var nickname = itemView.findViewById<TextView>(R.id.txNombre)
                        nickname.text = body?.nombre + " " + body?.apellidos
                    }
                }

                override fun onFailure(call: Call<MostrarCuentaResponse>, t: Throwable) {
                    println("Error" + t.message)
                }
            })
            texto.text = tweet.descripcion
            info.text = tweet.updatedAt.subSequence(0,10)
            //Picasso.get().load(country.countryInfo.flag).into(imageView)
        }
    }
}

