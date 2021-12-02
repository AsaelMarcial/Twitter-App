package com.example.twitter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.twitter.R.*
import models.LoginResponse
import models.RegisterResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_registro)

        var ptUsername = findViewById<EditText>(id.ptUsername)
        var ptpPassword = findViewById<EditText>(id.ptpPassword)
        var ptNombre = findViewById<EditText>(id.ptNombre)
        var ptApellidos = findViewById<EditText>(id.ptApellidos)
        var ptEmail = findViewById<EditText>(id.ptEmail)
        var ptInfo = findViewById<EditText>(id.ptInfo)

        var btnLogin = findViewById<Button>(id.btnRegistrar)

        btnLogin.setOnClickListener{
            if(ptUsername.length() != 0 && ptpPassword.length() != 0 && ptNombre.length() != 0 &&
                ptApellidos.length() != 0 && ptEmail.length() != 0 && ptInfo.length() != 0){

                val retrofit = Retrofit.Builder()
                    .baseUrl("http://www.ramiro.digital/api/register/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(ApiService::class.java)

                val fields: HashMap<String?, RequestBody?> = HashMap()
                fields["username"] =
                    (ptUsername.text.toString()).toRequestBody("text/plain".toMediaTypeOrNull())
                fields["password"] =
                    (ptpPassword.text.toString()).toRequestBody("text/plain".toMediaTypeOrNull())
                fields["nombre"] =
                    (ptNombre.text.toString()).toRequestBody("text/plain".toMediaTypeOrNull())
                fields["apellidos"] =
                    (ptApellidos.text.toString()).toRequestBody("text/plain".toMediaTypeOrNull())
                fields["email"] =
                    (ptUsername.text.toString()).toRequestBody("text/plain".toMediaTypeOrNull())
                fields["info"] =
                    (ptpPassword.text.toString()).toRequestBody("text/plain".toMediaTypeOrNull())

                val response = service.register(fields)
                response.enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        if (response.isSuccessful) {
                            val token = response.body()?.token
                            val intento1 = Intent(this@registro, perfil::class.java)
                            intento1.putExtra("token", token)
                            startActivity(intento1)
                        }
                    }
                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        println("Error" + t.message)
                        Toast.makeText(this@registro, "Verificar datos", Toast.LENGTH_SHORT).show()
                    }
                })


            } else {
                Toast.makeText(this, "Verificar campos vacios", Toast.LENGTH_SHORT).show()
            }
        }



    }
}