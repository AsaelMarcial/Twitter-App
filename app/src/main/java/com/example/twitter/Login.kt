package com.example.twitter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import models.LoginResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val user = findViewById<TextView>(R.id.username)
        val pass = findViewById<TextView>(R.id.password)

        val registroClick = findViewById<Button>(R.id.btnRegistro)
        registroClick.setOnClickListener{
            registroClick.setOnClickListener {
                val intento1 = Intent(this, registro::class.java)
                startActivity(intento1)
            }
        }


        val loginClick = findViewById<Button>(R.id.btnLogin)
        loginClick.setOnClickListener {
            if (user.length() != 0 && pass.length() != 0) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://www.ramiro.digital/api/login/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(ApiService::class.java)

                val fields: HashMap<String?, RequestBody?> = HashMap()
                fields["username"] =
                    (user.text.toString()).toRequestBody("text/plain".toMediaTypeOrNull())
                fields["password"] =
                    (pass.text.toString()).toRequestBody("text/plain".toMediaTypeOrNull())

                val response = service.login(fields)
                response.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            val token = response.body()?.token
                            val intento1 = Intent(this@Login, test::class.java)
                                //intento1.putExtra("token", token)
                            startActivity(intento1)
                        }
                    }
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        println("Error" + t.message)
                        Toast.makeText(this@Login, "Verificar datos", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Verificar campos vacios", Toast.LENGTH_SHORT).show()
            }


        }
    }
}