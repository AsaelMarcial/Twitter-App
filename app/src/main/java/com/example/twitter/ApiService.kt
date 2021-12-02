package com.example.twitter

import models.Constants
import models.LoginResponse
import models.RegisterResponse
import models.Usuario
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @Multipart
    @POST("/api/register")
    fun register(@PartMap map: HashMap<String?, RequestBody?>): Call<RegisterResponse>

    @POST("/api/login")
    fun login(@PartMap map: HashMap<String?, RequestBody?>): Call<LoginResponse>

    @GET("/api/getCuenta")
    fun getUser(@Header("Authorization") token: String): Call<Usuario>

}