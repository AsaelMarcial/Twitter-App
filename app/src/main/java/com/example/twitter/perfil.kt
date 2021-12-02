package com.example.twitter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import models.Post
import models.Usuario
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class perfil : AppCompatActivity() {
    private var listView: ListView? = null
    private var postView: MutableList<Post>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val bundle = intent.extras
        val token = bundle?.getString("token")
        println("Perfil: "+token)

        postView = mutableListOf<Post>()
        listView = findViewById(R.id.listViewTweet) as ListView

        loadProfile(token)
        loadTweets(token);


    }

    private fun loadProfile(token: String?) {

        val nombre = findViewById<TextView>(R.id.name)
        val info = findViewById<TextView>(R.id.info)
        val seguidores = findViewById<TextView>(R.id.seguidores)

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
                    nombre.text = body?.nombre + " " +body?.apellidos
                    info.text = body?.info
                    seguidores.text = body?.createdAt?.subSequence(0,10)
                }
            }
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                println("Error" + t.message)
            }
        })
    }

    private fun loadTweets(token: String?) {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/misPosts/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)
        val response = service.getMisPost("Bearer " + token)

        response.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                println("response:" + response.body())
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                print("error" + t.message)
            }

        })
        /*val stringRequest = StringRequest(Request.Method.GET,
            "http://www.ramiro.digital/api/misPosts/",
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("data")

                        for (i in 0..array.length() - 1) {
                            val objectData = array.getJSONObject(i)
                            val data = Post(
                                objectData.getInt("id"),
                                objectData.getInt("idUsuario"),
                                objectData.getString("descripcion"),
                                objectData.getInt("idMultimedia"),
                                objectData.getString("createdAt"),
                                objectData.getString("updatedAt")
                            )
                            postView!!.add(data)
                            val adapter = PostAdapter(this, postView!!)
                            listView!!.adapter = adapter
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)
    }*/
    }
}