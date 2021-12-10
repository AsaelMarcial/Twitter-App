package com.example.twitter


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.twitter.ui.home.InicioFragment
import com.example.twitter.ui.buscar.BuscarFragment
import com.example.twitter.ui.perfil.PerfilFragment
import kotlinx.android.synthetic.main.activity_principal.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody.Part.Companion.create
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Principal : AppCompatActivity(R.layout.activity_principal), NoticeDialogFragment.NoticeDialogListener {

    var token : String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //replaceFragment(perfilView)

        val extras = intent.extras
        token = extras?.getString("token")
        println("Token desde principal: "+ token)

        if (savedInstanceState == null) {
            val bundle = bundleOf("token" to token)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<PerfilFragment>(R.id.nav_fragment, args = bundle)
            }
        }


        nav_view.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.perfil -> {
                    val bundle = bundleOf("token" to token)
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<PerfilFragment>(R.id.nav_fragment, args = bundle)
                    }
                }
                R.id.buscar -> {
                    val bundle = bundleOf("token" to token)
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<BuscarFragment>(R.id.nav_fragment, args = bundle)
                    }
                }
                R.id.inicio -> {
                    val bundle = bundleOf("token" to token)
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<InicioFragment>(R.id.nav_fragment, args = bundle)
                    }
                }
            }
            true
        }
    }

    override fun onDialogPositiveClick(tweet: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.ramiro.digital/api/createPost/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)


        println(tweet)
        val response = service.createPost("Bearer " + token,tweet)

        response.enqueue(object : Callback<CreatePostResponse>{
            override fun onResponse(
                call: Call<CreatePostResponse>,
                response: Response<CreatePostResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Principal, response.body()?.message, Toast.LENGTH_SHORT).show()
                    println(tweet)
                }
            }
            override fun onFailure(call: Call<CreatePostResponse>, t: Throwable) {
                println("Error" + t.message)
                Toast.makeText(this@Principal, "Verificar datos", Toast.LENGTH_SHORT).show()
            }

        })

    }


}