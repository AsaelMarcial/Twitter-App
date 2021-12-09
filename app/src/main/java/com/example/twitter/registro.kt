package com.example.twitter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import models.RegisterResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val registrarClic = findViewById<Button>(R.id.btnRegistrar)
        val username = findViewById<EditText>(R.id.ptUsername)
        val password = findViewById<EditText>(R.id.ptpPassword)
        val nombre = findViewById<EditText>(R.id.ptNombre)
        val apellidos = findViewById<EditText>(R.id.ptApellidos)
        val email = findViewById<EditText>(R.id.ptEmail)
        val info = findViewById<EditText>(R.id.ptInfo)

        registrarClic.setOnClickListener {
            if(username.length() != 0 && password.length() > 6 && nombre.length() != 0 && apellidos.length() != 0 && email.length() != 0){
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://www.ramiro.digital/api/register/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(ApiService::class.java)

                val fields: HashMap<String?, RequestBody?> = HashMap()
                fields["username"] = (username.text.toString()).toRequestBody("text/plain".toMediaTypeOrNull())
                fields["password"] = (password.text.toString()).toRequestBody("text/plain".toMediaTypeOrNull())
                fields["nombre"] = (nombre.text.toString()).toRequestBody("text/plain".toMediaTypeOrNull())
                fields["apellidos"] = (apellidos.text.toString()).toRequestBody("text/plain".toMediaTypeOrNull())
                fields["email"] = (email.text.toString()).toRequestBody("text/plain".toMediaTypeOrNull())
                fields["info"] = (info.text.toString()).toRequestBody("text/plain".toMediaTypeOrNull())

                val response = service.register(fields)
                response.enqueue(object : Callback<RegisterResponse>{
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful){
                            val respuesta  = response.body()!!
                            Log.d("Response", "Token creado : ${respuesta.token}")
                            Toast.makeText(this@registro, "Registrado correctamente", Toast.LENGTH_SHORT).show()
                            val intento = Intent(this@registro, Login::class.java)
                            startActivity(intento)
                        }else{
                            Toast.makeText(this@registro, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Toast.makeText(this@registro, "Something went wrong $t", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }


    }
}